package application;

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
	private final static int _questNum = 5;
	
	public static GameData readGame() {
		GameData game = new GameData();
		try {
			if (_gameFile.exists() && _gameFile.isFile()) {
				BufferedReader br = new BufferedReader(new FileReader(_gameFile));
				String line;
				Category newCate = null;
				Question newQuest = null;
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
						newQuest = new Question(line.split("[(]")[0].trim(), line.split("[)]")[1].replace(".", "").trim(), price);
						newCate.addQuestion(newQuest);
						price += _priceIncrement;
					}
					_game.add(line);
				}
				br.close();		
			}
			else {
				OverallData quiz = readQuiz();
				Random rand = new Random();
				game.setWinning(0);
				Category newCate, selectedCate = null;
				
				for(int i = 0; i < _cateNum; i++) {
					int cateIndex = rand.nextInt(quiz.getCateSize());
					selectedCate = quiz.getCategory(cateIndex);
					newCate = new Category(selectedCate.getCateName());
					int price = _startPrice;
					for(int j = 0; j < _questNum; j++) {
						int questIndex = rand.nextInt(selectedCate.getQuestSize());
						newCate.addQuestion(new Question(selectedCate.getQuestion(questIndex).getQuestion(), selectedCate.getQuestion(questIndex).getAnswer(), price));
						selectedCate.removeQuestion(questIndex);
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
	
	public static OverallData readQuiz() {
		OverallData quiz = new OverallData();
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(_quizFile));
			Category newCate = null;
			Question newQuest = null;
			while(( line = br.readLine()) != null) {
				if(!line.contains("(") && !line.contains(")") && !line.isBlank()){
					newCate = new Category(line);
					quiz.addCategory(newCate);
				}else if(!line.isBlank()){
					newQuest = new Question(line.split("[(]")[0].trim().replace(".", "").replace(",", ""), line.split("[)]")[1].replace(".", "").trim());
					newCate.addQuestion(newQuest);
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
			Question writeQuest = null;
			BufferedWriter bw = new BufferedWriter(new FileWriter(_gameFile));
			bw.write(game.getWinning() + "\n\n");
			for(int i = 0; i < _cateNum; i++) {
				writeCate = game.getCategory(i);
				bw.write(writeCate.getCateName() + "\n");
				for(int j = 0; j < _questNum; j++) {
					writeQuest = writeCate.getQuestion(j);
					bw.write(writeQuest.getQuestion() + ", (What is) " + writeQuest.getAnswer() + "\n");
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
	
	//public static void main(String[] args) {
	//	GameData g = readGame();
	//}
}