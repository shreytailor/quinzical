package a3.quinzical.backend;

//Java API dependencies.
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import a3.quinzical.backend.database.GameDatabase;
import a3.quinzical.backend.models.Category;
import a3.quinzical.backend.models.Clue;

/**
 * This class is used to read/write the database of the game
 * 
 * @author Shrey Tailor, Jason Wang *
 */
public class IO {
	
	// Fields belonging to the static context.
	private final static File _gameFile = new File(System.getProperty("user.dir")+"/GameData.txt");
		
	/**
	 * This method is used to write a given GameDatabase into file to store the
	 * record of the game, including the winnings of the player, the categories
	 * and selectable questions, and the current question that the player is on.
	 * 
	 * @param game a GameDatabase object that is to be written into file.
	 * @throws IOException this exception is returned if the method failed to write
	 * the GameDatabase object into file.
	 */
	public static void writeGameData(GameDatabase game) throws IOException{
		Category writeCate = null;
		Clue writeClue = null;
		BufferedWriter bw = new BufferedWriter(new FileWriter(_gameFile));
		bw.write(game.getWinning() + "\n\n");
		for(int i = 0; i < game.getCateSize(); i++) {
			writeCate = game.getCategory(i);
			bw.write(writeCate.getName() + "\n");
			for(int j = 0; j < writeCate.getClueSize(); j++) {
				writeClue = writeCate.getClue(j);
				if(writeClue.equals(writeCate.getCurrentClue())) {
					bw.write("@");
				}
				bw.write(writeClue.getQuestion() + "|" + writeClue.getPrefix() + "|" + writeClue.getAnswer() + "\n");
			}
			bw.write("\n");
		}
		bw.close();
	}
	
	//Testing 
	//public static void main(String[] args) {
	//	GameDatabase.getInstance();
	//	System.out.println(GameDatabase.getInstance().getCategory(0).getCurrentClue().getAnswer());
	//	System.out.println(GameDatabase.getInstance().getCategory(0).getCurrentClue().checkAnswer("The seventy"));
	//}
}