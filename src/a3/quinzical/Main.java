package a3.quinzical;

import a3.quinzical.backend.Speaker;
import a3.quinzical.frontend.controllers.ScreenController;

// JavaFX dependencies.
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.application.Application;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        /*
        Initial configuration added for stage such as setting the title, making it not resizeable,
        and also setting a listener to save the progress when the stage is closed.
         */
        stage.setResizable(false);
        stage.setTitle("Quinzical");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("./frontend/resources/logo.png")));
        stage.setOnCloseRequest(event -> {
            /*
            --------------------------------------------------------
            THIS IS WHERE THE PROGRESS SAVING CODE WILL BE PLACED.
            --------------------------------------------------------
             */
            System.out.println("Window has been closed.");

            /*
             This code is present so that if the settings stage was open when the main application
             is closed, then all the windows are automatically closed.
             */
            Platform.exit();
        });

        // Using the custom created SceneController class to manage all and setup all the screens.
        ScreenController screenController = ScreenController.initialize(stage);
        screenController.addScreen("MAIN_MENU", FXMLLoader.load(getClass().getResource("./frontend/fxml/MainMenu.fxml")));
        screenController.setScreen("MAIN_MENU");

        // Setting up the speaker with the default speed.
        Speaker.init();

        // Showing the stage to the user.
        stage.show();
    }

}