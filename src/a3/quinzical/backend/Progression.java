package a3.quinzical.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import a3.quinzical.backend.tasks.FileManager;
import a3.quinzical.backend.tasks.Formatting;

public class Progression {
	
	// Fields belonging to the static context.
	private static Progression _progression;
	
	// Fields for calculation
	private int _EXP;
	private int _gamesCompleted;
	private int _totalWinnings;
	private int _answeredCorrect;
	private int _answeredWrong;
	private int _totalTime;
	
	/**
	 * The only constructor for Progression class, which is private, because it can only be
     * accessed by the getInstance method (according to the principles of Singleton pattern).
     * It will check if the Progression file exist and read from it, if not then start a new one.
	 */
	private Progression() {
		if(FileManager.progFileExist()) {
			List<String> progressList = IO.readFile(FileManager.getProgFile());
			for(String line : progressList) {
				if(line.startsWith("GC:")) {
					_gamesCompleted = Formatting.formatProgression(line);
				}else if(line.startsWith("TW:")){
					_totalWinnings = Formatting.formatProgression(line);
				}else if(line.startsWith("AC:")){
					_answeredCorrect = Formatting.formatProgression(line);
				}else if(line.startsWith("AW:")){
					_answeredWrong = Formatting.formatProgression(line);
				}else if(line.startsWith("TT:")){
					_totalTime = Formatting.formatProgression(line);
				}else if(line.startsWith("EXP:")){
					_EXP = Formatting.formatProgression(line);
				}
			}
		}else {
			// Starting values
			_gamesCompleted = 0;
			_totalWinnings = 0;
			_answeredCorrect = 0;
			_answeredWrong = 0;
			_totalTime = 0;
		}
	}
	
	/**
     * This method is used to return the singleton object of the Progression object.
     * If the Progression is not initialized this method will create a Progression 
     * object.
     * @return Progression the instance of class Progression.
     */
    public static Progression getInstance() {
        if (!singletonExist()) {
        	_progression = new Progression();
        }
        return _progression;
    }
	
	/**
     * This method is used to check if an instance of Progression exist
     * @return if the Progression singleton exist
     */
    public static boolean singletonExist() {
    	return (_progression != null);
    }
	
    /**
     * This method is used to advance the number of Games completed.
     */
	public void gamesCompletedPlus() {
		_gamesCompleted++;
	}
	
	/**
	 * This method is used to get the number of Games completed.
	 * @return int the number of Games completed
	 */
	public int getGamesCompleted() {
		return _gamesCompleted;
	}
	
	/**
	 * This method is used to advance the number of correctly answered questions.
	 */
	public void answeredCorrectPlus() {
		_answeredCorrect++;
	}
	
	/**
	 * This method is used to advance the number of incorrectly answered questions.
	 */
	public void answeredWrongPlus() {
		_answeredWrong++;
	}
	
	/**
	 * This method is used to find the percentage of questions that were answered correctly
	 * @return int percentage of questions that were answered correctly
	 */
	public int getPercentage() {
		return _answeredCorrect * 100 / (_answeredCorrect+_answeredWrong);
	}
	
	/**
	 * This method is used to add the time taken to answer the questions
	 * @param t amount of time to add
	 */
	public void addTime(int t) {
		_totalTime += t;
	}
	
	/**
	 * This method is used to calculate the average time taken to answer a question correctly
	 * @return int average time taken
	 */
	public int getAverageTime() {
		return _totalTime / _answeredCorrect;
	}
	
	/**
	 * This method is used to add the total winning throughout the game sessions
	 * @param w winnings to add
	 */
	public void addWinning(int w) {
		_totalWinnings += w;
	}
	
	/**
	 * This method is used to calculate the average winning the player get each game
	 * @return int average winning
	 */
	public int getAverageWinning() {
		return _totalWinnings / (_gamesCompleted);
	}
	
	/**
	 * This method is used to add the EXP gained by the player
	 * @param i EXP gained
	 */
	public void addEXP(int i) {
		_EXP += i;
	}
	
	/**
	 * This method is used to get the EXP the player has
	 * @return int EXP
	 */
	public int getEXP() {
		return _EXP;
	}
	
	/**
	 * This method is used to get a list of the field values in Progression object
	 * @return list of the field values
	 */
	public List<Integer> getStatsList(){
		List<Integer> statsList = new ArrayList<Integer>();
		statsList.add(_gamesCompleted);
		statsList.add(_totalWinnings);
		statsList.add(_answeredCorrect);
		statsList.add(_answeredWrong);
		statsList.add(_totalTime);
		statsList.add(_EXP);
		return statsList;
	}
	
	/**
	 * This method is used to get a list of the field names for writing Progression file
	 * @return list of the field names
	 */
	public List<String> getFieldsList(){
		List<String> fieldsList = new ArrayList<String>();
		fieldsList.add("GC:");
		fieldsList.add("TW:");
		fieldsList.add("AC:");
		fieldsList.add("AW:");
		fieldsList.add("TT:");
		fieldsList.add("EXP:");
		return fieldsList;
	}
	
	public static void kill() {
		_progression = null;
		try {
			Files.deleteIfExists(FileManager.getProgFile().toPath());
		} catch (IOException e) {
		}
	}
}
