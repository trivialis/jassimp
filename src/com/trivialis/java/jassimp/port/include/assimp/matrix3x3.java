package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;

public class matrix3x3 {

	public static class aiMatrix3x3t {
		public aiMatrix3x3t(ai_real a12, ai_real a22, ai_real a32, ai_real b12, ai_real b22, ai_real b32, ai_real c12, ai_real c22, ai_real c32)
		{
			// TODO Auto-generated constructor stub
		}
		public aiMatrix3x3t()
		{
			// TODO Auto-generated constructor stub
		}
		ai_real a1, a2, a3;
		ai_real b1, b2, b3;
		ai_real c1, c2, c3;
	}

	public static class aiMatrix3x3 extends aiMatrix3x3t {

		public aiMatrix3x3(ai_real a1, ai_real a2, ai_real a3, ai_real b1, ai_real b2, ai_real b3, ai_real c1, ai_real c2, ai_real c3)
		{
			super(a1,a2,a3,b1,b2,b3,c1,c2,c3);
		}

		public aiMatrix3x3()
		{
			// TODO Auto-generated constructor stub
		}

	}

}
