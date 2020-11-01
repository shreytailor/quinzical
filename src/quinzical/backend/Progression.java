package quinzical.backend;
import quinzical.backend.tasks.FileManager;
import quinzical.backend.tasks.Formatting;

// Java dependencies.
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Progression {
	// Static field(s).
	private static Progression progression;
	
	// Non-static field(s).
	private int EXP;
	private int totalTime;
	private int totalWinnings;
	private int answeredWrong;
	private int gamesCompleted;
	private int answeredCorrect;

	/**
	 * This private constructor is used to setup the singleton object for this class.
	 */
	private Progression() {
		// Initially, we are checking if the Statistics file does exist for the user.
		if (FileManager.progFileExist()) {
			List<String> progressList;
			try {
				progressList = IO.readFile(FileManager.getProgFile());

				// Depending on the line, populate the parameters for the different statistics.
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
			} catch (IOException e) {
				System.out.println("Failed to load the file for Statistics because of some problem. Please contact the developer.");
			}
		} else {
			// If the statistics file didn't exist, then we are starting from fresh.
			gamesCompleted = 0;
			totalWinnings = 0;
			answeredCorrect = 0;
			answeredWrong = 0;
			totalTime = 0;
		}
	}
	
	/**
     * This method is used to return the singleton object of the Progression object.
     * @return Progression the singleton object for this class.
     */
    public static Progression getInstance() {
        if (progression == null) {
        	progression = new Progression();
        }
        return progression;
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
	 * This method is used to find the percentage of questions that were answered correctly.
	 * @return int percentage of questions that were answered correctly.
	 */
	public int getPercentage() {
		if (answeredCorrect+answeredWrong == 0) {
			return 0;
		}
		return answeredCorrect * 100 / (answeredCorrect+answeredWrong);
	}
	
	/**
	 * This method is used to calculate the average time taken to answer a question correctly.
	 * @return int average time taken to answer a question correctly.
	 */
	public int getAverageTime() {
		if (answeredCorrect == 0) {
			return 0;
		}
		return totalTime / answeredCorrect;
	}
	
	/**
	 * This method is used to calculate the average winning the player get each game
	 * @return int average winning of the player.
	 */
	public int getAverageWinning() {
		if (gamesCompleted == 0) {
			return 0;
		}
		return totalWinnings / (gamesCompleted);
	}
	
	/**
	 * This method is used to add the EXP gained by the player
	 * @param xp EXP gained
	 */
	public void addEXP(int xp) {
		EXP += xp;
	}

	public int getEXP() {
		return EXP;
	}
	
	/**
	 * This method is used to get a list of the statistics to write to the local machine.
	 * @return List<Integer> list of the statistics.
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
	 * This method is used to get a list of the field names for writing to the local machine.
	 * @return List<String> list of the field names
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
	
	/**
	 * This method is used to dereference the singleton object, and remove the progression file.
	 */
	public static void kill() {
		progression = null;
		try {
			Files.deleteIfExists(FileManager.getProgFile().toPath());
		} catch (IOException e) {
		}
	}
}
