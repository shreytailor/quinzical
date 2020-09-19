package a3.quinzical;

import a3.quinzical.frontend.controllers.ScreenController;

// JavaFX dependencies.
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


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
        stage.setTitle("Quinzical");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            /*
            --------------------------------------------------------
            THIS IS WHERE THE PROGRESS SAVING CODE WILL BE PLACED.
            --------------------------------------------------------
             */
            System.out.println("Window has been closed.");
        });

        // Using the custom created SceneController class to manage all and setup all the screens.
        ScreenController screenController = ScreenController.initialize(stage);
        screenController.addScreen("MAIN_MENU", FXMLLoader.load(getClass().getResource("./frontend/fxml/MainMenu.fxml")));
        screenController.setScreen("MAIN_MENU");

        // Showing the stage to the user.
        stage.show();
    }

}