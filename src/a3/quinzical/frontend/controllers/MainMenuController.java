package a3.quinzical.frontend.controllers;

// JavaFX dependencies.
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void handleExitButton() {
        ScreenController screenController = ScreenController.getInstance();
        screenController.exit();
    }

}
