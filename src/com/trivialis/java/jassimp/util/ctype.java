package com.trivialis.java.jassimp.util;

public class ctype {

	public static boolean isspace(char c) {
		return c=='\r' || c=='\n' || Character.isSpaceChar((int) c) || Character.isWhitespace((int) c);
	}

	public static int tolower(int c) {
		return (((char) c)+"").toLowerCase().toCharArray()[0];
	}

	public static boolean isdigit(int c)
	{
		return Character.isDigit(c);
	}

	public static boolean isalpha(char charAt)
	{
		return Character.isAlphabetic(charAt);
	}

}
