package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4t;
import com.trivialis.java.jassimp.util.std;

public class vector3 {

	public static class aiVector3t<T extends Number> {
		
		public ai_real<T> x;
		public ai_real<T> y;
		public ai_real<T> z;

		public aiVector3t() {
			
		}
		
		public aiVector3t(ai_real<T> _x, ai_real<T> _y, ai_real<T> _z) {
			x=_x;
			y=_y;
			z=_z;
		}
		
		public aiVector3t(ai_real<T> _xyz) {
			x=_xyz;
			y=_xyz;
			z=_xyz;
		}
		public aiVector3t(aiVector3t<T> o) {
			x=o.x;
			y=o.y;
			z=o.z;
		}
		public aiVector3t<T> multiply(aiMatrix3x3t<ai_real<T>> pMatrix, aiVector3t<T> pVector) {
			aiVector3t<T> res = new aiVector3t<T>();
			res.x = (pMatrix.a1.opMultiply(pVector.x)).opAdd(pMatrix.a2.opMultiply(pVector.y)).opAdd(pMatrix.a3.opMultiply(pVector.z));
			res.y = (pMatrix.b1.opMultiply(pVector.x)).opAdd(pMatrix.b2.opMultiply(pVector.y)).opAdd(pMatrix.b3.opMultiply(pVector.z));
			res.z = (pMatrix.c1.opMultiply(pVector.x)).opAdd(pMatrix.c2.opMultiply(pVector.y)).opAdd(pMatrix.c3.opMultiply(pVector.z));
			return res;
		}
		public aiVector3t<T> multiply(aiMatrix4x4t<T> pMatrix, aiVector3t<T> pVector) {
			aiVector3t<T> res = new aiVector3t<T>();
			res.x = (pMatrix.a1.opMultiply(pVector.x)).opAdd(pMatrix.a2.opMultiply(pVector.y)).opAdd(pMatrix.a3.opMultiply(pVector.z)).opAdd(pMatrix.a4);
			res.y = (pMatrix.b1.opMultiply(pVector.x)).opAdd(pMatrix.b2.opMultiply(pVector.y)).opAdd(pMatrix.b3.opMultiply(pVector.z)).opAdd(pMatrix.b4);
			res.z = (pMatrix.c1.opMultiply(pVector.x)).opAdd(pMatrix.c2.opMultiply(pVector.y)).opAdd(pMatrix.c3.opMultiply(pVector.z)).opAdd(pMatrix.c4);
			return res;
		}
//		public <A extends ai_real> aiVector3t<A> cast(A o) {
//			return new aiVector3t(x.cast(o.getValue()), y.cast(o.getValue()), z.cast(o.getValue()));
//		}
		public void Set(ai_real<T> pX, ai_real<T> pY, ai_real<T> pZ) {
			x=pX; y= pY; z=pZ;
		}
		public ai_real<T> SquareLength() {
			return (x.opMultiply(x)).opAdd(y.opMultiply(y)).opAdd(z.opMultiply(z));
		}
		public ai_real<T> Length() {
			return x.forValue(std.sqrt((Double) SquareLength().getValue()));
		}
		public aiVector3t<T> Normalize() {
			ai_real<T> length = Length();
			x=x.opDivide(length); y=y.opDivide(length); z=z.opDivide(length);
			return this;
		}
		public aiVector3t<T> NormalizeSafe() {
			ai_real<T> len = Length();
			if(len.opBigger(len.forValue(0))) {
				return Normalize();
			}
			return this;
		}
		public aiVector3t<T> opAdd(aiVector3t<T> o) {
			x=x.opAdd(o.x);y=y.opAdd(o.y);z=z.opAdd(o.z);
			return this;
		}
		//TODO
		public boolean opEquals(aiVector3D<T> o)
		{
			return x.opEquals(o.x) && y.opEquals(o.y) && z.opEquals(o.z);
		}
		
	}
	
	public static class aiVector3D<T extends Number> extends aiVector3t<T> {


		
	}
	
	public static void main(String[] args) {
		aiVector3t<Double> a = new aiVector3t<Double>(new ai_real<Double>(1.0D), new ai_real<Double>(1.0), new ai_real<Double>(1.0));
		System.out.println(a.Length());
	}
	
}
