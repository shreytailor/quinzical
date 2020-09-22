package a3.quinzical.frontend.controllers;

// JavaFX dependencies.
import a3.quinzical.backend.GameDatabase;
import a3.quinzical.backend.PracticeDatabase;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;

// Java dependencies.
import java.net.URL;
import java.util.ResourceBundle;

public class PracticeModuleController implements Initializable {

    private GridPane _gridPane;

    @FXML
    BorderPane root;
    @FXML
    Button backButton;
    @FXML
    ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupGrid();
    }

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

    private void setupGrid() {
        _gridPane = new GridPane();

        PracticeDatabase database = PracticeDatabase.getInstance();
        int categories = database.getCateSize();

        System.out.println(categories);

        int ROWS = 6;
        int COLS = (categories / ROWS) + 1;

        int tracker = 0;
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (tracker >= categories) {
                    break;
                }

                String category = database.getCategory(tracker).getName();
                Button button = new Button(category);
                button.setPrefWidth(195);
                button.setPrefHeight(90);
                GridPane.setMargin(button, new Insets(20, 10, 20, 10));
                _gridPane.add(button, row, col);

                tracker++;
            }
        }

        scrollPane.setContent(_gridPane);
    }

}