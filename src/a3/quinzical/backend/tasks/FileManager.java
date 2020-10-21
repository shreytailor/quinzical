package a3.quinzical.backend.tasks;

import java.io.File;

/**
 * This class is used to help manage and check the files needed to run the program.
 *
 * @author Shrey Tailor, Jason Wang
 */
public class FileManager {
	private final static File _gameFile = new File(System.getProperty("user.dir")+"/.config/GameData");
	private final static File _configDirectory = new File(System.getProperty("user.dir")+"/.config/");
	private final static File _quizFile = new File(System.getProperty("user.dir")+"/Quinzical.txt");
    private final static File _intFile = new File(System.getProperty("user.dir")+"/International.txt");
    
    /**
     * This method is used to check if the GameData file exist
     * @return if the GameData file exist
     */
    public static boolean gameFileExist() {
    	return (_gameFile.exists() && _gameFile.isFile());
    }
    
	/**
	 * This method will check if the configuration folder exists, if not then 
	 * the folder will be created.
	 */
	public static void checkConfigDirectory() {
		if(!_configDirectory.exists()) {
			_configDirectory.mkdir();
		}
	}
	
	/**
	 * This is a method that will return where the GameData.txt is supposed to be saved.
	 * @return the file of GameData
	 */
	public static File getGameFile() {
		return _gameFile;
	}
	
	/**
	 * This is a method that will return where the GameData.txt is supposed to be saved.
	 * @return the file of GameData
	 */
	public static File getQuizFile() {
		return _quizFile;
	}
	
	/**
	 * This is a method that will return where the GameData.txt is supposed to be saved.
	 * @return the file of GameData
	 */
	public static File getInternationalFile() {
		return _intFile;
	}
}
