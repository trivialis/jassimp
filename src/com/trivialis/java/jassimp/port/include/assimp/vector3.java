package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4t;
import com.trivialis.java.jassimp.util.std;

public class vector3 {


	public static class aiVector3t {

		public ai_real x;
		public ai_real y;
		public ai_real z;

		public aiVector3t() {

		}



		public aiVector3t(ai_real _x, ai_real _y, ai_real _z) {
			x=_x;
			y=_y;
			z=_z;
		}


		public aiVector3t(ai_real _xyz) {
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
			aiVector3t res = new aiVector3t();
			res.x = (pMatrix.a1.opMultiply(pVector.x)).opAdd(pMatrix.a2.opMultiply(pVector.y)).opAdd(pMatrix.a3.opMultiply(pVector.z));
			res.y = (pMatrix.b1.opMultiply(pVector.x)).opAdd(pMatrix.b2.opMultiply(pVector.y)).opAdd(pMatrix.b3.opMultiply(pVector.z));
			res.z = (pMatrix.c1.opMultiply(pVector.x)).opAdd(pMatrix.c2.opMultiply(pVector.y)).opAdd(pMatrix.c3.opMultiply(pVector.z));
			return res;
		}
		public static aiVector3t multiply(aiMatrix4x4t pMatrix, aiVector3t pVector) {
			aiVector3t res = new aiVector3t();
			res.x = (pMatrix.a1.opMultiply(pVector.x)).opAdd(pMatrix.a2.opMultiply(pVector.y)).opAdd(pMatrix.a3.opMultiply(pVector.z)).opAdd(pMatrix.a4);
			res.y = (pMatrix.b1.opMultiply(pVector.x)).opAdd(pMatrix.b2.opMultiply(pVector.y)).opAdd(pMatrix.b3.opMultiply(pVector.z)).opAdd(pMatrix.b4);
			res.z = (pMatrix.c1.opMultiply(pVector.x)).opAdd(pMatrix.c2.opMultiply(pVector.y)).opAdd(pMatrix.c3.opMultiply(pVector.z)).opAdd(pMatrix.c4);
			return res;
		}
//		public <A extends ai_real> aiVector3t<A> cast(A o) {
//			return new aiVector3t(x.cast(o.getValue()), y.cast(o.getValue()), z.cast(o.getValue()));
//		}
		public void Set(ai_real pX, ai_real pY, ai_real pZ) {
			x=pX; y= pY; z=pZ;
		}
		public ai_real SquareLength() {
			return (x.opMultiply(x)).opAdd(y.opMultiply(y)).opAdd(z.opMultiply(z));
		}
		public ai_real Length() {
			return x.forValue(std.sqrt((Double) SquareLength().getValue()));
		}
		public aiVector3t Normalize() {
			ai_real length = Length();
			x=x.opDivide(length); y=y.opDivide(length); z=z.opDivide(length);
			return this;
		}
		public aiVector3t NormalizeSafe() {
			ai_real len = Length();
			if(len.opBigger(len.forValue(0))) {
				return Normalize();
			}
			return this;
		}
		public aiVector3t opAdd(aiVector3t o) {
			x=x.opAdd(o.x);y=y.opAdd(o.y);z=z.opAdd(o.z);
			return this;
		}
		//TODO
		public boolean opEquals(aiVector3D o)
		{
			return x.opEquals(o.x) && y.opEquals(o.y) && z.opEquals(o.z);
		}
		public aiVector3D opSubtract(aiVector3D a)
		{
			// TODO Auto-generated method stub
			return null;
		}
		public aiVector3t opMultiply(ai_real d)
		{
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static class aiVector3D extends aiVector3t {

		public aiVector3D(ai_real x, ai_real y, ai_real z)
		{
			super(x, y, z);
		}

		public aiVector3D()
		{
			// TODO Auto-generated constructor stub
		}






	}

	public static void main(String[] args) {
		aiVector3t a = new aiVector3t(new ai_real(1.0D), new ai_real(1.0), new ai_real(1.0));
		System.out.println(a.Length());
	}

}
