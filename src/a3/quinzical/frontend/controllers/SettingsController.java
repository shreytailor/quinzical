package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.Speaker;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    private Speaker _speaker;

    @FXML
    Slider synthesisSpeedSlider;

    @FXML
    Label synthesisSpeedLabel;

    @FXML
    Button synthesisPreviewButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _speaker = Speaker.init();

        // Getting the current value of synthesis speed (could be default or custom).
        synthesisSpeedSlider.setValue(_speaker.getSpeed());
        updateSpeedLabel();
    }

    public void handleSpeedChanged() {
        try {
            Double newSpeed = synthesisSpeedSlider.getValue();
            _speaker.setSpeed(newSpeed.intValue());
        } catch (IOException error) {
            /*
            Since, we have bounded our slider in FXML to only be within the 80 to 450 range,
            we have made sure that this exception will never be thrown from our API method.
             */
        }

        updateSpeedLabel();
    }

    public void handlePreviewButton() {
        _speaker.setSpeech("This is just a preview.");
        _speaker.speak();
    }

    private void updateSpeedLabel() {
        // Dynamically setting the speed text underneath the slider.
        int speed = _speaker.getSpeed();
        if (_speaker.isChanged()) {
            synthesisSpeedLabel.setText("You have changed the speed to " + speed + " words per minute.");
        } else {
            synthesisSpeedLabel.setText("The speed is set to default which is " + speed + " words per minute.");
        }
    }

}
