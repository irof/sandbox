package fxml;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

public class FXMLExampleController {
    public PasswordField passwordField;
    public Text actiontarget;

    public void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }
}
