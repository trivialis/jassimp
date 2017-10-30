package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.util.std;

public class vector2 {

	public static class aiVector2t {

		public float x;
		public float y;

		public aiVector2t()
		{

		}

		public aiVector2t(float _x, float _y) {
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

		public aiVector2t Normalize() {
			float len = Length();
			x=x /  (len);
			y=y /  (len);
			return this;
		}

		public aiVector2t opAdd(aiVector2t o) {
			x=x +  (o.x);y=y +  (o.y);
			return this;
		}
		public aiVector2t opSubtract(aiVector2t o) {
			x=x - (o.x);y=y - (o.y);
			return this;
		}
		public aiVector2t opMultiply(float f) {
			x=x * (f);y=y * (f);
			return this;
		}
		public aiVector2t opDivide(float f) {
			x=x /  (f);y=y /  (f);
			return this;
		}
		public float[] array() {
			return new float[]{x,y};
		}
		public float get(int i) {
			return array()[i];
		}
		public boolean opEquals(aiVector2t other) {
			return x == (other.x) && y == (other.y);
		}
		public boolean opNotEquals(aiVector2t other) {
			return x != (other.x) || y != (other.y);
		}
		public boolean opEqual(aiVector2t other, float epsilon) {
			return std.abs((double)(x - (other.x))) <= (epsilon)
					&& std.abs((double)(y - (other.y))) <= (epsilon);
		}
		public aiVector2t Set(float f) {
			x=y=f;
			return this;
		}
		public aiVector2t SymMul(aiVector2t o) {
			return new aiVector2t(x * (o.x), y * (o.y));
		}

		public static aiVector2t opAdd(aiVector2t v1, aiVector2t v2) {
			return new aiVector2t(v1.x +  (v2.x), v1.y +  (v2.y));
		}

		public static aiVector2t opSubtract(aiVector2t v1, aiVector2t v2) {
			return new aiVector2t(v1.x - (v2.x), v1.y - (v2.y));
		}

		public static aiVector2t opMultiply(aiVector2t v1, aiVector2t v2) {
			return new aiVector2t(v1.x * (v2.x), v1.y * (v2.y));
		}

		public static aiVector2t opMultiply(aiVector2t v1, float f) {
			return new aiVector2t(v1.x * (f), v1.y * (f));
		}
		public static aiVector2t opDivide(aiVector2t v, float f) {
			return v.opDivide(1.0F /  (f));
		}
		public static aiVector2t opDivide(aiVector2t v1, aiVector2t v2) {
			return new aiVector2t(v1.x /  (v2.x), v1.y /  (v2.y));
		}
		public static aiVector2t opNegate(aiVector2t v1) {
			return new aiVector2t(v1.x * (-1.0F), v1.y * (-1.0F));
		}

	}

	public static class aiVector2D extends aiVector2t {

		public aiVector2D(float d, float e)
		{
			this.x=d;
			this.y=e;
		}

		public aiVector2D()
		{
			// TODO Auto-generated constructor stub
		}

	}

}
