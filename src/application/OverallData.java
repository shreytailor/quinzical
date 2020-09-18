package application;

import java.util.ArrayList;

public class OverallData {

	private ArrayList<Category> _categories = new ArrayList<Category>();
	
	public void addCategory(Category c) {
		_categories.add(c);
	}
	
	public Category getCategory(int i){
		return _categories.get(i);
	}
	
	public int getCateSize() {
		return _categories.size();
	}
	
	public void removeCategory(int i) {
		_categories.remove(i);
	}
}
