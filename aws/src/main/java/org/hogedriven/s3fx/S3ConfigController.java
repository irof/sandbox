package org.hogedriven.s3fx;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Owner;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            return AmazonS3Factory.createMock();
        }
        if (basicMode.isSelected()) {
            return createRealClient(new BasicAWSCredentials(accessKey.getText(), secretKey.getText()));
        }
        return createRealClient(new ProfileCredentialsProvider().getCredentials());
    }

    private AmazonS3Client createRealClient(AWSCredentials credentials) {
        AmazonS3Client client = new AmazonS3Client(credentials, createClientConfig());
        Owner owner = client.getS3AccountOwner();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("S3に接続しました");
        alert.setContentText("Account Owner Name: " + owner.getDisplayName());
        alert.showAndWait();

        return client;
    }

    private ClientConfiguration createClientConfig() {
        ClientConfiguration config = new ClientConfiguration();
        String proxyText = proxy.getText();
        Pattern pattern = Pattern.compile("(.+):(\\d+)");
        Matcher matcher = pattern.matcher(proxyText);
        if (matcher.matches()) {
            config.setProxyHost(matcher.group(1));
            config.setProxyPort(Integer.valueOf(matcher.group(2)));
        }
        return config;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accessKey.disableProperty().bind(basicMode.selectedProperty().not());
        secretKey.disableProperty().bind(basicMode.selectedProperty().not());
        proxy.disableProperty().bind(mockMode.selectedProperty());
    }
}
