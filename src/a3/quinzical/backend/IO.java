package a3.quinzical.backend;
import a3.quinzical.backend.models.Clue;
import a3.quinzical.backend.models.Category;
import a3.quinzical.backend.database.GameDatabase;

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
 * @author Shrey Tailor, Jason Wang *
 */
public class IO {
	// Fields belonging to the static context.
	private final static File _configDirectory = new File(System.getProperty("user.dir")+"/.config/");
		
	/**
	 * This method is used to read a given file into a list of Strings
	 * the list of strings can then be processed to different databases.
	 * @param file the file to be read
	 * @return a list of strings that contains the data of the file
	 */
	public static List<String> readFile(File file){
		List<String> fileContent = new ArrayList<String>();
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while(( line = br.readLine()) != null) {
				fileContent.add(line.trim());
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Error reading file at: " + file.getPath());
			e.printStackTrace();
		}
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
		checkDirectory();
		Category writeCate = null;
		Clue writeClue = null;
		File gameFile = game.getFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(gameFile));
		bw.write(game.getWinning() + "\n\n");
		for(int i = 0; i < game.getCateSize(); i++) {
			writeCate = game.getCategory(i);
			bw.write(writeCate.getName() + "\n");
			for(int j = 0; j < writeCate.getClueSize(); j++) {
				writeClue = writeCate.getClue(j);
				bw.write(writeClue.getQuestion() + "|" + writeClue.getPrefix() + "|" + writeClue.getFullAnswer() + "|" + writeClue.getPrize() +"\n");
			}
			bw.write("\n");
		}
		bw.close();
	}
	
	/**
	 * This method will check if the configuration folder exists, if not then 
	 * the folder will be created.
	 */
	public static void checkDirectory() {
		if(!_configDirectory.exists()) {
			_configDirectory.mkdir();
		}
	}	
}