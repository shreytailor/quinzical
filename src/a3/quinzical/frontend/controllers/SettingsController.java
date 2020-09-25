package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.Speaker;

// Java dependencies.
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;


/**
 * This class is the controller class for the Settings screen.
 * @author Shrey Tailor, Jason Wang
 */

public class SettingsController implements Initializable {

    private Speaker _speaker;

    @FXML
    Slider synthesisSpeedSlider;
    @FXML
    Label synthesisSpeedLabel;
    @FXML
    Button synthesisPreviewButton;
    @FXML
    Button synthesisResetButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _speaker = Speaker.init();
        updateSpeedLabel();
    }


    /**
     * This is the handler for when the slider is changed by the user to change speed.
     */
    public void handleSpeedChanged() {
        try {
            Double newSpeed = synthesisSpeedSlider.getValue();
            _speaker.setSpeed(newSpeed);
        } catch (IOException error) {  }
        updateSpeedLabel();
    }


    /**
     * This is the handler for when the user wants to preview the voice synthesis.
     */
    public void handlePreviewButton() {
        _speaker.setSpeech("Hello person, how are you doing today?");
        _speaker.speak();
    }

    public void handleResetButton() {
        _speaker.resetSpeed();
        updateSpeedLabel();
    }


    /**
     * This method is called whenever we want to update the label (essentially whenever the speed
     * changes). It has been put inside of this method for code re-usage in multiple places.
     */
    private void updateSpeedLabel() {
        synthesisSpeedSlider.setValue(_speaker.getSpeed());

        double speed = _speaker.getSpeed();
        if (_speaker.isChanged()) {
            synthesisSpeedLabel.setText("The speed has been changed to " + speed + ".");
        } else {
            synthesisSpeedLabel.setText("The speed is set to default, that is " + speed + ".");
        }
    }

}
