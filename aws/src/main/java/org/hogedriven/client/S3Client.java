package org.hogedriven.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * @author irof
 */
public class S3Client extends Application implements Initializable {
    public TextField bucketName;
    public TextField keyName;
    public Label status;
    public ListView<Bucket> bucketList;
    public ListView<S3ObjectSummary> objectList;
    public Label selectedFile;

    private File file;
    private ObservableList<Bucket> buckets = FXCollections.observableArrayList();
    private ObservableList<S3ObjectSummary> objects = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws Exception {
        Object root = FXMLLoader.load(getClass().getResource("../../../s3client.fxml"));
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(new Scene((Parent) root, 500, 520));
        stage.setResizable(false);

        stage.show();
    }

    public void getBuckets() {
        buckets.clear();
        s3Operation(client -> buckets.addAll(client.listBuckets()));
    }

    public void createBucket() {
        s3Operation(client -> client.createBucket(bucketName.getText()));
    }

    public void deleteBucket() {
        s3Operation(client -> {
            client.deleteBucket(bucketName.getText());
            buckets.removeIf(s -> s.getName().equals(bucketName.getText()));
        });
    }

    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
        selectedFile.setText(file.getName());
    }

    public void uploadFile() {
        s3Operation(client ->
                client.putObject(bucketName.getText(), keyName.getText(), file));
        s3Operation(this::refleshObjects);
    }

    public void deleteFile() {
        s3Operation(client ->
                client.deleteObject(bucketName.getText(),
                        objectList.getSelectionModel().getSelectedItem().getKey()));
        s3Operation(this::refleshObjects);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bucketList.setItems(buckets);
        bucketList.setCellFactory(list -> new ListCell<Bucket>() {
            @Override
            protected void updateItem(Bucket item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        });
        bucketList.setOnMouseClicked(event -> {
                    String name = bucketList.getSelectionModel().getSelectedItem().getName();
                    bucketName.setText(name);
                    s3Operation(this::refleshObjects);
                }
        );

        objectList.setItems(objects);
        objectList.setCellFactory(list -> new ListCell<S3ObjectSummary>() {
            @Override
            protected void updateItem(S3ObjectSummary item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getKey());
            }
        });

        status.setText("");
        selectedFile.setText("");
    }

    private void refleshObjects(AmazonS3Client client) {
        objects.clear();
        ObjectListing listing = client.listObjects(bucketName.getText());
        do {
            objects.addAll(listing.getObjectSummaries());
            listing = client.listNextBatchOfObjects(listing);
        } while (listing.getMarker() != null);
    }

    private void s3Operation(Consumer<AmazonS3Client> r) {
        status.setText("processing...");
        try {
            r.accept(getClient());
            status.setText("success!!");
        } catch (RuntimeException e) {
            status.setText("failed... : " + e.getMessage());
            throw e;
        }
    }

    private AmazonS3Client getClient() {
        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
        return new AmazonS3Client(credentials);
    }
}
