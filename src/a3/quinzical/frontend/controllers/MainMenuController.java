package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.Speaker;

// Java dependencies.
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;


/**
 * This class is the controller class for the MainMenu screen.
 * @author Shrey Tailor, Jason Wang
 */

public class MainMenuController implements Initializable {

    @FXML
    Label title;
    @FXML
    Button practiceModuleButton;
    @FXML
    Button gameModuleButton;
    @FXML
    Button exitButton;
    @FXML
    Button settingsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Speaker.init();
    }

    /**
     * This method is the handler which is used when any key is pressed on the Main Menu screen.
     * We are using shortcuts to navigate through the game.
     * @param event
     */
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch(event.getCode()) {
            case P:
                practiceModuleButton.fireEvent(new ActionEvent());
            case G:
                gameModuleButton.fireEvent(new ActionEvent());
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
        ScreenController.getInstance().exit();
    }

    /**
     * This is the handler for when the settings button is clicked. It opens the new stage, and also
     * makes sure to disable the parent window so nothing can be clicked.
     */
    @FXML
    public void handleSettingsButton() {
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
        settingsStage.initOwner(ScreenController.getInstance().getStage().getScene().getWindow());

        settingsStage.setOnCloseRequest(event -> {
            Speaker.init().kill();
        });
        settingsStage.show();
    }

}
