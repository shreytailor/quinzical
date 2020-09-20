package a3.quinzical.frontend.controllers;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

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
     * This method is the handler which is used when any key is pressed on the Main Menu screen.
     * We are using shortcuts to navigate through the game as well, therefore this is important.
     * @param event
     */
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

    /**
     * This method is the handler which is used when an action is performed on the exit button.
     */
    @FXML
    private void handleExitButton() {
        ScreenController screenController = ScreenController.getInstance();
        screenController.exit();
    }

}
