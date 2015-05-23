package org.hogedriven.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../s3client.fxml"));
        loader.setControllerFactory(clz -> new S3Controller(stage, AmazonS3Factory.createAmazonS3Client()));

        stage.setTitle(this.getClass().getSimpleName());
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
