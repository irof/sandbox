package org.hogedriven.client;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * @author irof
 */
public class ObjectController {
    public TextArea observationWindow;
    public Label bucketLabel;
    public Label keyLabel;
    public Label fileNameLabel;

    private final S3ObjectSummary objectSummary;

    public ObjectController(S3ObjectSummary objectSummary) {
        this.objectSummary = objectSummary;
    }

    public void onShow(ActionEvent actionEvent) {
    }

    public void onDelete(ActionEvent actionEvent) {
    }

    public void onDownload(ActionEvent actionEvent) {
    }
}
