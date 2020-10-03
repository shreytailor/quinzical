package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.tasks.Speaker;
import a3.quinzical.frontend.helper.ScreenType;
import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
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
import javafx.scene.input.KeyEvent;

/**
 * This class is the controller class for the MainMenu screen.
 * @author Shrey Tailor, Jason Wang
 */
public class MainMenuController {

    @FXML Button practiceModuleButton;
    @FXML Button gameModuleButton;
    @FXML Button exitButton;
    @FXML Button settingsButton;

    private Speaker _speaker = Speaker.init();

    /**
     * This handler is used to respond to the shortcuts of the screen.
     * @param event the key event.
     */
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch(event.getCode()) {
            case P:
                practiceModuleButton.fire();
                break;
            case G:
                gameModuleButton.fire();
                break;
            case X:
                exitButton.fire();
                break;
            case S:
                settingsButton.fire();
                break;
        }
    }

    /**
     * This is the handler for clicking on the "Practice" button.
     */
    @FXML
    private void handlePracticeModuleButton() {
        ScreenSwitcher switcher = ScreenSwitcher.getInstance();
        switcher.switchTo(ScreenType.PRACTICE_MODULE);
        switcher.setTitle("Practice Module");
    }

    /**
     * This is the handler for clicking on the "Game" button.
     */
    @FXML
    private void handleGameModuleButton() {
        ScreenSwitcher switcher = ScreenSwitcher.getInstance();

        // If there are questions remaining, go to the Game Module, else go to the other screen.
        if (GameDatabase.getInstance().getRemainingClues() > 0) {
            switcher.switchTo(ScreenType.GAME_MODULE);
        } else {
            switcher.switchTo(ScreenType.GAME_FINISHED);
        }
        switcher.setTitle("Game Module");
    }


    /**
     * This is the handler for when the "Exit" button is clicked.
     */
    @FXML
    private void handleExitButton() {
        ScreenSwitcher.getInstance().exit();
    }


    /**
     * This is the handler for when the "Settings" button is clicked. We show a new window.
     */
    @FXML
    private void handleSettingsButton() {
        int WIDTH = 450;
        int HEIGHT = 250;

        // Creating and configuring the new stage.
        Stage settingsStage = new Stage();
        settingsStage.setResizable(false);
        settingsStage.setTitle("Settings");
        try {
            settingsStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("./../fxml/Settings.fxml")), WIDTH, HEIGHT));
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

        settingsStage.setOnCloseRequest(event -> {
            _speaker.kill();
        });
        settingsStage.show();
    }

}
