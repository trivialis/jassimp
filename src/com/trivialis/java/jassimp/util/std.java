package com.trivialis.java.jassimp.util;

public class std {

	public static class string {
		public static final int npos = -1;
	}

	public static double abs(Double value) {
		return Math.abs(value);
	}
	public static double sqrt(Double value) {
		return Math.sqrt(value);
	}
	public static <T> T swap(T... args) {
		return args[0];
	}
	public static double pow(Double a, Double b) {
		return Math.pow(a, b);
	}

}
