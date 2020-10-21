package a3.quinzical.frontend.controllers;
import a3.quinzical.backend.models.Clue;
import a3.quinzical.backend.models.Category;
import a3.quinzical.frontend.helper.ScreenType;
import a3.quinzical.frontend.helper.AlertHelper;
import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This is the controller class for the GameModule screen.
 */
public class GameModuleController implements Initializable {

    @FXML TabPane tabPane;
    @FXML Button backButton;
    @FXML Tab newZealandTab;
    @FXML Button resetButton;
    @FXML Label winningsLabel;
    @FXML Tab internationalTab;
    @FXML ImageView lockSymbol;
    @FXML GridPane newZealandGrid;
    @FXML GridPane internationalGrid;
    @FXML ToggleButton newZealandButton;
    @FXML ToggleButton internationalButton;

    private GameDatabase  _db = GameDatabase.getInstance();
    private ScreenSwitcher _switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupNewZealandGrid();
        setupInternationalGrid();
        setupToggleButtons();
        newZealandButton.setSelected(true);

        // Set the international section to locked, if it hasn't been unlocked for the user.
        if (_db.getInternationalCategory().isLocked()) {
            lockSymbol.setVisible(true);
            internationalButton.setDisable(true);
        }
    }

    @FXML
    private void handleBackButton () {
        _switcher.switchTo(ScreenType.MAIN_MENU);
        _switcher.setTitle("Main Menu");
    }

    @FXML
    private void handleResetButton() {
        String message = "Are you sure you want to reset the game?";
        AlertHelper _helper = AlertHelper.getInstance();
        _helper.showAlert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);

        if (_helper.getResult() == ButtonType.YES) {
            GameDatabase.kill();
            _switcher.switchTo(ScreenType.CHOOSE_CATEGORIES);
        }
    }

    /**
     * This is a private method used within the class to initialize the root GridPane with the grid
     * for the game. The process is such that we are iterating through each category, and then
     * through each question to display them properly so that it's readable.
     */
    private void setupNewZealandGrid() {
        // Displaying the winnings.
        int winnings = GameDatabase.getInstance().getWinning();
        winningsLabel.setText("$" + winnings);

        // Iterating through each category from the Database.
        for (int category = 0; category < 5; category++) {
            Category categoryObject = _db.getCategory(category);
            Label title = new Label(categoryObject.getName());
            title.getStyleClass().add("category");
            title.getStylesheets().add(getClass().getClassLoader().getResource("a3/quinzical/frontend/styles/GameModule.css").toExternalForm());
            newZealandGrid.add(title, category, 0);

            // Making sure that the first clue is always clickable, hence using this flag.
            boolean active = true;

            // Iterating through each remaining clues within the category.
            List<Clue> clues = categoryObject.remainingClue();
            for (int clue = 1; clue < clues.size() + 1; clue++) {
                Clue clueObject = categoryObject.getClue(clue - 1);
                Button clueButton = clueButtonGenerator(clueObject);

                if (!active) {
                    clueButton.setDisable(true);
                }
                active = false;

                // Setting the listener for the current button.
                clueBinder(clueButton, clueObject, categoryObject);

                // Finally, we add the clue to the grid.
                newZealandGrid.add(clueButton, category, clue);
            }
        }
    }

    private void setupInternationalGrid() {
        Category internationCategory = _db.getInternationalCategory();
        List<Clue> internationalClues = internationCategory.remainingClue();

        boolean active = true;
        for (int clue = 1; clue < internationalClues.size() + 1; clue++) {
            Clue clueObject = internationalClues.get(clue - 1);
            Button clueButton = clueButtonGenerator(clueObject);

            if (!active) {
                clueButton.setDisable(true);
            }
            active = false;

            // Setting the listener for the current button.
            clueBinder(clueButton, clueObject, internationCategory);

            internationalGrid.add(clueButton, 0, clue);
        }
    }

    private void setupToggleButtons() {
        newZealandButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                internationalButton.setSelected(false);
                tabPane.getSelectionModel().select(newZealandTab);
            } else {
                if (internationalButton.isDisabled()) {
                    newZealandButton.setSelected(true);
                } else {
                    internationalButton.setSelected(true);
                }
            }
        });

        internationalButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                newZealandButton.setSelected(false);
                tabPane.getSelectionModel().select(internationalTab);
            } else {
                newZealandButton.setSelected(true);
            }
        });
    }

    private Button clueButtonGenerator(Clue clue) {
        Button clueButton = new Button("$" + clue.getPrize());
        clueButton.getStyleClass().add("clue");
        clueButton.getStylesheets().add(getClass().getClassLoader().getResource("a3/quinzical/frontend/styles/GameModule.css").toExternalForm());
        return clueButton;
    }

    private void clueBinder(Button button, Clue clue, Category category) {
        button.setOnAction(event -> {
            _db.setCurrentClue(clue);
            category.nextQuestion();
            _switcher.switchTo(ScreenType.GAME_CLUE);
        });
    }

}
