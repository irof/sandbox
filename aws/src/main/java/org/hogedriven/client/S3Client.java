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
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author irof
 */
public class S3Client extends Application implements Initializable {
    public TextField bucketName;
    public TextField keyName;
    public ListView<Bucket> bucketList;
    public ListView<S3ObjectSummary> objectList;
    public Label selectedFile;

    private File file;
    private ObservableList<Bucket> buckets = FXCollections.observableArrayList();
    private ObservableList<S3ObjectSummary> objects = FXCollections.observableArrayList();
    private AmazonS3Client client;

    @Override
    public void start(Stage stage) throws Exception {
        Object root = FXMLLoader.load(getClass().getResource("../../../s3client.fxml"));
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(new Scene((Parent) root, 500, 500));
        stage.setResizable(false);

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("こまりました！");
            alert.setHeaderText("なんか例外だよ");
            alert.setContentText("気になる人のためのスタックトレース");
            try (StringWriter stringWriter = new StringWriter();
                 PrintWriter writer = new PrintWriter(stringWriter);) {
                if (e instanceof RuntimeException
                        && e.getCause() instanceof InvocationTargetException) {
                    Throwable ite = e.getCause();
                    Throwable cause = ite.getCause();
                    cause.printStackTrace(writer);
                } else {
                    e.printStackTrace(writer);
                }
                alert.getDialogPane().setExpandableContent(new TextArea(stringWriter.toString()));
            } catch (IOException e1) {
                // きにしない
                e1.printStackTrace();
            }
            alert.show();
        });

        stage.show();
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
        file = fileChooser.showOpenDialog(null);
        selectedFile.setText(file.getName());
    }

    public void uploadFile() {
        client.putObject(bucketName.getText(), keyName.getText(), file);
        refreshObjects(client);
    }

    public void deleteFile() {
        client.deleteObject(bucketName.getText(),
                objectList.getSelectionModel().getSelectedItem().getKey());
        refreshObjects(client);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bucketList.setItems(buckets);
        bucketList.setCellFactory(this::createBucketCell);

        objectList.setItems(objects);
        objectList.setCellFactory(this::createObjectCell);

        selectedFile.setText("");

        AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
        client = new AmazonS3Client(credentials);
    }

    private ListCell<Bucket> createBucketCell(ListView<Bucket> listView) {
        ListCell<Bucket> cell = new ListCell<Bucket>() {
            @Override
            protected void updateItem(Bucket item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        cell.setOnMouseClicked(event -> {
            Bucket bucket = cell.getItem();
            if (bucket != null)
                bucketName.setText(bucket.getName());
            refreshObjects(client);
        });
        return cell;
    }

    private ListCell<S3ObjectSummary> createObjectCell(ListView<S3ObjectSummary> s3ObjectSummaryListView) {
        ListCell<S3ObjectSummary> listCell = new ListCell<S3ObjectSummary>() {
            @Override
            protected void updateItem(S3ObjectSummary item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getKey());
            }
        };
        listCell.setOnMouseClicked(event -> {
            new Alert(Alert.AlertType.INFORMATION).show();
        });
        return listCell;
    }

    private void refreshObjects(AmazonS3Client client) {
        objects.clear();
        ObjectListing listing = client.listObjects(bucketName.getText());
        do {
            objects.addAll(listing.getObjectSummaries());
            listing = client.listNextBatchOfObjects(listing);
        } while (listing.getMarker() != null);
    }
}
