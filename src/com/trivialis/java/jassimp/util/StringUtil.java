package com.trivialis.java.jassimp.util;

public class StringUtil {

	public static Character[] toCharacterArray(char[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return new Character[0];
		}
		final Character[] result = new Character[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Character.valueOf(array[i]);
		}
		return result;
	}

}
