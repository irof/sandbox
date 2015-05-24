package org.hogedriven.s3fx;

import com.amazonaws.services.s3.AmazonS3;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * @author irof
 */
public class Starter extends Application {

    private final AmazonS3Factory factory = new AmazonS3Factory();

    @Override
    public void start(Stage stage) throws Exception {
        AmazonS3 amazonS3Client = factory.createAmazonS3Client();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("s3client.fxml"));
        loader.setControllerFactory(clz -> new S3BucketController(stage, amazonS3Client));

        stage.setTitle("S34FX - JavaFX S3 Client");
        stage.setScene(new Scene(loader.load()));

        setExceptionHandler();

        stage.show();
    }

    private void setExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            // 標準出力にも吐いておく
            e.printStackTrace();

            // ダイアログだしとく
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
    }
}
