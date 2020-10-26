package quinzical.frontend.controllers;
import quinzical.backend.models.Clue;
import quinzical.backend.models.Category;
import quinzical.frontend.helper.ScreenType;
import quinzical.frontend.helper.ScreenSwitcher;
import quinzical.backend.database.PracticeDatabase;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

// JavaFX dependencies.
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;

/**
 * This class is the controller class for the Practice Module screen.
 * @author Shrey Tailor, Jason Wang
 */
public class PracticeModuleController implements Initializable {

    @FXML BorderPane root;
    @FXML Button backButton;
    @FXML ScrollPane scrollPane;

    private GridPane gridPane;
    private final ScreenSwitcher switcher = ScreenSwitcher.getInstance();
    private final PracticeDatabase database = PracticeDatabase.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGrid();
    }

    @FXML
    private void handleBackButton() {
        switcher.switchTo(ScreenType.MAIN_MENU);
        switcher.setTitle("Main Menu");
    }

    /**
     * This is a private method which is used by the initialize() method in this class, in order to
     * setup the GridPane programmatically. Here, we are reading the categories from the Databases
     * backend, and trying to populate the GUI with their respective buttons.
     */
    private void setupGrid() {
        gridPane = new GridPane();
        int categories = database.getCateSize();

        // Determine the rows and columns of the GridPane.
        int ROWS = 5;
        int COLS = (categories / ROWS) + 1;

        // Loop through the columns and rows of the GridPane and add the buttons.
        int tracker = 0;
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                // If we finish all the categories, then finish both the loops.
                if (tracker >= categories) {
                    break;
                }

                // Getting the information of the current category and creating its button.
                String category = database.getCategory(tracker).getName();
                Button button = buttonGenerator(category);

                // Applying different styles to mark the category that needs to be practised.
                Category marked = database.getMarkedCategory();
                if (marked != null && category.equals(marked.getName())) {
                    button.getStyleClass().add("markedButton");
                }

                // Configuring the button such that if pressed, it would open practice module for it.
                int finalTracker = tracker;
                button.setOnAction(action -> {
                    handleCategoryButton(finalTracker);
                });

                GridPane.setMargin(button, new Insets(12));

                // Adding the button to the grid.
                gridPane.add(button, row, col);
                tracker++;
            }
        }

        // After GridPane is built, we're adding it to the parent ScrollPane, and then centering.
        scrollPane.setContent(gridPane);
        gridPane.translateXProperty().bind(scrollPane.widthProperty().subtract(gridPane.widthProperty()).divide(2));
    }

    /**
     * This is a private method, and a handler for when the one of the categories is selected.
     * @param categoryNumber the category number which is selected by the user.
     */
    private void handleCategoryButton(int categoryNumber) {
        Clue random = database.getCategory(categoryNumber).getRandom();
        database.select(random);
        switcher.switchTo(ScreenType.PRACTICE_CLUE);
    }

    private Button buttonGenerator(String categoryName) {
        Button button = new Button(categoryName);
        button.setWrapText(true);
        button.getStyleClass().add("categoryButton");
        button.getStylesheets().add(getClass().getClassLoader().getResource("quinzical/frontend/styles/PracticeModule.css").toExternalForm());
        return button;
    }

}