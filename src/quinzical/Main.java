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
        ---------------------- NOTE TO MARKER ABOUT PACKAGE LEVEL DECISIONS ----------------------
        It would have been much easier to place the "resources" on the same level as the "quinzical"
        package, as this would give much shorter paths for the resources which are being used.

        However in the announcements made by Nasser, it was mentioned that we should have a single
        top-level package, and thus we had made a decision to place "resources" within "quinzical".
         */

        // Checking if the configuration folder exists.
        Progression.getInstance();
        FileManager.checkConfigDirectory();

        /*
        If the game file exists (i.e. the user has started a game), then populate the database from
        that file which exists.
         */
        if (FileManager.gameFileExist()) {
            GameDatabase.getInstance();
        }

        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            cleanupOnClose();
        });

        // Initialize the singleton switcher with the current stage so that it manages it.
        ScreenSwitcher.initialize(stage);
        stage.show();
    }

    /**
     * This method is used to clean-up the current session of the game by doing things such as
     * stopping the speaker instance, saving the current user progress and other things.
     */
    private static void cleanupOnClose() {
        /*
            If there is user progress within the current session of the game, then offload it into
            the .config directory, so it can be loaded back next time.
             */
        if (GameDatabase.singletonExist()) {
            try {
                IO.writeGameData(GameDatabase.getInstance());
                IO.writeProgressionData(Progression.getInstance());
            } catch (IOException error) {
                // We can be assured that this will not be thrown due to such implementation.
            };
        }

        // Stop the speaker before closing the application, so user isn't confused.
        Speaker.init().kill();
        System.exit(0);
        Platform.exit();
    }
}