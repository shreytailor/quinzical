package quinzical.frontend.controllers;
import quinzical.backend.models.Clue;
import quinzical.backend.models.Category;
import quinzical.frontend.helper.ScreenType;
import quinzical.frontend.helper.AlertHelper;
import quinzical.backend.database.GameDatabase;
import quinzical.frontend.helper.ScreenSwitcher;

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

    private final GameDatabase database = GameDatabase.getInstance();
    private final ScreenSwitcher switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Using different private methods, we are setting up the screen.
        setupNewZealandGrid();
        setupInternationalGrid();
        setupToggleButtons();
        newZealandButton.setSelected(true);

        // Lock the International section, if not unlocked for the game.
        if (database.getInternationalCategory().isLocked()) {
            lockSymbol.setVisible(true);
            internationalButton.setDisable(true);
        } else {
            internationalButton.setDisable(false);
        }
    }

    @FXML
    private void handleBackButton () {
        switcher.switchTo(ScreenType.MAIN_MENU);
        switcher.setTitle("Main Menu");
    }

    @FXML
    private void handleResetButton() {
        // Alerts used to confirm decision user's decision to reset the game.
        String message = "Are you sure you want to reset the game?";
        AlertHelper helper = AlertHelper.getInstance();
        helper.showAlert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);

        // If confirmed, then perform the resetting process.
        if (helper.getResult() == ButtonType.YES) {
            GameDatabase.kill();
            switcher.switchTo(ScreenType.CHOOSE_CATEGORIES);
        }
    }

    /**
     * This is a private method used within the class to initialize the root GridPane with the grid
     * for the game. The process is such that we are iterating through each category, and then
     * through each question to display them properly.
     */
    private void setupNewZealandGrid() {
        // Displaying and showing the winnings.
        int winnings = GameDatabase.getInstance().getWinning();
        winningsLabel.setText("$" + winnings);

        // Iterating through each available category.
        for (int category = 0; category < 5; category++) {
            Category categoryObject = database.getCategory(category);
            Label title = new Label(categoryObject.getName());
            title.getStyleClass().add("category");
            title.getStylesheets().add(getClass().getClassLoader().getResource("quinzical/resources/styles/GameModule.css").toExternalForm());
            newZealandGrid.add(title, category, 0);

            // Making sure that the first clue is always clickable, hence using a flag.
            boolean active = true;

            List<Clue> clues = categoryObject.remainingClue();
            int startingRow = 6 - clues.size();

            // Iterating through each remaining clues within the category.
            for (int clue = 1; clue < clues.size() + 1; clue++) {
                Clue clueObject = categoryObject.getClue(clue - 1);
                Button clueButton = clueButtonGenerator(clueObject);

                if (!active) {
                    clueButton.setDisable(true);
                }
                active = false;

                // Setting the listener for the current button.
                clueBinder(clueButton, clueObject, categoryObject);
                newZealandGrid.add(clueButton, category, startingRow);
                startingRow++;
            }
        }
    }

    /**
     * This private method is used to setup the international 5x5 grid system.
     */
    private void setupInternationalGrid() {
        Category internationCategory = database.getInternationalCategory();
        List<Clue> internationalClues = internationCategory.remainingClue();

        boolean active = true;
        int startingRow = 6 - internationalClues.size();

        // Iterating through each remaining clue, and creating a button for each.
        for (int clue = 1; clue < internationalClues.size() + 1; clue++) {
            Clue clueObject = internationalClues.get(clue - 1);
            Button clueButton = clueButtonGenerator(clueObject);

            if (!active) {
                clueButton.setDisable(true);
            }
            active = false;

            // Setting the listener for the current button.
            clueBinder(clueButton, clueObject, internationCategory);
            internationalGrid.add(clueButton, 0, startingRow);
            startingRow++;
        }
    }

    /**
     * This method is used to setup the toggle buttons for NZ and International Sections.
     */
    private void setupToggleButtons() {
        // Setting listener so when the New Zealand section is clicked, it is shown on the grid.
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

        // Setting listener so when the International section is clicked, it is shown on the grid.
        internationalButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                newZealandButton.setSelected(false);
                tabPane.getSelectionModel().select(internationalTab);
            } else {
                newZealandButton.setSelected(true);
            }
        });
    }

    /**
     * This method is used to generates buttons for us to reduce the code-duplication.
     * @param clue the clue for which we want to create a button.
     * @return Button the resulting button.
     */
    private Button clueButtonGenerator(Clue clue) {
        Button clueButton = new Button("$" + clue.getPrize());
        clueButton.getStyleClass().add("clue");
        clueButton.getStylesheets().add(getClass().getClassLoader().getResource("quinzical/resources/styles/GameModule.css").toExternalForm());
        return clueButton;
    }

    /**
     * This method is used to bind a listener onto a clue button (also used to reduce the amount of
     * code that is duplicated. The bound listener would open the question screen when clicked on.
     * @param button the button on which we want to bind our listener.
     * @param clue the clue which should open when clicked on the button.
     * @param category the category of the clue above.
     */
    private void clueBinder(Button button, Clue clue, Category category) {
        button.setOnAction(event -> {
            database.setCurrentClue(clue);
            category.nextQuestion();
            switcher.switchTo(ScreenType.GAME_CLUE);
        });
    }

}
