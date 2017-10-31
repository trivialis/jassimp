package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.util.std;

public class vector2 {

	public static class aiVector2D {

		public float x;
		public float y;

		public aiVector2D()
		{

		}

		public aiVector2D(float _x, float _y) {
			x=_x;y=_y;
		}

//		public aiVector2t(float _xyz) {
//			x=_xyz;y=_xyz;
//		}

//		public aiVector2t(aiVector2t o) {
//			x=o.x;
//			y=o.y;
//		}

		public void Set(float pX, float pY) {
			x=pX;y=pY;
		}

		public float SquareLength() {
			return (x * (x)) +  ((y * (y)));
		}

		public float Length() {
			return std.sqrt(SquareLength());
		}

		public aiVector2D Normalize() {
			float len = Length();
			x=x /  (len);
			y=y /  (len);
			return this;
		}

		public aiVector2D opAdd(aiVector2D o) {
			x=x +  (o.x);y=y +  (o.y);
			return this;
		}
		public aiVector2D opSubtract(aiVector2D o) {
			x=x - (o.x);y=y - (o.y);
			return this;
		}
		public aiVector2D opMultiply(float f) {
			x=x * (f);y=y * (f);
			return this;
		}
		public aiVector2D opDivide(float f) {
			x=x /  (f);y=y /  (f);
			return this;
		}
		public float[] array() {
			return new float[]{x,y};
		}
		public float get(int i) {
			return array()[i];
		}
		public boolean opEquals(aiVector2D other) {
			return x == (other.x) && y == (other.y);
		}
		public boolean opNotEquals(aiVector2D other) {
			return x != (other.x) || y != (other.y);
		}
		public boolean opEqual(aiVector2D other, float epsilon) {
			return std.abs((double)(x - (other.x))) <= (epsilon)
					&& std.abs((double)(y - (other.y))) <= (epsilon);
		}
		public aiVector2D Set(float f) {
			x=y=f;
			return this;
		}
		public aiVector2D SymMul(aiVector2D o) {
			return new aiVector2D(x * (o.x), y * (o.y));
		}

		public static aiVector2D opAdd(aiVector2D v1, aiVector2D v2) {
			return new aiVector2D(v1.x +  (v2.x), v1.y +  (v2.y));
		}

		public static aiVector2D opSubtract(aiVector2D v1, aiVector2D v2) {
			return new aiVector2D(v1.x - (v2.x), v1.y - (v2.y));
		}

		public static aiVector2D opMultiply(aiVector2D v1, aiVector2D v2) {
			return new aiVector2D(v1.x * (v2.x), v1.y * (v2.y));
		}

		public static aiVector2D opMultiply(aiVector2D v1, float f) {
			return new aiVector2D(v1.x * (f), v1.y * (f));
		}
		public static aiVector2D opDivide(aiVector2D v, float f) {
			return v.opDivide(1.0F /  (f));
		}
		public static aiVector2D opDivide(aiVector2D v1, aiVector2D v2) {
			return new aiVector2D(v1.x /  (v2.x), v1.y /  (v2.y));
		}
		public static aiVector2D opNegate(aiVector2D v1) {
			return new aiVector2D(v1.x * (-1.0F), v1.y * (-1.0F));
		}

	}

}
