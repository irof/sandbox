package org.hogedriven.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author irof
 */
public class S3Controller implements Initializable {
    public TextField bucketName;
    public TextField keyName;
    public ListView<Bucket> bucketList;
    public ListView<S3ObjectSummary> objectList;
    public Label selectedFile;
    public Button uploadButton;

    private File file;
    private ObservableList<Bucket> buckets = FXCollections.observableArrayList();
    private ObservableList<S3ObjectSummary> objects = FXCollections.observableArrayList();

    private final Stage stage;
    private final AmazonS3 client;

    private final Map<ObjectIdentifier, Stage> objectWindows = new HashMap<>();

    public S3Controller(Stage stage, AmazonS3 client) {
        this.stage = stage;
        this.client = client;
    }

    public void getBuckets() {
        buckets.clear();
        buckets.addAll(client.listBuckets());
    }

    public void createBucket() {
        Bucket bucket = client.createBucket(bucketName.getText());
        buckets.add(bucket);
    }

    public void deleteBucket() {
        client.deleteBucket(bucketName.getText());
        buckets.removeIf(s -> s.getName().equals(bucketName.getText()));
    }

    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(stage);
        selectedFile.setText(file == null ? null : file.getPath());
        keyName.setText(file.getName());
    }

    public void uploadFile() {
        client.putObject(bucketName.getText(), keyName.getText(), file);
        refreshObjects();
    }

    public void deleteFile() {
        client.deleteObject(bucketName.getText(),
                objectList.getSelectionModel().getSelectedItem().getKey());
        refreshObjects();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bucketList.setItems(buckets);
        bucketList.setCellFactory(this::createBucketCell);

        objectList.setItems(objects);
        objectList.setCellFactory(this::createObjectCell);

        selectedFile.setText("");

        uploadButton.disableProperty().bind(
                Bindings.isEmpty(selectedFile.textProperty())
                        .or(Bindings.isEmpty(keyName.textProperty())));
    }

    private ListCell<Bucket> createBucketCell(ListView<Bucket> listView) {
        ListCell<Bucket> cell = new ListCell<Bucket>() {
            @Override
            protected void updateItem(Bucket item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty);
                setText(empty ? "" : item.getName());
            }
        };
        cell.setOnMouseClicked(event -> {
            Bucket bucket = cell.getItem();
            bucketName.setText(bucket.getName());
            refreshObjects();
        });
        return cell;
    }

    private ListCell<S3ObjectSummary> createObjectCell(ListView<S3ObjectSummary> s3ObjectSummaryListView) {
        ListCell<S3ObjectSummary> listCell = new ListCell<S3ObjectSummary>() {
            @Override
            protected void updateItem(S3ObjectSummary item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty);
                setText(empty ? "" : item.getKey());
            }
        };
        listCell.setOnMouseClicked(event -> {
            S3ObjectSummary item = listCell.getItem();
            ObjectIdentifier id = new ObjectIdentifier(item);
            if (objectWindows.containsKey(id)) {
                objectWindows.get(id).requestFocus();
            } else {
                Stage objectWindow = createS3ObjectWindow(item);
                objectWindow.show();
                objectWindows.put(id, objectWindow);
                objectWindow.setOnCloseRequest(e -> objectWindows.remove(id));
            }
        });
        return listCell;
    }

    private Stage createS3ObjectWindow(S3ObjectSummary item) {
        try {
            Stage stage = new Stage(StageStyle.DECORATED);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../s3object.fxml"));
            loader.setControllerFactory(clz -> new ObjectController(stage, client, item));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            return stage;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void refreshObjects() {
        objects.clear();
        ObjectListing listing = client.listObjects(bucketName.getText());
        do {
            objects.addAll(listing.getObjectSummaries());
            listing = client.listNextBatchOfObjects(listing);
        } while (listing.getMarker() != null);
    }
}
