package a3.quinzical.frontend.controllers;

// JavaFX dependencies.
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class ScreenController {

    private static ScreenController _screenController;

    private Stage _mainStage;
    private HashMap<String, Pane> _screenMap = new HashMap<>();

    private ScreenController(Stage stage) {
        _mainStage = stage;
    }

    public static ScreenController getInstance(Stage stage) {
        if (_screenController == null) {
            _screenController = new ScreenController(stage);
        }

        return _screenController;
    }

    public void addScreen(String screenName, Pane pane) {
        _screenMap.put(screenName, pane);
    }

    public void deleteScreen(String screenName) {
        _screenMap.remove(screenName);
    }

    public void setScreen(String screenName) {
        _mainStage.setScene(new Scene(_screenMap.get(screenName), 1350, 750));
    }

}
