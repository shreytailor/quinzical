package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.models.Clue;
import a3.quinzical.backend.models.Category;
import a3.quinzical.frontend.helper.AlertHelper;
import a3.quinzical.frontend.helper.ScreenType;
import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ButtonType;

/**
 * This is the controller class for the GameModule screen.
 */
public class GameModuleController implements Initializable {

    @FXML GridPane clueGrid;
    @FXML Label winningsLabel;
    @FXML Button resetButton;
    @FXML Button backButton;

    private GameDatabase  _db = GameDatabase.getInstance();
    private ScreenSwitcher _switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupScreen();
    }

    /**
     * This is for setting the shortcuts of the current screen.
     * @param event the key press event.
     */
    @FXML
    private void onKeyPressed (KeyEvent event) {
        switch (event.getCode()) {
            case B:
                backButton.fire();
                break;
            case R:
                resetButton.fire();
                break;
        }
    }

    /**
     * This is the handler class for when the "Back" button is pressed.
     */
    @FXML
    private void handleBackButton () {
        _switcher.switchTo(ScreenType.MAIN_MENU);
        _switcher.setTitle("Main Menu");
    }

    /**
     * This is the handler class for when the user intends to "Reset" the game.
     */
    @FXML
    private void handleResetButton() {
        String message = "Are you sure you want to reset the game?";
        AlertHelper _helper = AlertHelper.getInstance();
        _helper.showAlert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);

        if (_helper.getResult() == ButtonType.YES) {
            GameDatabase.kill();
            _switcher.switchTo(ScreenType.GAME_MODULE);
        }
    }

    /**
     * This is a private method used within the class to initialize the root GridPane with the grid
     * for the game. The process is such that we are iterating through each category, and then
     * through each question to display them properly so that it's readable.
     */
    private void setupScreen() {
        // Displaying the winnings.
        int winnings = GameDatabase.getInstance().getWinning();
        winningsLabel.setText("$" + winnings);

        // Iterating through each category from the Database.
        for (int category = 0; category < 5; category++) {
            Category categoryObject = _db.getCategory(category);
            Label title = new Label(categoryObject.getName());
            title.getStyleClass().add("category");
            title.getStylesheets().add(getClass().getClassLoader().getResource("a3/quinzical/frontend/styles/GameModule.css").toExternalForm());
            clueGrid.add(title, category, 0);

            // Making sure that the first clue is always clickable, hence using this flag.
            boolean active = true;

            // Iterating through each remaining clues within the category.
            List<Clue> clues = categoryObject.remainingClue();
            for (int clue = 1; clue < clues.size() + 1; clue++) {
                Clue clueObject = categoryObject.getClue(clue - 1);
                Button clueButton = new Button("$" + clueObject.getPrize());
                clueButton.getStyleClass().add("clue");
                clueButton.getStylesheets().add(getClass().getClassLoader().getResource("a3/quinzical/frontend/styles/GameModule.css").toExternalForm());

                if (!active) {
                    clueButton.setDisable(true);
                }
                active = false;

                // Setting the listener for the current button.
                clueButton.setOnAction(event -> {
                    _db.setCurrentClue(clueObject);
                    categoryObject.nextQuestion();
                    _switcher.switchTo(ScreenType.GAME_CLUE);
                });

                // Finally, we add the clue to the grid.
                clueGrid.add(clueButton, category, clue);
            }
        }
    }

}
