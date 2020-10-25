package quinzical.frontend.controllers;
import quinzical.frontend.helper.Speaker;

// Java dependencies.
import java.net.URL;
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

    @FXML Slider synthesisSpeedSlider;
    @FXML Label synthesisSpeedLabel;
    @FXML Button synthesisPreviewButton;
    @FXML Button synthesisResetButton;

    private final Speaker _speaker = Speaker.init();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateSpeedLabel();
    }

    /**
     * This is the handler for when the speed is changed using the slider..
     */
    public void handleSpeedChanged() {
        Double newSpeed = synthesisSpeedSlider.getValue();
        _speaker.setSpeed(newSpeed);

        // Show the new update on the label after being changed..
        updateSpeedLabel();
    }

    /**
     * This is the handler for when the user wants to preview the voice.
     */
    public void handlePreviewButton() {
        String test = "Hey there, how are you doing today? This is just a test";
        _speaker.setSpeech(test);
        _speaker.speak();
    }

    /**
     * This is the handler for when speed is reset to the original.
     */
    public void handleResetButton() {
        _speaker.resetSpeed();
        updateSpeedLabel();
    }

    /**
     * This private method is used to just update the label reflecting the current speed.
     */
    private void updateSpeedLabel() {
        synthesisSpeedSlider.setValue(_speaker.getSpeed());
        double speed = _speaker.getSpeed();

        // Dynamic label content depending on whether the speed is default or custom.
        if (_speaker.isChanged()) {
            synthesisSpeedLabel.setText("The speed has been changed to " + speed + ".");
        } else {
            synthesisSpeedLabel.setText("The speed is set to default, which is " + speed + ".");
        }
    }

}
