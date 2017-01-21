package com.trivialis.java.jassimp.util;

import java.util.ArrayList;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiBone;

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
	public static ai_real sqrt(ai_real t)
	{
		return new ai_real(sqrt((Double) t.getValue()));
	}
	public static <T> T swap(T... args) {
		return args[0];
	}
	public static double pow(Double a, Double b) {
		return Math.pow(a, b);
	}

	public static double sin(Double value) {
		return Math.sin(value);
	}

	public static ai_real sin(ai_real a)
	{
		if(a.getClass().equals(Double.class)) {
			return new ai_real(sin((double) a.getValue()));
		} else if(a.getClass().equals(Float.class)) {
			return new ai_real(sin((double) a.getValue()));
		}
		return new ai_real(sin((double) a.getValue()));
	}

	public static double cos(Double value) {
		return Math.cos(value);
	}

	public static ai_real cos(ai_real a)
	{
		if(a.getClass().equals(Double.class)) {
			return new ai_real(cos((double) a.getValue()));
		} else if(a.getClass().equals(Float.class)) {
			return new ai_real(cos((double) a.getValue()));
		}
		return new ai_real(cos((double) a.getValue()));
	}
	public static double acos(double value)
	{
		return Math.acos(value);
	}
	public static ai_real acos(ai_real a)
	{
		return new ai_real(acos((double) a.getValue()));
	}
	public static <T extends Object> void copy(ArrayList<T> newBones, T aiBone, T aiBone2, T[] mBones)
	{
		System.arraycopy(newBones.toArray(new Object[0]), newBones.indexOf(aiBone), mBones, 0, newBones.indexOf(aiBone2)-newBones.indexOf(aiBone));
	}



}
