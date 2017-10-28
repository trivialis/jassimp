package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4t;

public class matrix3x3 {

	public static class aiMatrix3x3t {
		public float a1, a2, a3;
		public float b1, b2, b3;
		public float c1, c2, c3;

		public aiMatrix3x3t(float _a1, float _a2, float _a3, float _b1, float _b2, float _b3, float _c1, float _c2, float _c3)
		{
			a1=_a1;a2=_a2;a3=_a3;b1=_b1;b2=_b2;b3=_b3;c1=_c1;c2=_c2;c3=_c3;
		}
		public aiMatrix3x3t()
		{
			a1=1.0F;a2=0.0F; a3=0.0F;
			b1=0.0F;b2=1.0F; b3=0.0F;
			c1=0.0F;c2=0.0F; c3=1.0F;
		}
		
		public aiMatrix3x3t(aiMatrix4x4t pMatrix) {
		    a1 = pMatrix.a1; a2 = pMatrix.a2; a3 = pMatrix.a3;
		    b1 = pMatrix.b1; b2 = pMatrix.b2; b3 = pMatrix.b3;
		c1 = pMatrix.c1; c2 = pMatrix.c2; c3 = pMatrix.c3;
		}

	}

	public static class aiMatrix3x3 extends aiMatrix3x3t {

		public aiMatrix3x3(float a1, float a2, float a3, float b1, float b2, float b3, float c1, float c2, float c3)
		{
			super(a1,a2,a3,b1,b2,b3,c1,c2,c3);
		}
		
		public aiMatrix3x3(aiMatrix4x4 pMatrix) {
			super(pMatrix);
		}

		public aiMatrix3x3()
		{
			super();
		}

	}

}
