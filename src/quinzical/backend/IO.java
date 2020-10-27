package quinzical.backend;
import quinzical.backend.models.Clue;
import quinzical.backend.models.Category;
import quinzical.backend.tasks.FileManager;
import quinzical.backend.database.GameDatabase;

//Java API dependencies.
import java.io.File;
import java.util.List;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

/**
 * This class is used to read/write the database of the game
 * 
 * @author Shrey Tailor, Jason Wang
 */
public class IO {
	/**
	 * This method is used to read a given file into a list of Strings
	 * the list of strings can then be processed to different databases.
	 * @param file the file to be read
	 * @return a list of strings that contains the data of the file
	 * @throws IOException 
	 */
	public static List<String> readFile(File file) throws IOException{
		List<String> fileContent = new ArrayList<String>();
		String line;
		// reading lines into a list of Strings
		BufferedReader br = new BufferedReader(new FileReader(file));
		while(( line = br.readLine()) != null) {
			fileContent.add(line.trim());
		}
		br.close();

		return fileContent;
	}
	
	/**
	 * This method is used to write a given GameDatabase into file to store the
	 * record of the game, including the winnings of the player, the categories
	 * and selectable questions, and the current question that the player is on.
	 * 
	 * @param game a GameDatabase object that is to be written into file.
	 * @throws IOException this exception is returned if the method failed to write
	 * the GameDatabase object into file.
	 */
	public static void writeGameData(GameDatabase game) throws IOException {
		FileManager.checkConfigDirectory();
		Category writeCate = null;
		Clue writeClue = null;
		File gameFile = FileManager.getGameFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(gameFile));
		String question, prefix, answer, prize;
		// Write winning
		bw.write(game.getWinning() + "\n\n");
		for(int i = 0; i < game.getCateSize() + 1; i++) {
			if(i == game.getCateSize()) {
				// write the International category last
				writeCate = game.getInternationalCategory();
			}else {
				// write the non-International category first
				writeCate = game.getCategory(i);
			}			
			bw.write(writeCate.getName() + "\n");
			for(int j = 0; j < writeCate.getClueSize(); j++) {
				writeClue = writeCate.getClue(j);
				// Extract data from the clue
				question = writeClue.getQuestion();
				prefix = writeClue.getPrefix();
				answer = writeClue.getFullAnswer();
				prize = Integer.toString(writeClue.getPrize());
				// write the data from the clue
				bw.write(question + "|" + prefix + "|" + answer + "|" + prize +"\n");
			}
			bw.write("\n");
		}
		bw.close();
	}
	
	/**
	 * This method is used to write a given Progression into file to store the
	 * record of progression, including several stats of the player throughout
	 * game sessions
	 * 
	 * @throws IOException this exception is returned if the method failed to write
	 * the Progression object into file.
	 */
	public static void writeProgressionData(Progression progression) throws IOException {
		FileManager.checkConfigDirectory();
		BufferedWriter bw = new BufferedWriter(new FileWriter(FileManager.getProgFile()));
		List<Integer> statsList = progression.getStatsList();
		List<String> fieldsList = progression.getFieldsList();
		
		for(int i = 0; i < statsList.size(); i++) {
			//writing each statistic into their own lines
			bw.write(fieldsList.get(i) + statsList.get(i) + "\n");
		}		
		bw.close();
	}
	
	/**
	 * This method is used to write a list of Strings to a given file
	 * @param list the list of String to be written
	 * @param fileLocation the destined location for the file
	 * @throws IOExceptionthis exception is returned if the method failed to write
	 * the Progression object into file. 
	 */
	public static void writeToFile(List<String> list, File fileLocation) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocation));
		for(String line : list) {
			// writing each string into individual lines
			bw.write(line);
		}
		bw.close();
	}	
}