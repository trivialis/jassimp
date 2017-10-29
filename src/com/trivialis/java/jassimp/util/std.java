package com.trivialis.java.jassimp.util;

import java.util.ArrayList;

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
	public static float sqrt(float t)
	{
		return (float) sqrt((double) t);
	}
//	public static <T> T swap(T... args) {
//		return args[0];
//	}
	public static double pow(Double a, Double b) {
		return Math.pow(a, b);
	}

	public static double sin(Double value) {
		return Math.sin(value);
	}

	public static float sin(float a)
	{
		return (float) sin((double) a);
	}

	public static double cos(Double value) {
		return Math.cos(value);
	}

	public static float cos(float a)
	{
		return (float) cos((double) a);
	}
	public static double acos(double value)
	{
		return Math.acos(value);
	}
	public static float acos(float a)
	{
		return (float) acos((double) a);
	}
	public static <T extends Object> void copy(ArrayList<T> newBones, T aiBone, T aiBone2, T[] mBones)
	{
		System.arraycopy(newBones.toArray(new Object[0]), newBones.indexOf(aiBone), mBones, 0, newBones.indexOf(aiBone2)-newBones.indexOf(aiBone));
	}
	public static double max(double mDuration, double mTime)
	{
		return Math.max(mDuration, mTime);
	}
//	public static <T extends Object> void copy(ArrayList<T> src, int i, int j, ArrayList<T> dest)
//	{
//		List<T> temp = src.subList(i, j);
//
//	}



}
