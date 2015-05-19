package org.hogedriven.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
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

    private File file;
    private ObservableList<Bucket> buckets = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws Exception {
        Object root = FXMLLoader.load(getClass().getResource("../../../s3client.fxml"));
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(new Scene((Parent) root, 600, 200));

        stage.show();
    }

    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
    }

    public void uploadFile() {
        s3Operation(client ->
                client.putObject(bucketName.getText(), keyName.getText(), file));
    }

    public void getBuckets() {
        this.buckets.clear();
        s3Operation(client -> this.buckets.addAll(client.listBuckets()));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bucketList.setItems(buckets);
        bucketList.setCellFactory(list -> new ListCell<Bucket>() {
            @Override
            protected void updateItem(Bucket item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) setText(item.getName());
            }
        });
        bucketList.setOnMouseClicked(event ->
                bucketName.setText(bucketList.getSelectionModel().getSelectedItem().getName()));

        status.setText("");
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
        AmazonS3Client client = new AmazonS3Client(credentials);
        client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
        return client;
    }
}
