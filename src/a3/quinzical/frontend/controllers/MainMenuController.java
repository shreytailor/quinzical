package a3.quinzical.frontend.controllers;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.Modality;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

/**
 * This class is the controller class for the MainMenu screen.
 */
public class MainMenuController {

    /**
     * The title on the top of the menu.
     */
    @FXML
    Label title;

    /**
     * The button to go to the practice module.
     */
    @FXML
    Button practiceModuleButton;

    /**
     * The button to go to the game module.
     */
    @FXML
    Button gameModuleButton;

    /**
     * The button to go exit the game.
     */
    @FXML
    Button exitButton;

    /**
     * The button for opening the settings window.
     */
    @FXML
    Button settingsButton;

    /**
     * This method is the handler which is used when any key is pressed on the Main Menu screen.
     * We are using shortcuts to navigate through the game as well, therefore this is important.
     * @param event
     */
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch(event.getCode()) {
            case P:
                /*
                ----------------------------------------------------
                EVENT CODE TO BE ADDED HERE LATER ON.
                ----------------------------------------------------
                 */
            case G:
                /*
                ----------------------------------------------------
                EVENT CODE TO BE ADDED HERE LATER ON.
                ----------------------------------------------------
                 */
            case X:
                exitButton.fireEvent(new ActionEvent());
            case S:
                settingsButton.fireEvent(new ActionEvent());
        }
    }

    /**
     * This method is the handler which is used when an action is performed on the exit button.
     */
    @FXML
    private void handleExitButton() {
        ScreenController screenController = ScreenController.getInstance();
        screenController.exit();
    }

    @FXML
    private void handleSettingsButton() {
        // Determining the width, and height constants for the settings screen.
        int WIDTH = 450;
        int HEIGHT = 250;

        Stage settingsStage = new Stage();
        settingsStage.setResizable(false);
        try {
            settingsStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("./../fxml/Settings.fxml")), WIDTH, HEIGHT));
        } catch (IOException error) {
            // We can be assured that this error will never get thrown in our game.
        }

        // Trying to center the settings pane on the screen, when opened.
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        settingsStage.setX((screen.getWidth() - WIDTH) / 2);
        settingsStage.setY((screen.getHeight() - HEIGHT) / 2);

        // These extra settings are necessary to block the main stage when this settings is opened.
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.initOwner(ScreenController.getInstance().getStage().getScene().getWindow());

        settingsStage.show();
    }

}
