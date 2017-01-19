package com.trivialis.java.jassimp.util;

public class ctype {

	public static boolean isspace(char c) {
		return Character.isSpaceChar(c);
	}

	public static int tolower(int c) {
		return (((char) c)+"").toLowerCase().toCharArray()[0];
	}

}
