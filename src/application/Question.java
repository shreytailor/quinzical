package application;

public class Question {
	private String _question;
	private String _answer;
	private int _price;
	private boolean _attempted = false;
	
	public Question(String q, String a) {
		_question = q;
		_answer = a;
	}
	
	public Question(String q, String a, int p) {
		_question = q;
		_answer = a;
		_price = p;
	}
	
	public String getQuestion() {
		return _question;
	}
	
	public String getAnswer() {
		return _answer;
	}
	
	public int getPrice() {
		return _price;
	}	
}
