package quinzical.frontend.controllers;
import quinzical.frontend.helper.Speaker;
import quinzical.frontend.helper.ScreenType;
import quinzical.backend.database.GameDatabase;
import quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.io.IOException;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;

/**
 * This class is the controller class for the MainMenu screen.
 * @author Shrey Tailor, Jason Wang
 */
public class MainMenuController {

    @FXML Button practiceModuleButton;
    @FXML Button gameModuleButton;
    @FXML Button exitButton;
    @FXML Button settingsButton;

    private final Speaker _speaker = Speaker.init();

    @FXML
    private void handlePracticeModuleButton() {
        ScreenSwitcher switcher = ScreenSwitcher.getInstance();
        switcher.switchTo(ScreenType.PRACTICE_MODULE);
        switcher.setTitle("Practice Module");
    }

    @FXML
    private void handleGameModuleButton() {
        /*
         * Dynamic routing of the button depending on the state of the game. It depends on whether
         * the game already exists, and other factors.
         */
        ScreenSwitcher switcher = ScreenSwitcher.getInstance();
        if (GameDatabase.singletonExist()) {
            if (GameDatabase.getInstance().getRemainingClues() > 0) {
                switcher.switchTo(ScreenType.GAME_MODULE);
            } else {
                switcher.switchTo(ScreenType.GAME_FINISHED);
            }
        } else {
            switcher.switchTo(ScreenType.CHOOSE_CATEGORIES);
        }

        switcher.setTitle("Game Module");
    }

    @FXML
    private void handleExitButton() {
        ScreenSwitcher.getInstance().exit();
    }

    @FXML
    private void handleSettingsButton() {
        int WIDTH = 600;
        int HEIGHT = 350;

        // Creating and configuring the new stage.
        Stage settingsStage = new Stage();
        settingsStage.setResizable(false);
        settingsStage.setTitle("Settings");
        try {
            URL url = getClass().getClassLoader().getResource("quinzical/frontend/fxml/Settings.fxml");
            settingsStage.setScene(new Scene(FXMLLoader.load(url), WIDTH, HEIGHT));
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }

        // Trying to center the settings pane on the screen, when opened.
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        settingsStage.setX((screen.getWidth() - WIDTH) / 2);
        settingsStage.setY((screen.getHeight() - HEIGHT) / 2);

        // These extra settings are necessary to block the main stage when this settings is opened.
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.initOwner(ScreenSwitcher.getInstance().getStage().getScene().getWindow());

        // If the screen was closed before the voice stops speaking, then we are stopping synthesis.
        settingsStage.setOnCloseRequest(event -> {
            _speaker.kill();
        });
        settingsStage.show();
    }

}
