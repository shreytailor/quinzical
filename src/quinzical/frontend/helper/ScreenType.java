package quinzical.frontend.helper;

import java.net.URL;

/**
 * This enum class was created so that we can use some type-safety throughout the application
 * regarding what screens we are using. Another approach could have been using just strings to name
 * the different screens, but this seems to be a safer approach.
 * @author Shrey Tailor, Jason Wang
 */
public enum ScreenType {
    MAIN_MENU("MainMenu.fxml"),
    PRACTICE_MODULE("PracticeModule.fxml"),
    PRACTICE_CLUE("PracticeClue.fxml"),
    CHOOSE_CATEGORIES("ChooseCategories.fxml"),
    GAME_MODULE("GameModule.fxml"),
    GAME_CLUE("GameClue.fxml"),
    GAME_FINISHED("GameFinished.fxml"),
    STATS("Statistics.fxml"),
    HELP("Help.fxml");

    private URL _url;
    private String _prefix = "quinzical/resources/fxml/";

    /**
     * This method is returning the path from the root of the project, which leads to the FXML file.
     * @param sceneName the name of the FXML file.
     */
    ScreenType(String sceneName) {
        _url = getClass().getClassLoader().getResource(_prefix + sceneName);
    }

    /**
     * This method is used to get the URL of the FXML file that was previously set.
     * @return URL the URL to the FXML file.
     */
    public URL getUrl() {
        return _url;
    }

}