package a3.quinzical.frontend.controllers;

// JavaFX dependencies.
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;


/**
 * This class is for the custom controller created to take care of the routing done within our
 * JavaFX application. It uses a slightly tweaked version of the Singleton pattern.
 * @author Shrey Tailor, Jason Wang
 */

public class ScreenController {

    // Static context fields.
    private static ScreenController _screenController;

    // Non-static (object) context fields.
    private Stage _mainStage;
    private HashMap<String, Pane> _screenMap = new HashMap<>();

    /**
     * This is the private constructor of our Singleton object.
     * @param stage the main stage of the game.
     */
    private ScreenController(Stage stage) {
        _mainStage = stage;
    }

    /**
     * This method is used at the very beginning of the game in the Main class to initialize a
     * Singleton object with the main stage of our game.
     * @param stage the main stage of the game.
     * @return ScreenController the Singleton object which was created.
     */
    public static ScreenController initialize(Stage stage) {
        if (_screenController == null) {
            _screenController = new ScreenController(stage);
        }

        return _screenController;
    }

    /**
     * This method is used to get the instance of our Singleton object from anywhere in the game.
     * @return ScreenController the Singleton object.
     */
    public static ScreenController getInstance() {
        return _screenController;
    }

    /**
     * This method is used to get the main stage of the application. This is particularly needed
     * when making the main window inactive as the Settings pane could be opened by the user.
     * @return Stage the main stage for our application.
     */
    public Stage getStage() {
        return _mainStage;
    }

    /**
     * This method is used to add a screen (pane) to the available screens, which are stored as a
     * map in one of the non-static fields.
     * @param screenName the name of the screen.
     * @param pane the FXML pane which is stored within one of the packages.
     */
    public void addScreen(String screenName, Pane pane) {
        _screenMap.put(screenName, pane);
    }

    /**
     * This method is used to delete some screen (pane) from the available ones.
     * @param screenName the name of the screen by which you saved it before.
     */
    public void deleteScreen(String screenName) {
        _screenMap.remove(screenName);
    }

    /**
     * This method is used to set one of the previously added screens to the game's stage.
     * @param screenName the name of the screen by which you saved it before.
     */
    public void setScreen(String screenName) {
        _mainStage.setScene(new Scene(_screenMap.get(screenName), 1350, 750));
    }

    /**
     * This method is used to exit the game, by firing an exit event to the stage of the game. Note
     * that after this is executed, the listener in the Main class is executed, where all the logic
     * regarding saving the game is located.
     */
    public void exit() {
        _mainStage.fireEvent(new WindowEvent(_mainStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
