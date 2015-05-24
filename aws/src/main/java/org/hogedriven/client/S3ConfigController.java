package org.hogedriven.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author irof
 */
public class S3ConfigController implements Initializable {

    public TextField accessKey;
    public PasswordField secretKey;
    public ToggleGroup modeGroup;
    public TextField httpsProxy;
    public RadioButton defaultMode;
    public RadioButton mockMode;
    public RadioButton basicMode;

    public S3ConfigController(Dialog<AmazonS3> dialog) {
        dialog.setResultConverter(this::createResult);
    }

    private AmazonS3 createResult(ButtonType button) {
        if (button.getButtonData().isCancelButton()) return null;
        if (mockMode.isSelected()) {
            return AmazonS3Factory.createMock();
        }
        if (basicMode.isSelected()) {
            return createRealClient(new BasicAWSCredentials(accessKey.getText(), secretKey.getText()));
        }
        return createRealClient(new ProfileCredentialsProvider().getCredentials());
    }

    private AmazonS3Client createRealClient(AWSCredentials credentials) {
        return new AmazonS3Client(credentials);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accessKey.disableProperty().bind(basicMode.selectedProperty().not());
        secretKey.disableProperty().bind(basicMode.selectedProperty().not());
    }
}
