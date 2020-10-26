package quinzical.frontend.controllers;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private final PracticeDatabase _db = PracticeDatabase.getInstance();
    private final ScreenSwitcher _switcher = ScreenSwitcher.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGrid();
    }

    @FXML
    private void handleBackButton() {
        _switcher.switchTo(ScreenType.MAIN_MENU);
        _switcher.setTitle("Main Menu");
    }

    /**
     * This is a private method which is used by the initialize() method in this class, in order to
     * setup the GridPane programmatically. Here, we are reading the categories from the Databases
     * backend, and trying to populate the GUI with their respective buttons.
     */
    private void setupGrid() {
        GridPane _gridPane = new GridPane();
        int categories = _db.getCateSize();

        // Determine the rows and columns of the GridPane.
        int ROWS = 5;
        int COLS = (categories / ROWS) + 1;

        int tracker = 0;

        // Loop through the columns and rows of the GridPane and add the buttons.
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                // If we finish all the categories, then finish both the loops.
                if (tracker >= categories) {
                    break;
                }

                // Getting the information of the current category and creating its button.
                String category = _db.getCategory(tracker).getName();
                Button button = new Button(category);
                button.setWrapText(true);
                button.getStyleClass().add("categoryButton");
                button.getStylesheets().add(getClass().getClassLoader().getResource("quinzical/frontend/styles/PracticeModule.css").toExternalForm());

                // Applying different styles to mark the category that needs to be practised.
                Category marked = _db.getMarkedCategory();
                if (marked != null && category.equals(marked.getName())) {
                    button.getStyleClass().add("markedButton");
                }

                int finalTracker = tracker;
                button.setOnAction(action -> {
                    handleCategoryButton(finalTracker);
                });

                GridPane.setMargin(button, new Insets(12));

                // Adding the button to the grid.
                _gridPane.add(button, row, col);
                tracker++;
            }
        }

        // After GridPane is built, we're adding it to the parent ScrollPane, and then centering.
        scrollPane.setContent(_gridPane);
        _gridPane.translateXProperty().bind(scrollPane.widthProperty().subtract(_gridPane.widthProperty()).divide(2));
    }

    /**
     * This is a private method, and a handler for when the one of the categories is selected.
     * @param categoryNumber the category number which is selected by the user.
     */
    private void handleCategoryButton(int categoryNumber) {
        PracticeDatabase db = PracticeDatabase.getInstance();
        Clue random = db.getCategory(categoryNumber).getRandom();
        PracticeDatabase.getInstance().select(random);
        _switcher.switchTo(ScreenType.PRACTICE_CLUE);
    }

}