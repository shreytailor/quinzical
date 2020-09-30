package a3.quinzical.frontend.controllers;

import a3.quinzical.backend.models.Clue;
import a3.quinzical.backend.models.Category;
import a3.quinzical.frontend.switcher.ScreenType;
import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.switcher.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.util.List;
import java.io.IOException;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;


public class GameModuleController implements Initializable {

    @FXML
    GridPane clueGrid;
    @FXML
    Label winningsLabel;
    @FXML
    Button backButton;

    private GameDatabase  _db = GameDatabase.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupScreen();
    }


    @FXML
    private void handleKeyPressed (KeyEvent event) {
        switch (event.getCode()) {
            case B:
                backButton.fire();
        }
    }


    @FXML
    private void handleBackButton () {
        ScreenSwitcher screenSwitcher = ScreenSwitcher.getInstance();
        screenSwitcher.setScreen(ScreenType.MAIN_MENU);
        screenSwitcher.setTitle("Main Menu");
    }


    /**
     * This is a private method used only within the class, and its used to initialize the screen
     * on two different scenarios.
     * (1) When the screen is first launched by the user from the Main Menu.
     * (2) If the game is reset by the user, we must also reset the screen to display the latest
     *      information about their session.
     *
     * Hence, since we had to do this action in more than one scenario, we are doing some code
     * re-usage by making another method for it.
     */
    private void setupScreen() {
        // Updating the winnings label to show the latest winnings.
        int winnings = GameDatabase.getInstance().getWinning();
        winningsLabel.setText("$" + winnings);

        // Populating the grid with the categories and questions.
        for (int category = 0; category < 5; category++) {
            // Creating a custom label for the name of the category, we are currently on.
            Category categoryObject = _db.getCategory(category);
            Label title = new Label(categoryObject.getName());
            title.getStyleClass().add("category");
            title.getStylesheets().add(getClass().getClassLoader().getResource("a3/quinzical/frontend/styles/GameModule.css").toExternalForm());
            clueGrid.add(title, category, 0);

            // Process of populating each category with the remaining clues.
            List<Clue> clues = categoryObject.remainingClue();
            for (int clue = 1; clue < clues.size() + 1; clue++) {
                // Creating the button for the current question, and adding it to the grid.
                Clue clueObject = categoryObject.getClue(clue - 1);
                Button clueButton = new Button("$" + clueObject.getPrize());
                clueButton.getStyleClass().add("clue");
                clueButton.getStylesheets().add(getClass().getClassLoader().getResource("a3/quinzical/frontend/styles/GameModule.css").toExternalForm());

                clueButton.setOnAction(event -> {
                    _db.setCurrentClue(clueObject);
                    categoryObject.nextQuestion();

                    try {
                        ScreenSwitcher.getInstance().addScreen(ScreenType.GAME_CLUE, FXMLLoader.load(getClass().getResource("./../fxml/GameClue.fxml")));
                    } catch (IOException error) {  };

                    ScreenSwitcher.getInstance().setScreen(ScreenType.GAME_CLUE);
                });

                clueGrid.add(clueButton, category, clue);
            }
        }
    }

}
