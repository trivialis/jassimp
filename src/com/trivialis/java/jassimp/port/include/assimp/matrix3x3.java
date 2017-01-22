package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;

public class matrix3x3 {

	public static class aiMatrix3x3t {
		public ai_real a1, a2, a3;
		public ai_real b1, b2, b3;
		public ai_real c1, c2, c3;

		public aiMatrix3x3t(ai_real _a1, ai_real _a2, ai_real _a3, ai_real _b1, ai_real _b2, ai_real _b3, ai_real _c1, ai_real _c2, ai_real _c3)
		{
			a1=_a1;a2=_a2;a3=_a3;b1=_b1;b2=_b2;b3=_b3;c1=_c1;c2=_c2;c3=_c3;
		}
		public aiMatrix3x3t()
		{
			a1=new ai_real(1.0f);a2=new ai_real(0.0f); a3=new ai_real(0.0f);
			b1=new ai_real(0.0f);b2=new ai_real(1.0f); b3=new ai_real(0.0f);
			c1=new ai_real(0.0f);c2=new ai_real(0.0f); c3=new ai_real(1.0f);
		}

	}

	public static class aiMatrix3x3 extends aiMatrix3x3t {

		public aiMatrix3x3(ai_real a1, ai_real a2, ai_real a3, ai_real b1, ai_real b2, ai_real b3, ai_real c1, ai_real c2, ai_real c3)
		{
			super(a1,a2,a3,b1,b2,b3,c1,c2,c3);
		}

		public aiMatrix3x3()
		{
			super();
		}

	}

}
