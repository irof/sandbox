package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author irof
 */
public class TinyRestClient extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        Object root = FXMLLoader.load(getClass().getResource("customers-client.fxml"));
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(new Scene((Parent) root, 800, 600));
        stage.show();
    }
}
