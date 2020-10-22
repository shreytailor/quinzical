package a3.quinzical.backend.tasks;

import a3.quinzical.backend.models.Category;
import a3.quinzical.backend.models.Clue;

public class Formatting {
	
    /**
     * This method is used to make the string lower case and no white spaces
     * @param answer the string to be formatted
     * @return the formatted string
     */
	public static String trimAndLower(String answer) {
		answer = answer.toLowerCase();
		answer = answer.replaceAll("\\s+", " ").trim();
		return answer;
	}
	
	/**
     * This method is used to remove some redundant part of the string like "the" and "a"
     * @param answer the string to be formatted
     * @return the formatted string
     */
	public static String removeTheA(String answer) {
		if(answer.toUpperCase().startsWith("THE ")) {
			answer = answer.substring(4);
		}
		if(answer.toUpperCase().startsWith("A ")) {
			answer = answer.substring(2);
		}
		return answer;
	}
	
	/**
     * This method combines trimAndLower and removeTheA to sanitize the string for answer comparison
     * @param answer the string to be formatted
     * @return the formatted string
     */
	public static String sanitize(String answer) {
		answer = trimAndLower(answer);
		answer = removeTheA(answer);
		return answer;
	}
	
	/**
	 * This method is used to construct a Clue object while formatting the input strings of the Clue
	 * @param str the line read from a file
	 * @param cate the category the Clue belongs to
	 * @return a clue object that is sanitized
	 */
	public static Clue formatClue(String str, Category cate) {
		String question, prefix, answer;
		question = str.split("[|]")[0].trim();
		prefix = str.split("[|]")[1].trim();
		answer = str.split("[|]")[2].trim();
		Clue clue = new Clue(question, prefix, answer, cate);
		return clue;
	}
	
	public static int formatProgression(String str) {
		str = str.replaceAll("[^0-9]", "");
		return Integer.parseInt(str);
	}
}
