package a3.quinzical.backend;

import java.util.ArrayList;

public class GameData {
	private ArrayList<Category> _categories = new ArrayList<Category>();
	private int _winning = 0;
	
	public void setWinning(int i) {
		_winning = i;
	}
	
	public void addCategory(Category c) {
		_categories.add(c);
	}
	
	public Category getCategory(int i){
		return _categories.get(i);
	}
	
	public int getWinning() {
		return _winning;
	}
	
	public void removeCategory(int i) {
		_categories.remove(i);
	}
}
