package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaternion;

public class quaternion {

	public static class aiQuaterniont<T extends Number> {

		public aiMatrix3x3t<ai_real<T>> GetMatrix()
		{
			// TODO Auto-generated method stub
			return null;
		}

		//TODO
		public boolean opEquals(aiQuaternion<T> mValue)
		{
			// TODO Auto-generated method stub
			return false;
		}

		public static aiQuaternion<Number> Interpolate(aiQuaternion a, aiQuaternion b, ai_real<?> d)
		{
			// TODO Auto-generated method stub
			return null;
		}

		public aiQuaternion<Number> opSubtract(aiQuaternion<Number> a)
		{
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static class aiQuaternion<T extends Number> extends aiQuaterniont<T> {







	}

}
