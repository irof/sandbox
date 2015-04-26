package client;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author irof
 */
public class Controller implements Initializable {

    public TextArea responseBody;

    public TextField uri;

    public TextArea payload;

    public ChoiceBox<String> contentType;

    public RadioButton get;
    public RadioButton post;

    public TableView<Map.Entry<String, List<String>>> responseHeaders;
    public TableColumn<Map.Entry<String, List<String>>, String> responseHeaderName;
    public TableColumn<Map.Entry<String, List<String>>, String> responseHeaderValue;
    public Text responseStatus;

    public void send(ActionEvent actionEvent) {
        try {
            Response response = sendRequest();
            try (UncheckedAutoCloseable ignored = response::close) {
                Response.StatusType status = response.getStatusInfo();
                responseStatus.setText(String.format("%d %s", status.getStatusCode(), status.getReasonPhrase()));
                responseHeaders.setItems(FXCollections.observableArrayList(
                        response.getStringHeaders().entrySet()
                ));
                responseBody.setText(response.readEntity(String.class));
            }
        } catch (ProcessingException e) {
            // ダイアログ使ってみたかった
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("JAX-RS しょりちゅーれーがい");
            alert.setHeaderText("たぶん通信エラーとかそのへん");
            alert.setContentText("気になる人のためのスタックトレース");

            try (StringWriter stringWriter = new StringWriter();
                 PrintWriter writer = new PrintWriter(stringWriter);) {
                e.printStackTrace(writer);
                alert.getDialogPane().setExpandableContent(new TextArea(stringWriter.toString()));
            } catch (IOException e1) {
                // きにしない
                e1.printStackTrace();
            }
            alert.show();
        }
    }

    private Response sendRequest() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(uri.getText());
        if (post.isSelected()) {
            return target.request().post(Entity.xml(payload.getText()));
        } else {
            return target.request().get();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        uri.setText("http://localhost:8080/resteasy/customers");
        try {
            payload.setText(new String(Files.readAllBytes(Paths.get(
                    this.getClass().getResource("default/payload.xml").toURI()))));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        contentType.setValue("application/xml");
        post.setSelected(true);
        responseHeaders.setPlaceholder(new Label("ボタン押したらなんか出るはず"));

        // lambdaで誤魔化せてるように見えるけど、力技すぎる気がする
        responseHeaderName.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getKey())
        );
        responseHeaderValue.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue().getValue().toString())
        );
    }
}

/**
 * AutoCloseableをそのまま使うと投げもしないチェック例外を処理しろ言われるので、チェック例外を投げ無いのを作っておく。
 */
@FunctionalInterface
interface UncheckedAutoCloseable extends AutoCloseable {
    @Override
    void close();
}
