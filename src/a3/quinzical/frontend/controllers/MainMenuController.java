package a3.quinzical.frontend.controllers;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

public class MainMenuController {

    @FXML
    Label title;

    @FXML
    Button practiceModuleButton;

    @FXML
    Button gameModuleButton;

    @FXML
    Button exitButton;

    @FXML
    private void handleExitButton() {
        ScreenController screenController = ScreenController.getInstance();
        screenController.exit();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch(event.getCode()) {
            case Q:
                exitButton.fireEvent(new ActionEvent());
        }

        /* --------------------------------------------------------
        ADD MORE KEY SHORTCUTS HERE AS THE BUTTON FUNCTIONALITY IS ADDED.
         -------------------------------------------------------- */
    }

}
