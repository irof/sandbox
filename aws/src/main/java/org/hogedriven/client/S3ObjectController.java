package org.hogedriven.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author irof
 */
public class S3ObjectController implements Initializable {
    public Label bucket;
    public Label key;
    public Label contentType;
    public Label size;
    public Label eTag;
    public Label lastModified;
    public Button observationButton;
    public TextArea observationWindow;

    private final Stage stage;
    private final AmazonS3 client;
    private final S3ObjectSummary summary;

    public S3ObjectController(Stage stage, AmazonS3 client, S3ObjectSummary summary) {
        this.stage = stage;
        this.client = client;
        this.summary = summary;
    }

    public void onObservation() throws Exception {
        S3Object object = client.getObject(getGetObjectRequest());
        try (S3ObjectInputStream content = object.getObjectContent()) {
            byte[] bytes = new byte[1024];
            content.read(bytes);
            String text = new String(bytes);
            observationWindow.setText(text);
        }
    }

    public void onDownload() {
        // タイトルを戻すために一旦退避する
        String title = stage.getTitle();
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            stage.setTitle("保存先ディレクトリを選ぶのです");
            File dir = chooser.showDialog(stage);
            if (dir != null) {
                File destFile = new File(dir, new File(key.getText()).getName());
                // 上書きはしない
                if (destFile.exists()) {
                    throw new UnsupportedOperationException("同じ名前のファイルがあるよ");
                }
                client.getObject(getGetObjectRequest(), destFile);
            }
        } finally {
            stage.setTitle(title);
        }
    }

    private GetObjectRequest getGetObjectRequest() {
        return new GetObjectRequest(bucket.getText(), key.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObjectMetadata meta = client.getObjectMetadata(summary.getBucketName(), summary.getKey());
        bucket.setText(summary.getBucketName());
        key.setText(summary.getKey());
        contentType.setText(meta.getContentType());
        size.setText(String.format("%,3d byte", summary.getSize()));
        eTag.setText(summary.getETag());
        lastModified.setText(summary.getLastModified().toString());

        String contentType = meta.getContentType();
        boolean isText = contentType != null && contentType.startsWith("text");
        observationButton.setDisable(!isText);
        observationWindow.setDisable(!isText);
        observationWindow.setPromptText("チラ見窓です。ボタンおしたら先頭部分だけここに出るです。");
    }
}
