package org.hogedriven.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author irof
 */
public class S3Controller implements Initializable {
    public ListView<Bucket> bucketList;
    public ListView<S3ObjectSummary> objectList;

    public Button deleteButton;

    private ObservableList<Bucket> buckets = FXCollections.observableArrayList();
    private ObservableList<S3ObjectSummary> objects = FXCollections.observableArrayList();

    private final Stage stage;
    private final AmazonS3 client;

    private Optional<String> currentBucket = Optional.empty();

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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("新しいBucketの作成");
        dialog.setHeaderText("作りたいBucketの名前を入力してください");
        dialog.setContentText("new Bucket Name :");

        dialog.showAndWait().ifPresent(name -> {
            Bucket bucket = client.createBucket(name);
            buckets.add(bucket);
        });
    }

    public void deleteBucket() {
        currentBucket.ifPresent(client::deleteBucket);
        getBuckets();
    }

    public void uploadFile() {
        // ファイルを選択してもらう
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) return;

        TextInputDialog dialog = new TextInputDialog(file.getName());
        dialog.setTitle("Bucketに格納するキーを入力してください");
        dialog.setHeaderText(file.getPath());
        dialog.setContentText("Key :");

        dialog.showAndWait().ifPresent(name -> {
            client.putObject(currentBucket.get(), name, file);
            refreshObjects();
        });
    }

    public void deleteFile() {
        client.deleteObject(currentBucket.get(),
                objectList.getSelectionModel().getSelectedItem().getKey());
        refreshObjects();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bucketList.setItems(buckets);
        bucketList.setCellFactory(this::createBucketCell);

        objectList.setItems(objects);
        objectList.setCellFactory(this::createObjectCell);
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
            currentBucket = Optional.of(bucket.getName());
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
        currentBucket.ifPresent(name -> {
            ObjectListing listing = client.listObjects(name);
            do {
                objects.addAll(listing.getObjectSummaries());
                listing = client.listNextBatchOfObjects(listing);
            } while (listing.getMarker() != null);
        });
    }
}
