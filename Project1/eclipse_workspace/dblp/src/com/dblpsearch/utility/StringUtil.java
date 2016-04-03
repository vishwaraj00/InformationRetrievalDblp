package com.dblpsearch.utility;

public class StringUtil {

	public static boolean isTextInQuotes(String text) {
		
		if(text.startsWith("\"") && text.endsWith("\""))
			return true;
		
		return false;
	}
	
	public static String removeQuotes(String text) {
		
		int lenght = text.length();
		String newText = text.substring(1, lenght-1);
		return newText;
	}
}
