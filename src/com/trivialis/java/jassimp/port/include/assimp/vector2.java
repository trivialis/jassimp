package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.util.std;

public class vector2 {

	public static class aiVector2t<T extends Number> {
		
		public ai_real<T> x;
		public ai_real<T> y;
		
		public aiVector2t()
		{
			
		}
		
		public aiVector2t(ai_real<T> _x, ai_real<T> _y) {
			x=_x;y=_y;
		}
		
		public aiVector2t(ai_real<T> _xyz) {
			x=_xyz;y=_xyz;
		}
		
		public aiVector2t(aiVector2t<T> o) {
			x=o.x;
			y=o.y;
		}
		
		public void Set(ai_real<T> pX, ai_real<T> pY) {
			x=pX;y=pY;
		}
		
		public ai_real<T> SquareLength() {
			return (x.opMultiply(x)).opAdd((y.opMultiply(y)));
		}
		
		public ai_real<T> Length() {
			return x.forValue(std.sqrt(SquareLength().getValue().doubleValue()));
		}
		
		public aiVector2t<T> Normalize() {
			ai_real<T> len = Length();
			x=x.opDivide(len);
			y=y.opDivide(len);
			return this;
		}
		
		public aiVector2t<T> opAdd(aiVector2t<T> o) {
			x=x.opAdd(o.x);y=y.opAdd(o.y);
			return this;
		}
		public aiVector2t<T> opSubtract(aiVector2t<T> o) {
			x=x.opSubtract(o.x);y=y.opSubtract(o.y);
			return this;
		}
		public aiVector2t<T> opMultiply(ai_real<T> f) {
			x=x.opMultiply(f);y=y.opMultiply(f);
			return this;
		}
		public aiVector2t<T> opDivide(ai_real<T> f) {
			x=x.opDivide(f);y=y.opDivide(f);
			return this;
		}
		public ai_real<T>[] array() {
			return new ai_real[]{x,y};
		}
		public ai_real<T> get(int i) {
			return array()[i];
		}
		public boolean opEquals(aiVector2t<T> other) {
			return x.opEquals(other.x) && y.opEquals(other.y);
		}
		public boolean opNotEquals(aiVector2t<T> other) {
			return !x.opEquals(other.x) || !y.opEquals(other.y);
		}
		public boolean opEqual(aiVector2t<T> other, ai_real<T> epsilon) {
			return x.forValue(std.abs(x.opSubtract(other.x).getValue().doubleValue())).opSmallerOrEqual(epsilon) 
					&& x.forValue(std.abs(y.opSubtract(other.y).getValue().doubleValue())).opSmallerOrEqual(epsilon);
		}
		public aiVector2t<T> Set(ai_real<T> f) {
			x=y=f;
			return this;
		}
		public aiVector2t<T> SymMul(aiVector2t<T> o) {
			return new aiVector2t<T>(x.opMultiply(o.x), y.opMultiply(o.y));
		}
		
		public static <T extends Number> aiVector2t<T> opAdd(aiVector2t<T> v1, aiVector2t<T> v2) {
			return new aiVector2t<T>(v1.x.opAdd(v2.x), v1.y.opAdd(v2.y));
		}
		
		public static <T extends Number> aiVector2t<T> opSubtract(aiVector2t<T> v1, aiVector2t<T> v2) {
			return new aiVector2t<T>(v1.x.opSubtract(v2.x), v1.y.opSubtract(v2.y));
		}
		
		public static <T extends Number> aiVector2t<T> opMultiply(aiVector2t<T> v1, aiVector2t<T> v2) {
			return new aiVector2t<T>(v1.x.opMultiply(v2.x), v1.y.opMultiply(v2.y));
		}
		
		public static <T extends Number> aiVector2t<T> opMultiply(aiVector2t<T> v1, ai_real<T> f) {
			return new aiVector2t<T>(v1.x.opMultiply(f), v1.y.opMultiply(f));
		}
		public static <T extends Number> aiVector2t<T> opDivide(aiVector2t<T> v, ai_real<T> f) {
			return v.opDivide(v.x.forValue(1).opDivide(f));
		}
		public static <T extends Number> aiVector2t<T> opDivide(aiVector2t<T> v1, aiVector2t<T> v2) {
			return new aiVector2t<T>(v1.x.opDivide(v2.x), v1.y.opDivide(v2.y));
		}
		public static <T extends Number> aiVector2t<T> opNegate(aiVector2t<T> v1) {
			return new aiVector2t<T>(v1.x.opMultiply(v1.x.forValue(-1.0)), v1.y.opMultiply(v1.y.forValue(-1.0)));
		}
		
	}
	
	public static class aiVector2D<T extends Number> extends aiVector2t<T> {
		
	}
	
}
