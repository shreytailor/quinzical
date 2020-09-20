package a3.quinzical.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class IO {
	private final static File _quizFile = new File(System.getProperty("user.dir")+"/Quinzical.txt");
	private final static File _gameFile = new File(System.getProperty("user.dir")+"/GameData.txt");
	private static ArrayList<String> _game = new ArrayList<String>();
	
	private final static int _startPrice = 100;
	private final static int _priceIncrement = 100;	
	private final static int _cateNum = 5;
	private final static int _clueNum = 5;
	
	public static GameData readGame() {
		GameData game = new GameData();
		try {
			if (_gameFile.exists() && _gameFile.isFile()) {
				BufferedReader br = new BufferedReader(new FileReader(_gameFile));
				String line;
				Category newCate = null;
				Clue newClue = null;
				int price = _startPrice;
				while(( line = br.readLine()) != null) {
					line = line.trim();
					if (line.matches("\\d+")) {
						game.setWinning(Integer.parseInt(line));
					}else if(!line.contains("(") && !line.contains(")") && !line.isBlank()){
						price = _startPrice;
						newCate = new Category(line);
						game.addCategory(newCate);
					}else if(!line.isBlank()){
						newClue = new Clue(line.split("[(]")[0].trim().replace(",", ""), line.split("[)]")[1].trim());
						newClue.setPrize(price);
						newCate.addClue(newClue);
						price += _priceIncrement;
					}
					_game.add(line);
				}
				br.close();		
			}
			else {
				PracticeDatabase quiz = readQuiz();
				Random rand = new Random();
				game.setWinning(0);
				Category newCate, selectedCate = null;
				Clue newClue = null;
				
				for(int i = 0; i < _cateNum; i++) {
					int cateIndex = rand.nextInt(quiz.getCateSize());
					selectedCate = quiz.getCategory(cateIndex);
					newCate = new Category(selectedCate.getName());
					int price = _startPrice;
					for(int j = 0; j < _clueNum; j++) {
						int questIndex = rand.nextInt(selectedCate.getClueSize());
						newClue = new Clue(selectedCate.getClue(questIndex).getQuestion(), selectedCate.getClue(questIndex).getAnswer());
						newClue.setPrize(price);
						newCate.addClue(newClue);
						selectedCate.removeClue(questIndex);
						price += _priceIncrement;
					}
					game.addCategory(newCate);
					quiz.removeCategory(cateIndex);
				}
				writeGameData(game);
			}
		}catch(IOException e) {
			System.out.println("Error occured during reading GameData file");
		}
		return game;
	}
	
	public static PracticeDatabase readQuiz() {
		PracticeDatabase quiz = PracticeDatabase.getInstance();
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(_quizFile));
			Category newCate = null;
			Clue newClue = null;
			while(( line = br.readLine()) != null) {
				if(!line.contains("(") && !line.contains(")") && !line.isBlank()){
					newCate = new Category(line);
					quiz.addCategory(newCate);
				}else if(!line.isBlank()){
					newClue = new Clue(line.split("[(]")[0].trim().replace(".", "").replace(",", ""), line.split("[)]")[1].replace(".", "").trim());
					newCate.addClue(newClue);
				}
			}
			br.close();		
		}catch(FileNotFoundException e) {
			System.out.println("Quinzical.txt not found in root directory");
		}catch(IOException e) {
			System.out.println("Error occured during reading Quiz file");
		}
		return quiz;
	}	
	
	public static boolean writeGameData(GameData game) {
		try {
			Category writeCate = null;
			Clue writeClue = null;
			BufferedWriter bw = new BufferedWriter(new FileWriter(_gameFile));
			bw.write(game.getWinning() + "\n\n");
			for(int i = 0; i < _cateNum; i++) {
				writeCate = game.getCategory(i);
				bw.write(writeCate.getName() + "\n");
				for(int j = 0; j < _clueNum; j++) {
					writeClue = writeCate.getClue(j);
					bw.write(writeClue.getQuestion() + ", (What is) " + writeClue.getAnswer() + "\n");
				}
				bw.write("\n");
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("Error occured duing writing GameData file");
			e.printStackTrace();
		}
		return true;	
	}
	
	public static void main(String[] args) {
		GameData g = readGame();
		System.out.print(g.getCategory(4).getClue(4).getQuestion());
	}
}