package a3.quinzical;

import a3.quinzical.backend.Speaker;
import a3.quinzical.backend.Database.PracticeDatabase;
import a3.quinzical.frontend.switcher.ScreenType;
import a3.quinzical.frontend.switcher.ScreenSwitcher;

// JavaFX dependencies.
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.application.Platform;
import javafx.application.Application;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("./frontend/resources/logo.png")));
        stage.setOnCloseRequest(event -> {
            /*
            --------------------------------------------------------
            THIS IS WHERE THE PROGRESS SAVING CODE WILL BE PLACED.
            --------------------------------------------------------
             */
            Speaker.init().kill();
            Platform.exit();
        });

        ScreenSwitcher screenSwitcher = ScreenSwitcher.initialize(stage);
        screenSwitcher.addScreen(ScreenType.PRACTICE_MODULE, FXMLLoader.load(getClass().getResource("./frontend/fxml/PracticeModule.fxml")));
        screenSwitcher.addScreen(ScreenType.GAME_MODULE, FXMLLoader.load(getClass().getResource("./frontend/fxml/GameModule.fxml")));

        stage.show();
    }

}