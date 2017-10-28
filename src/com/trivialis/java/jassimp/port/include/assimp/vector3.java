package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4t;
import com.trivialis.java.jassimp.util.std;

public class vector3 {


	public static class aiVector3t {

		public float x;
		public float y;
		public float z;

		public aiVector3t() {
			x=0.0F;
			y=0.0F;
			z=0.0F;
		}



		public aiVector3t(float _x, float _y, float _z) {
			x=_x;
			y=_y;
			z=_z;
		}


		public aiVector3t(float _xyz) {
			x=_xyz;
			y=_xyz;
			z=_xyz;
		}
		public aiVector3t(aiVector3t o) {
			x=o.x;
			y=o.y;
			z=o.z;
		}
		public static aiVector3t multiply(aiMatrix3x3t pMatrix, aiVector3t pVector) {
			aiVector3t res = pVector.newInstance();
			res.x = (pMatrix.a1 * pVector.x) + (pMatrix.a2 * pVector.y) + (pMatrix.a3 * pVector.z);
			res.y = (pMatrix.b1 * pVector.x) + (pMatrix.b2 * pVector.y) + (pMatrix.b3 * pVector.z);
			res.z = (pMatrix.c1 * pVector.x) + (pMatrix.c2 * pVector.y) + (pMatrix.c3 * pVector.z);
			return res;
		}
		public static aiVector3t multiply(aiMatrix4x4t pMatrix, aiVector3t pVector) {
			aiVector3t res = pVector.newInstance();
			res.x = (pMatrix.a1 * pVector.x) + (pMatrix.a2 * pVector.y) + (pMatrix.a3 * pVector.z) + (pMatrix.a4);
			res.y = (pMatrix.b1 * pVector.x) + (pMatrix.b2 * pVector.y) + (pMatrix.b3 * pVector.z) + (pMatrix.b4);
			res.z = (pMatrix.c1 * pVector.x) + (pMatrix.c2 * pVector.y) + (pMatrix.c3 * pVector.z) + (pMatrix.c4);
			return res;
		}
//		public <A extends ai_real> aiVector3t<A> cast(A o) {
//			return new aiVector3t(x.cast(o.getValue()), y.cast(o.getValue()), z.cast(o.getValue()));
//		}
		public void Set(float pX, float pY, float pZ) {
			x=pX; y= pY; z=pZ;
		}
		public float SquareLength() {
			return (x * x) + (y * y) + (z * z);
		}
		public float Length() {
			return (float) std.sqrt((double) SquareLength());
		}
		public aiVector3t Normalize() {
			float length = Length();
			x=x / (length); y=y / (length); z=z / (length);
			return this;
		}
		public aiVector3t NormalizeSafe() {
			float len = Length();
			if(len > (0F)) {
				return Normalize();
			}
			return this;
		}
		public aiVector3t opAdd(aiVector3t o) {
			x=x + o.x;y=y + o.y;z=z + o.z;
			return this;
		}

		public boolean opEquals(aiVector3t o)
		{
			return x == o.x && y == o.y && z == o.z;
		}
		public aiVector3t opSubtract(aiVector3t a)
		{
                    return new aiVector3t(x - a.x, y - a.y, z - a.z);
		}
		public aiVector3t opMultiply(float d)
		{
                    return new aiVector3t(x - d,y - d,z - d);
		}
		
		public <T extends aiVector3t> T newInstance() {
			return (T) new aiVector3t();
		}
                
                @Override
            public String toString() {
                return new StringBuilder("aiVector3:").append(" x: ").append(x).append(" y: ").append(y).append(" z: ").append(z).toString();
            }

	}

	public static class aiVector3D extends aiVector3t {

		public aiVector3D(float x, float y, float z)
		{
			super(x, y, z);
		}

            public aiVector3D() {
                super();
            }

            @Override
            public aiVector3D newInstance() {

                return new aiVector3D();
            }
            

	}

	public static void main(String[] args) {
		aiVector3t a = new aiVector3t(1.0F, 1.0F, 1.0F);
		System.out.println(a.Length());
	}

}
