package a3.quinzical.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import a3.quinzical.backend.tasks.FileManager;
import a3.quinzical.backend.tasks.Formatting;

public class Progression {
	
	// Fields belonging to the static context.
	private static Progression progression;
	
	// Fields for calculation
	private int EXP;
	private int gamesCompleted;
	private int totalWinnings;
	private int answeredCorrect;
	private int answeredWrong;
	private int totalTime;
	
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
					gamesCompleted = Formatting.formatProgression(line);
				}else if(line.startsWith("TW:")){
					totalWinnings = Formatting.formatProgression(line);
				}else if(line.startsWith("AC:")){
					answeredCorrect = Formatting.formatProgression(line);
				}else if(line.startsWith("AW:")){
					answeredWrong = Formatting.formatProgression(line);
				}else if(line.startsWith("TT:")){
					totalTime = Formatting.formatProgression(line);
				}else if(line.startsWith("EXP:")){
					EXP = Formatting.formatProgression(line);
				}
			}
		}else {
			// Starting values
			gamesCompleted = 0;
			totalWinnings = 0;
			answeredCorrect = 0;
			answeredWrong = 0;
			totalTime = 0;
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
        	progression = new Progression();
        }
        return progression;
    }
	
	/**
     * This method is used to check if an instance of Progression exist
     * @return if the Progression singleton exist
     */
    public static boolean singletonExist() {
    	return (progression != null);
    }
	
    /**
     * This method is used to advance the number of Games completed.
     */
	private void gamesCompletedPlus() {
		gamesCompleted++;
	}
	
	public int getGamesCompleted() {
		return gamesCompleted;
	}
	
	/**
	 * This method is used when a game is finished, it increments the number of games finished and
	 * the total winnings throughout games.
	 * @param winning the winning of the game
	 */
	public void gameFinished(int winning) {
		gamesCompletedPlus();
		totalWinnings += winning;
	}
	
	/**
	 * This method is used when a question is answered correctly, it increments the number of questions
	 * answered correctly and increases the time left when answering the question.
	 * @param time time left when answering the question
	 */
	public void answeredCorrect(int time) {
		answeredCorrectPlus();
		totalTime += time;
	}
	
	/**
	 * This method is used to advance the number of correctly answered questions.
	 */
	private void answeredCorrectPlus() {
		answeredCorrect++;
	}
	
	/**
	 * This method is used to advance the number of incorrectly answered questions.
	 */
	public void answeredWrongPlus() {
		answeredWrong++;
	}
	
	/**
	 * This method is used to find the percentage of questions that were answered correctly
	 * @return int percentage of questions that were answered correctly
	 */
	public int getPercentage() {
		return answeredCorrect * 100 / (answeredCorrect+answeredWrong);
	}
	
	/**
	 * This method is used to calculate the average time taken to answer a question correctly
	 * @return int average time taken
	 */
	public int getAverageTime() {
		return totalTime / answeredCorrect;
	}
	
	/**
	 * This method is used to calculate the average winning the player get each game
	 * @return int average winning
	 */
	public int getAverageWinning() {
		return totalWinnings / (gamesCompleted);
	}
	
	/**
	 * This method is used to add the EXP gained by the player
	 * @param i EXP gained
	 */
	public void addEXP(int i) {
		EXP += i;
	}

	public int getEXP() {
		return EXP;
	}
	
	/**
	 * This method is used to get a list of the field values in Progression object
	 * @return list of the field values
	 */
	public List<Integer> getStatsList(){
		List<Integer> statsList = new ArrayList<Integer>();
		statsList.add(gamesCompleted);
		statsList.add(totalWinnings);
		statsList.add(answeredCorrect);
		statsList.add(answeredWrong);
		statsList.add(totalTime);
		statsList.add(EXP);
		return statsList;
	}
	
	/**
	 * This method is used to get a list of the field names for writing Progression file
	 * @return list of the field names
	 */
	public List<String> getFieldsList(){
		List<String> filedsList = new ArrayList<String>();
		filedsList.add("GC:");
		filedsList.add("TW:");
		filedsList.add("AC:");
		filedsList.add("AW:");
		filedsList.add("EXP:");
		return filedsList;
	}
	
	/**
	 * This method is used to dereference the singleton object and romove the progression file.
	 */
	public static void kill() {
		progression = null;
		try {
			Files.deleteIfExists(FileManager.getProgFile().toPath());
		} catch (IOException e) {
		}
	}
}
