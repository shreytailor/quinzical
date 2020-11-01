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
 * This class is used to read/write the database of the game to the local machine.
 * 
 * @author Shrey Tailor, Jason Wang
 */
public class IO {
	/**
	 * This method is used to break the given file into individual lines of strings.
	 * @param file the file which is to be read
	 * @return List<String> a list of strings that contains each line of the file.
	 * @throws IOException if the file which is to be read is not found.
	 */
	public static List<String> readFile(File file) throws IOException{
		String line;
		List<String> fileContent = new ArrayList<String>();

		// Starting the process of reading the files.
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
	 * @param game a GameDatabase object that is to be written into file.
	 * @throws IOException if the method fails to write information to file.
	 */
	public static void writeGameData(GameDatabase game) throws IOException {
		Clue writeClue = null;
		Category writeCate = null;

		// Creating the file (if doesn't exist) and starting a writer to that file.
		FileManager.checkConfigDirectory();
		String question, prefix, answer, prize;
		File gameFile = FileManager.getGameFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(gameFile));

		// Firstly, write the winning to the file.
		bw.write(game.getWinning() + "\n\n");

		for (int i = 0; i < game.getCateSize() + 1; i++) {

			if (i == game.getCateSize()) {
				// Writing the information about the International category.
				writeCate = game.getInternationalCategory();
			} else {
				// Writing the information about the New Zealand category.
				writeCate = game.getCategory(i);
			}
			bw.write(writeCate.getName() + "\n");

			for (int j = 0; j < writeCate.getClueSize(); j++) {
				writeClue = writeCate.getClue(j);
				
				// Extracting the individual information about the clue.
				question = writeClue.getQuestion();
				prefix = writeClue.getPrefix();
				answer = writeClue.getFullAnswer();
				prize = Integer.toString(writeClue.getPrize());
				
				// Writing the data about the clue in the format below.
				bw.write(question + "|" + prefix + "|" + answer + "|" + prize +"\n");
			}
			bw.write("\n");
		}

		// Close the writer once everything has been written.
		bw.close();
	}
	
	/**
	 * This method is used to write a given Progression to a file, including several statistics of
	 * the player throughout the game sessions (permanent storage).
	 * @throws IOException if failure to write the statistics to the file.
	 */
	public static void writeProgressionData(Progression progression) throws IOException {
		// Check if the directory exists, if not create a new one.
		FileManager.checkConfigDirectory();

		// Creating a writer for the file.
		BufferedWriter bw = new BufferedWriter(new FileWriter(FileManager.getProgFile()));
		List<Integer> statsList = progression.getStatsList();
		List<String> fieldsList = progression.getFieldsList();

		// Writing each statistic to the file, on individual lines.
		for(int i = 0; i < statsList.size(); i++) {
			bw.write(fieldsList.get(i) + statsList.get(i) + "\n");
		}
		bw.close();
	}
}