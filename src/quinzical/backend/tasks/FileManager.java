package quinzical.backend.tasks;

import java.io.File;

/**
 * This class is used to help manage and check the files needed to run the program.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class FileManager {
	private final static File GAME_FILE = new File(System.getProperty("user.dir")+"/.config/GameData");
	private final static File PROG_FILE = new File(System.getProperty("user.dir")+"/.config/Progression");
	private final static File CONFIG_DIRECTORY = new File(System.getProperty("user.dir")+"/.config/");
	private final static File QUIZ_FILE = new File(System.getProperty("user.dir")+"/quizBank/Quinzical.txt");
    private final static File INT_FILE = new File(System.getProperty("user.dir")+"/quizBank/International.txt");
    
    /**
     * This method is used to check if the GameData file exist
     * @return if the GameData file exist
     */
    public static boolean gameFileExist() {
    	return (GAME_FILE.exists() && GAME_FILE.isFile());
    }
    
    /**
     * This method is used to check if the Progression file exist
     * @return if the Progression file exist
     */
    public static boolean progFileExist() {
    	return (PROG_FILE.exists() && PROG_FILE.isFile());
    }
    
	/**
	 * This method will check if the configuration folder exists, if not then 
	 * the folder will be created.
	 */
	public static void checkConfigDirectory() {
		if(!CONFIG_DIRECTORY.exists()) {
			CONFIG_DIRECTORY.mkdir();
		}
	}
	
	/**
	 * This is a method that will return where the GameData is supposed to be saved.
	 * @return the file of GameData
	 */
	public static File getGameFile() {
		return GAME_FILE;
	}
	
	/**
	 * This is a method that will return where the Progression is supposed to be saved.
	 * @return the file of Progression
	 */
	public static File getProgFile() {
		return PROG_FILE;
	}
	
	/**
	 * This is a method that will return where the GameData.txt is supposed to be saved.
	 * @return the file of GameData
	 */
	public static File getQuizFile() {
		return QUIZ_FILE;
	}
	
	/**
	 * This is a method that will return where the GameData.txt is supposed to be saved.
	 * @return the file of GameData
	 */
	public static File getInternationalFile() {
		return INT_FILE;
	}
}
