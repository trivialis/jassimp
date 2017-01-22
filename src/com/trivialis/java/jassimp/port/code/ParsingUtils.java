package com.trivialis.java.jassimp.port.code;

public class ParsingUtils {


	public static boolean IsSpace( char in)
	{
	    return (in == (char)' ' || in == (char)'\t');
	}

	public static boolean IsLineEnd( char in)
	{
	    return (in==(char)'\r'||in==(char)'\n'||in==(char)'\0'||in==(char)'\f');
	}

	public static boolean IsSpaceOrNewLine(char in) {
		return IsSpace(in) || IsLineEnd(in);
	}

}
