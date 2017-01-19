package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaternion;

public class quaternion {

	public static class aiQuaterniont {

		public aiMatrix3x3t<ai_real> GetMatrix()
		{
			// TODO Auto-generated method stub
			return null;
		}

		//TODO
		public boolean opEquals(aiQuaternion mValue)
		{
			// TODO Auto-generated method stub
			return false;
		}

		public static aiQuaternion Interpolate(aiQuaternion a, aiQuaternion b, ai_real<?> d)
		{
			// TODO Auto-generated method stub
			return null;
		}

		public aiQuaternion opSubtract(aiQuaternion a)
		{
			// TODO Auto-generated method stub
			return null;
		}


	}

	public static class aiQuaternion extends aiQuaterniont {

	}

}
