package a3.quinzical.backend;

import a3.quinzical.backend.models.Category;
import a3.quinzical.backend.models.Clue;

public class Formatting {
	
    /**
     * This method is used to make the string lower case and no white spaces
     * @param answer the string to be formatted
     * @return the formatted string
     */
	public static String trimAndLower(String str) {
		str = str.toLowerCase();
		str = str.replaceAll("\\s+", " ").trim();
		return str;
	}
	
	/**
     * This method is used to remove some redundant part of the string like "the" and "a"
     * @param answer the string to be formatted
     * @return the formatted string
     */
	public static String removeTheA(String str) {
		str = str.replace("the ", "");
		str = str.replace("a ", "");
		return str;
	}
	
	/**
     * This method combines trimAndLower and removeTheA to sanitize the string for answer comparison
     * @param answer the string to be formatted
     * @return the formatted string
     */
	public static String sanitize(String str) {
		str = trimAndLower(str);
		str = removeTheA(str);
		return str;
	}
	
	/**
	 * This method is used to construct a Clue object while formatting the input strings of the Clue
	 * @param str the line read from a file
	 * @param cate the category the Clue belongs to
	 * @return a clue object that is sanitized
	 */
	public static Clue formatClue(String str, Category cate) {
		Clue clue = new Clue(str.split("[|]")[0].trim(), str.split("[|]")[1].trim(), str.split("[|]")[2].trim(), cate);
		return clue;
	}
}
