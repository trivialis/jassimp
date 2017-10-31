package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.util.std;

public class vector3 {


	public static class aiVector3D {

		public float x;
		public float y;
		public float z;

		public aiVector3D() {
			x=0.0F;
			y=0.0F;
			z=0.0F;
		}

		public aiVector3D(float _x, float _y, float _z) {
			x=_x;
			y=_y;
			z=_z;
		}


		public aiVector3D(float _xyz) {
			x=_xyz;
			y=_xyz;
			z=_xyz;
		}
		public aiVector3D(aiVector3D o) {
			x=o.x;
			y=o.y;
			z=o.z;
		}
		public static aiVector3D multiply(aiMatrix3x3 pMatrix, aiVector3D pVector) {
			aiVector3D res = pVector.newInstance();
			res.x = (pMatrix.a1 * pVector.x) + (pMatrix.a2 * pVector.y) + (pMatrix.a3 * pVector.z);
			res.y = (pMatrix.b1 * pVector.x) + (pMatrix.b2 * pVector.y) + (pMatrix.b3 * pVector.z);
			res.z = (pMatrix.c1 * pVector.x) + (pMatrix.c2 * pVector.y) + (pMatrix.c3 * pVector.z);
			return res;
		}
		public static aiVector3D multiply(aiMatrix4x4 pMatrix, aiVector3D pVector) {
			aiVector3D res = pVector.newInstance();
			res.x = (pMatrix.a1 * pVector.x) + (pMatrix.a2 * pVector.y) + (pMatrix.a3 * pVector.z) + (pMatrix.a4);
			res.y = (pMatrix.b1 * pVector.x) + (pMatrix.b2 * pVector.y) + (pMatrix.b3 * pVector.z) + (pMatrix.b4);
			res.z = (pMatrix.c1 * pVector.x) + (pMatrix.c2 * pVector.y) + (pMatrix.c3 * pVector.z) + (pMatrix.c4);
			return res;
		}
//		public <A extends ai_real> aiVector3D<A> cast(A o) {
//			return new aiVector3D(x.cast(o.getValue()), y.cast(o.getValue()), z.cast(o.getValue()));
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
		public aiVector3D Normalize() {
			float length = Length();
			x=x / (length); y=y / (length); z=z / (length);
			return this;
		}
		public aiVector3D NormalizeSafe() {
			float len = Length();
			if(len > (0F)) {
				return Normalize();
			}
			return this;
		}
		public aiVector3D opAdd(aiVector3D o) {
			x=x + o.x;y=y + o.y;z=z + o.z;
			return this;
		}

		public boolean opEquals(aiVector3D o)
		{
			return x == o.x && y == o.y && z == o.z;
		}
		public aiVector3D opSubtract(aiVector3D a)
		{
                    return new aiVector3D(x - a.x, y - a.y, z - a.z);
		}
		public aiVector3D opMultiply(float d)
		{
                    return new aiVector3D(x * d,y * d,z * d);
		}
		
		public <T extends aiVector3D> T newInstance() {
			return (T) new aiVector3D();
		}
                
                @Override
            public String toString() {
                return new StringBuilder("aiVector3:").append(" x: ").append(x).append(" y: ").append(y).append(" z: ").append(z).toString();
            }



				@Override
				public int hashCode()
				{
					final int prime = 31;
					int result = 1;
					result = prime * result + Float.floatToIntBits(x);
					result = prime * result + Float.floatToIntBits(y);
					result = prime * result + Float.floatToIntBits(z);
					return result;
				}



				@Override
				public boolean equals(Object obj)
				{
					if (this == obj)
						return true;
					if (obj == null)
						return false;
					if (getClass() != obj.getClass())
						return false;
					aiVector3D other = (aiVector3D) obj;
					if(!this.opEquals(other)) {
						return false;
					}
					return true;
				}
                
                

	}

	public static void main(String[] args) {
		aiVector3D a = new aiVector3D(1.0F, 1.0F, 1.0F);
		System.out.println(a.Length());
	}

}
