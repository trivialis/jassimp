package com.trivialis.java.jassimp.port.code;

public class TinyFormatter {

	public static String format(String... tokens)
	{
		return String.join("", tokens);
	}

}
