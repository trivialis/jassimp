package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4t;
import com.trivialis.java.jassimp.util.std;

public class vector3 {

	public static class aiVector3t<TReal extends ai_real> {
		
		private TReal x;
		private TReal y;
		private TReal z;

		public aiVector3t() {
			
		}
		
		public aiVector3t(TReal _x, TReal _y, TReal _z) {
			x=_x;
			y=_y;
			z=_z;
		}
		
		public aiVector3t(TReal _xyz) {
			x=_xyz;
			y=_xyz;
			z=_xyz;
		}
		public aiVector3t(aiVector3t<TReal> o) {
			x=o.x;
			y=o.y;
			z=o.z;
		}
		public aiVector3t<ai_real> multiply(aiMatrix3x3t<TReal> pMatrix, aiVector3t<TReal> pVector) {
			aiVector3t<ai_real> res = new aiVector3t<ai_real>();
			res.x = (pMatrix.a1.opMultiply(pVector.x)).opAdd(pMatrix.a2.opMultiply(pVector.y)).opAdd(pMatrix.a3.opMultiply(pVector.z));
			res.y = (pMatrix.b1.opMultiply(pVector.x)).opAdd(pMatrix.b2.opMultiply(pVector.y)).opAdd(pMatrix.b3.opMultiply(pVector.z));
			res.z = (pMatrix.c1.opMultiply(pVector.x)).opAdd(pMatrix.c2.opMultiply(pVector.y)).opAdd(pMatrix.c3.opMultiply(pVector.z));
			return res;
		}
		public aiVector3t<ai_real> multiply(aiMatrix4x4t<TReal> pMatrix, aiVector3t<TReal> pVector) {
			aiVector3t<ai_real> res = new aiVector3t<ai_real>();
			res.x = (pMatrix.a1.opMultiply(pVector.x)).opAdd(pMatrix.a2.opMultiply(pVector.y)).opAdd(pMatrix.a3.opMultiply(pVector.z)).opAdd(pMatrix.a4);
			res.y = (pMatrix.b1.opMultiply(pVector.x)).opAdd(pMatrix.b2.opMultiply(pVector.y)).opAdd(pMatrix.b3.opMultiply(pVector.z)).opAdd(pMatrix.b4);
			res.z = (pMatrix.c1.opMultiply(pVector.x)).opAdd(pMatrix.c2.opMultiply(pVector.y)).opAdd(pMatrix.c3.opMultiply(pVector.z)).opAdd(pMatrix.c4);
			return res;
		}
		public <T extends ai_real> aiVector3t<T> cast(T o) {
			return new aiVector3t<T>(x.cast(o), y.cast(o), z.cast(o));
		}
		public void Set(TReal pX, TReal pY, TReal pZ) {
			x=pX; y= pY; z=pZ;
		}
		public TReal SquareLength() {
			return (TReal) (x.opMultiply(x)).opAdd(y.opMultiply(y)).opAdd(z.opMultiply(z));
		}
		public TReal Length() {
			return (TReal) x.forValue(std.sqrt(SquareLength().getDoubleValue()));
		}
		public aiVector3t<TReal> Normalize() {
			TReal length = Length();
			x=(TReal) x.opDivide(length); y=(TReal) y.opDivide(length); z=(TReal) z.opDivide(length);
			return this;
		}
		public aiVector3t<TReal> NormalizeSafe() {
			TReal len = Length();
			if(len.opBigger(len.forValue(0))) {
				return Normalize();
			}
			return this;
		}
		public aiVector3t<TReal> opAdd(aiVector3t<TReal> o) {
			x=(TReal) x.opAdd(o.x);y=(TReal) y.opAdd(o.y);z=(TReal) z.opAdd(o.z);
			return this;
		}
		
	}
	
}
