package org.hogedriven.s3fx;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Owner;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.hogedriven.s3fx.client.AmazonS3Builder;
import org.hogedriven.s3fx.client.AmazonS3MockBuilder;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author irof
 */
public class S3ConfigController implements Initializable {

    public TextField accessKey;
    public PasswordField secretKey;
    public ToggleGroup modeGroup;
    public TextField proxy;
    public RadioButton defaultMode;
    public RadioButton mockMode;
    public RadioButton basicMode;

    public S3ConfigController(Dialog<AmazonS3> dialog) {
        dialog.setResultConverter(this::createResult);
    }

    private AmazonS3 createResult(ButtonType button) {
        if (button.getButtonData().isCancelButton()) return null;

        if (mockMode.isSelected()) {
            return new AmazonS3MockBuilder().build();
        }

        if (basicMode.isSelected()) {
            return new AmazonS3Builder()
                    .withProxy(proxy.getText())
                    .basic(accessKey.getText(), secretKey.getText())
                    .verify(this::ownerCheck)
                    .build();
        }

        return new AmazonS3Builder()
                .withProxy(proxy.getText())
                .defaultProfile()
                .verify(this::ownerCheck)
                .build();
    }

    private void ownerCheck(AmazonS3 client) {
        Owner owner = client.getS3AccountOwner();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("S3に接続しました");
        alert.setContentText("Account Owner Name: " + owner.getDisplayName());
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accessKey.disableProperty().bind(basicMode.selectedProperty().not());
        secretKey.disableProperty().bind(basicMode.selectedProperty().not());
        proxy.disableProperty().bind(mockMode.selectedProperty());
    }
}
