package a3.quinzical.backend;

public class Formatting {
	
    /**
     * This method is used to format the answer for comparison
     * @param answer the string to be formatted
     * @return the formatted string
     */
	public static String trimAndLower(String str) {
		str = str.toLowerCase();
		str = str.replaceAll("\\s+", " ").trim();
		return str;
	}
	
	public static String removeTheA(String str) {
		str = str.replace("the ", "");
		str = str.replace("a ", "");
		return str;
	}
	
	public static String sanitize(String str) {
		str = trimAndLower(str);
		str = removeTheA(str);
		return str;
	}
}
