package quinzical.frontend.controllers;
import javafx.scene.control.*;
import quinzical.backend.Progression;
import quinzical.frontend.helper.Speaker;
import quinzical.frontend.helper.ScreenType;
import quinzical.frontend.helper.AlertHelper;
import quinzical.frontend.helper.ScreenSwitcher;

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

    @FXML
    public void handleSpeedChanged() {
        Double newSpeed = synthesisSpeedSlider.getValue();
        speaker.setSpeed(newSpeed);

        // Reflect the new speed on the label.
        updateSpeedLabel();
    }

    @FXML
    public void handlePreviewButton() {
        // Speak the text to the user, so they can test out their new speed.
        String test = "Hey there, how are you doing today? This is just a test";
        speaker.setSpeech(test);
        speaker.speak();
    }

    @FXML
    public void handleResetButton() {
        // Reset the speed back to the original speed (1.0)
        speaker.resetSpeed();
        updateSpeedLabel();
    }

    @FXML
    private void handleResetXPButton() {
        // Spawning an alert to ask the user for confirmation to reset their statistics.
        AlertHelper helper = AlertHelper.getInstance();
        helper.showAlert(Alert.AlertType.CONFIRMATION, "Are you sure you want to reset your XP?", ButtonType.YES, ButtonType.NO);

        // If confirmed, then perform the reset.
        if (helper.getResult() == ButtonType.YES) {
            Progression.kill();
            ScreenSwitcher.getInstance().switchTo(ScreenType.MAIN_MENU);
        }
    }

    /**
     * This private method is used to just update the label reflecting their new synthesis speed.
     */
    private void updateSpeedLabel() {
        synthesisSpeedSlider.setValue(speaker.getSpeed());
        double speed = speaker.getSpeed();

        // Dynamic label content depending on whether the speed is default or set to custom.
        if (speaker.isChanged()) {
            synthesisSpeedLabel.setText("The speed has been changed to " + speed + ".");
        } else {
            synthesisSpeedLabel.setText("The speed is set to default, which is " + speed + ".");
        }
    }
}
