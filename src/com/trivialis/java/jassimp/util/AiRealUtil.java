package com.trivialis.java.jassimp.util;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;

public class AiRealUtil {

	public static double[] convertToDoubles(ai_real... ai_reals) {
		double[] doubles = new double[ai_reals.length];
		for(int i = 0; i < doubles.length; i++) {
			doubles[i]=ai_reals[i].getValue().doubleValue();
		}
		return doubles;
	}

	public static float[] convertToFloats(ai_real... ai_reals) {
		float[] doubles = new float[ai_reals.length];
		for(int i = 0; i < doubles.length; i++) {
			doubles[i]=ai_reals[i].getValue().floatValue();
		}
		return doubles;
	}

}
