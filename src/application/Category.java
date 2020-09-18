package application;

import java.util.ArrayList;

public class Category {
	private String _cateName;
	private ArrayList<Question> _questions = new ArrayList<Question>();
	
	public Category(String s){
		_cateName = s;
	}
	
	public void addQuestion(Question q) {
		_questions.add(q);
	}
	
	public String getCateName() {
		return _cateName;
	}
	
	public Question getQuestion(int i) {
		return _questions.get(i);
	}
	
	public int getQuestSize() {
		return _questions.size();
	}
	
	public void removeQuestion(int i) {
		_questions.remove(i);
	}
}
