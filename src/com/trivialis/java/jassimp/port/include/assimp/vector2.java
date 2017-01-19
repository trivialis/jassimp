package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.util.std;

public class vector2 {

	public static class aiVector2t {

		public ai_real x;
		public ai_real y;

		public aiVector2t()
		{

		}

		public aiVector2t(ai_real _x, ai_real _y) {
			x=_x;y=_y;
		}

		public aiVector2t(ai_real _xyz) {
			x=_xyz;y=_xyz;
		}

		public aiVector2t(aiVector2t o) {
			x=o.x;
			y=o.y;
		}

		public void Set(ai_real pX, ai_real pY) {
			x=pX;y=pY;
		}

		public ai_real SquareLength() {
			return (x.opMultiply(x)).opAdd((y.opMultiply(y)));
		}

		public ai_real Length() {
			return x.forValue(std.sqrt(SquareLength().getValue().doubleValue()));
		}

		public aiVector2t Normalize() {
			ai_real len = Length();
			x=x.opDivide(len);
			y=y.opDivide(len);
			return this;
		}

		public aiVector2t opAdd(aiVector2t o) {
			x=x.opAdd(o.x);y=y.opAdd(o.y);
			return this;
		}
		public aiVector2t opSubtract(aiVector2t o) {
			x=x.opSubtract(o.x);y=y.opSubtract(o.y);
			return this;
		}
		public aiVector2t opMultiply(ai_real f) {
			x=x.opMultiply(f);y=y.opMultiply(f);
			return this;
		}
		public aiVector2t opDivide(ai_real f) {
			x=x.opDivide(f);y=y.opDivide(f);
			return this;
		}
		public ai_real[] array() {
			return new ai_real[]{x,y};
		}
		public ai_real get(int i) {
			return array()[i];
		}
		public boolean opEquals(aiVector2t other) {
			return x.opEquals(other.x) && y.opEquals(other.y);
		}
		public boolean opNotEquals(aiVector2t other) {
			return !x.opEquals(other.x) || !y.opEquals(other.y);
		}
		public boolean opEqual(aiVector2t other, ai_real epsilon) {
			return x.forValue(std.abs(x.opSubtract(other.x).getValue().doubleValue())).opSmallerOrEqual(epsilon)
					&& x.forValue(std.abs(y.opSubtract(other.y).getValue().doubleValue())).opSmallerOrEqual(epsilon);
		}
		public aiVector2t Set(ai_real f) {
			x=y=f;
			return this;
		}
		public aiVector2t SymMul(aiVector2t o) {
			return new aiVector2t(x.opMultiply(o.x), y.opMultiply(o.y));
		}

		public static aiVector2t opAdd(aiVector2t v1, aiVector2t v2) {
			return new aiVector2t(v1.x.opAdd(v2.x), v1.y.opAdd(v2.y));
		}

		public static aiVector2t opSubtract(aiVector2t v1, aiVector2t v2) {
			return new aiVector2t(v1.x.opSubtract(v2.x), v1.y.opSubtract(v2.y));
		}

		public static aiVector2t opMultiply(aiVector2t v1, aiVector2t v2) {
			return new aiVector2t(v1.x.opMultiply(v2.x), v1.y.opMultiply(v2.y));
		}

		public static aiVector2t opMultiply(aiVector2t v1, ai_real f) {
			return new aiVector2t(v1.x.opMultiply(f), v1.y.opMultiply(f));
		}
		public static aiVector2t opDivide(aiVector2t v, ai_real f) {
			return v.opDivide(v.x.forValue(1).opDivide(f));
		}
		public static aiVector2t opDivide(aiVector2t v1, aiVector2t v2) {
			return new aiVector2t(v1.x.opDivide(v2.x), v1.y.opDivide(v2.y));
		}
		public static aiVector2t opNegate(aiVector2t v1) {
			return new aiVector2t(v1.x.opMultiply(v1.x.forValue(-1.0)), v1.y.opMultiply(v1.y.forValue(-1.0)));
		}

	}

	public static class aiVector2D extends aiVector2t {

	}

}
