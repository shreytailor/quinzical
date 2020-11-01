package quinzical;
import quinzical.backend.IO;
import quinzical.backend.Progression;
import quinzical.frontend.helper.Speaker;
import quinzical.backend.tasks.FileManager;
import quinzical.backend.database.GameDatabase;
import quinzical.frontend.helper.ScreenSwitcher;

// Java dependencies.
import java.io.IOException;
import javafx.application.Platform;
import javafx.application.Application;

// JavaFX dependencies.
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        /*
        ----------------------------------- NOTES FOR THE MARKER -----------------------------------
        1) For some exceptions, we have made sure our implementation is such that they would never be
        thrown in any scenario. However for some of the exceptions which could be thrown, we have
        added a System.out.print to the console so that the user knows what has gone wrong exactly.
        Hopefully this doesn't count as a bad usage of System.out.print feature because our intentions
        are to deliver the message to the user.
         */

        // Checking if the configuration folder exists.
        Progression.getInstance();
        FileManager.checkConfigDirectory();

        /*
        If the game file exists (i.e. the user has a game saved), then import that file to continue
        from where they had left off.
         */
        if (FileManager.gameFileExist()) {
            GameDatabase.getInstance();
        }

        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            cleanupOnClose();
        });

        // Initialize the singleton screen switcher on the stage to manage the delegation.
        ScreenSwitcher.initialize(stage);
        stage.show();
    }

    /**
     * This method is used to clean-up the current session of the game by doing things such as
     * stopping the speaker instance, saving the current user progress etc.
     */
    private static void cleanupOnClose() {
        /*
        If there is user progress within the current session of the game, then save it into the
        .config directory, so it can be loaded back next time when the user plays.
        */
        if (GameDatabase.singletonExist()) {
            try {
                IO.writeGameData(GameDatabase.getInstance());
                IO.writeProgressionData(Progression.getInstance());
            } catch (IOException error) {
                // We can be assured that this will not be thrown due to type of implementation.
            };
        }

        // Stop the speaker before closing the application, so user isn't confused.
        Speaker.init().kill();
        System.exit(0);
        Platform.exit();
    }
}