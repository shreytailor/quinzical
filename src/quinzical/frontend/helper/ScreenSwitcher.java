package quinzical.frontend.helper;

// Java dependencies.
import java.io.IOException;

// JavaFX dependencies.
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

/**
 * This class is a custom manager class which takes care of the routing done within our game. It
 * uses a slightly tweaked version of the Singleton pattern to function properly.
 * @author Shrey Tailor, Jason Wang
 */
public class ScreenSwitcher {
    // Static context fields.
    private static ScreenSwitcher screenSwitcher;

    // Non-static (object) context fields.
    private Stage mainStage;
    private Scene mainScene;

    /**
     * This is the private constructor of this class to initialize the singleton object. It is
     * accessed by the initialize() public method in this class.
     * @param stage the main stage of the game.
     */
    private ScreenSwitcher(Stage stage) {
        mainStage = stage;
        ScreenType mainMenu = ScreenType.MAIN_MENU;

        try {
            // Loading the main screen onto the stage initially.
            Pane pane = FXMLLoader.load(mainMenu.getUrl());
            mainScene = new Scene(pane, 1200, 670);
            mainStage.setScene(mainScene);
            setTitle("Main Menu");
        } catch (IOException error) {
            /*
                We can confirm that this error will never be thrown because the
                MainMenu.fxml file accessed by ScreenType class is always going
                to be present within the .jar file
             */
        };
    }

    /**
     * This method is used at the beginning to initialize the singleton object.
     * @param stage the main stage of the game.
     * @return ScreenController the singleton object that was initialized.
     */
    public static ScreenSwitcher initialize (Stage stage) {
        if (screenSwitcher == null) {
            screenSwitcher = new ScreenSwitcher(stage);
        }

        return screenSwitcher;
    }

    /**
     * This method is used to get the instance of our Singleton object in the game.
     * @return ScreenController the initialized singleton object.
     */
    public static ScreenSwitcher getInstance() {
        return screenSwitcher;
    }

    /**
     * This private method is used by the switchTo() method within this class to set a given
     * scene to the main stage.
     * @param screenTypeName the name of the screen by which it's know by the system.
     */
    private void setScreen(ScreenType screenTypeName) {
        try {
            // Load the desired screen to the primary stage.
            Pane pane = FXMLLoader.load(screenTypeName.getUrl());
            mainScene.setRoot(pane);
        } catch (IOException error) {
            // If there is any error with the FXML, print the stack and message.
            System.out.println("There was a problem in the FXML file.");
            error.printStackTrace();
        }
    }

    /**
     * This is the client method which enables the user to easily switch to a new screen.
     * @param screenType the type of screen.
     */
    public void switchTo(ScreenType screenType) {
        setScreen(screenType);
    }

    /**
     * This method is used to exit the game, by firing an exit event to the stage of the game.
     */
    public void exit() {
        mainStage.fireEvent(new WindowEvent(mainStage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * This method is used to set the header of the stage, once the parent of the scene is changed.
     * @param title the title that you want to give to the stage.
     */
    public void setTitle(String title) {
        mainStage.setTitle("Quinzical | " + title);
    }

    public Stage getStage() {
        return mainStage;
    }
}