package quinzical.frontend.controllers;
import javafx.scene.control.*;
import quinzical.backend.Progression;
import quinzical.frontend.helper.AlertHelper;
import quinzical.frontend.helper.ScreenSwitcher;
import quinzical.frontend.helper.ScreenType;
import quinzical.frontend.helper.Speaker;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * This class is the controller class for the Settings screen.
 * @author Shrey Tailor, Jason Wang
 */
public class SettingsController implements Initializable {

    @FXML Slider synthesisSpeedSlider;
    @FXML Label synthesisSpeedLabel;
    @FXML Button synthesisPreviewButton;
    @FXML Button synthesisResetButton;

    private final Speaker speaker = Speaker.init();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateSpeedLabel();
    }

    /**
     * This is the handler for when the speed is changed using the slider..
     */
    public void handleSpeedChanged() {
        Double newSpeed = synthesisSpeedSlider.getValue();
        speaker.setSpeed(newSpeed);

        // Show the new update on the label after being changed..
        updateSpeedLabel();
    }

    /**
     * This is the handler for when the user wants to preview the voice.
     */
    public void handlePreviewButton() {
        String test = "Hey there, how are you doing today? This is just a test";
        speaker.setSpeech(test);
        speaker.speak();
    }

    /**
     * This is the handler for when speed is reset to the original.
     */
    public void handleResetButton() {
        speaker.resetSpeed();
        updateSpeedLabel();
    }

    @FXML
    private void handleResetXPButton() {
        AlertHelper helper = AlertHelper.getInstance();
        helper.showAlert(Alert.AlertType.CONFIRMATION, "Are you sure you want to reset your XP?", ButtonType.YES, ButtonType.NO);

        if (helper.getResult() == ButtonType.YES) {
            Progression.kill();
            ScreenSwitcher.getInstance().switchTo(ScreenType.MAIN_MENU);
        }
    }

    /**
     * This private method is used to just update the label reflecting the current speed.
     */
    private void updateSpeedLabel() {
        synthesisSpeedSlider.setValue(speaker.getSpeed());
        double speed = speaker.getSpeed();

        // Dynamic label content depending on whether the speed is default or custom.
        if (speaker.isChanged()) {
            synthesisSpeedLabel.setText("The speed has been changed to " + speed + ".");
        } else {
            synthesisSpeedLabel.setText("The speed is set to default, which is " + speed + ".");
        }
    }

}
