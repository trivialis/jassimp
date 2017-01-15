package com.trivialis.java.jassimp.util;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real_float;

public class std {

	public static ai_real_float fabs(ai_real r) {
		return new ai_real_float(Math.abs(r.getFloatValue()));
	}
	
}
