package a3.quinzical.frontend.helper;

// Java dependencies.
import java.io.IOException;

// JavaFX dependencies.
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;


/**
 * This class is for the custom controller created to take care of the routing done within our
 * JavaFX application. It uses a slightly tweaked version of the Singleton pattern.
 * @author Shrey Tailor, Jason Wang
 */
public class ScreenSwitcher {

    // Static context fields.
    private static ScreenSwitcher _screenSwitcher;

    // Non-static (object) context fields.
    private Stage _mainStage;
    private Scene _mainScene;

    /**
     * This is the private constructor of our Singleton object.
     * @param stage the main stage of the game.
     */
    private ScreenSwitcher(Stage stage) {
        _mainStage = stage;
        ScreenType mainMenu = ScreenType.MAIN_MENU;

        try {
            Pane pane = FXMLLoader.load(mainMenu.getUrl());
            _mainScene = new Scene(pane, 1350, 750);
            _mainStage.setScene(_mainScene);
            setTitle("Main Menu");
        } catch (IOException error) {  };
    }

    /**
     * This method is used at the very beginning of the game in the Main class to initialize a
     * Singleton object with the main stage of our game.
     * @param stage the main stage of the game.
     * @return ScreenController the Singleton object which was created.
     */
    public static ScreenSwitcher initialize (Stage stage) {
        if (_screenSwitcher == null) {
            _screenSwitcher = new ScreenSwitcher(stage);
        }

        return _screenSwitcher;
    }

    /**
     * This method is used to get the instance of our Singleton object from anywhere in the game.
     * @return ScreenController the Singleton object.
     */
    public static ScreenSwitcher getInstance() {
        return _screenSwitcher;
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
     * This method is used to set one of the previously added screens to the game's stage.
     * @param screenTypeName the name of the screen by which you saved it before.
     */
    private void setScreen(ScreenType screenTypeName) {
        try {
            Pane pane = FXMLLoader.load(screenTypeName.getUrl());
            _mainScene.setRoot(pane);
        } catch (IOException error) {
            System.out.println("There was a problem in the FXML file.");
        }
    }

    /**
     * This is the client method for the front-end which enables the user to easily switch
     * to a new screen.
     * @param screenType the type of screen.
     */
    public void switchTo(ScreenType screenType) {
        setScreen(screenType);
    }

    /**
     * This method is used to set the header of the stage, once the parent of the scene is changed.
     * @param title the title that you want to give to the stage.
     */
    public void setTitle(String title) {
        _mainStage.setTitle("Quinzical | " + title);
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