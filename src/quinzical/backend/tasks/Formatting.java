package quinzical.backend.tasks;
import quinzical.backend.models.Clue;
import quinzical.backend.models.Category;

public class Formatting {
    /**
     * This method is used to make the string lower case and remove white spaces.
     * @param answer the string to be formatted.
     * @return String the formatted string.
     */
	public static String trimAndLower(String answer) {
		answer = answer.toLowerCase();
		answer = answer.replaceAll("\\s+", " ").trim();
		return answer;
	}
	
	/**
     * This method is used to remove some redundant prefix of the answer like "the" and "a".
     * @param answer the string to be formatted.
     * @return String the formatted string.
     */
	public static String removeTheA(String answer) {
		// Removing "THE".
		if(answer.toUpperCase().startsWith("THE ")) {
			answer = answer.substring(4);
		}

		// Removing "A".
		if(answer.toUpperCase().startsWith("A ")) {
			answer = answer.substring(2);
		}

		return answer;
	}
	
	/**
     * This method uses trimAndLower() and removeTheA() to sanitize the answer for comparison.
     * @param answer the string to be formatted.
     * @return String the formatted string.
     */
	public static String sanitize(String answer) {
		answer = trimAndLower(answer);
		answer = removeTheA(answer);
		return answer;
	}
	
	/**
	 * This method is used to construct a Clue object, after formatting the strings for Clue.
	 * @param string the line read from a file
	 * @param category the category the Clue belongs to
	 * @return a clue object that is sanitized
	 */
	public static Clue formatClue(String string, Category category) {
		String question, prefix, answer;

		question = string.split("[|]")[0].trim();
		prefix = string.split("[|]")[1].trim();
		answer = string.split("[|]")[2].trim();

		Clue clue = new Clue(question, prefix, answer, category);
		return clue;
	}
	
	/**
	 * This method is used to help read in the values from progression file by the prefixes.
	 * @param string string to be formatted.
	 * @return int the part after the prefix in the string.
	 */
	public static int formatProgression(String string) {
		string = string.replaceAll("[^0-9]", "");
		return Integer.parseInt(string);
	}
}
