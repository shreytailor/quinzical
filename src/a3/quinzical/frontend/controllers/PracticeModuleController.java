package a3.quinzical.frontend.controllers;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

public class PracticeModuleController {

    @FXML
    Button backButton;

    @FXML
    private void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case B:
                backButton.fire();
                break;
        }
    }

    @FXML
    private void handleBackButton() {
        ScreenController screenController = ScreenController.getInstance();
        screenController.setTitle("Main Menu");
        screenController.setScreen("MAIN_MENU");
    }
}