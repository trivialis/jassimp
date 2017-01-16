package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.util.std;

public class color4 {

	public abstract static class aiColor4t<T extends Number> {
		public ai_real<T> r;
		public ai_real<T> g;
		public ai_real<T> b;
		public ai_real<T> a;
		public aiColor4t() {
			
		}
		public aiColor4t(ai_real<T> _r, ai_real<T> _b, ai_real<T> _g, ai_real<T> _a) {
			r=_r;
			g=_g;
			b=_b;
			a=_a;
		}
		public aiColor4t(aiColor4t<T> o) {
			r=o.r;
			g=o.g;
			b=o.b;
			a=o.a;
		}
		public aiColor4t<T> opAdd(aiColor4t<T> o) {
			r=r.opAdd(o.r); g=g.opAdd(o.g); b=b.opAdd(o.b); a=a.opAdd(o.a);
			return this;
		}
		public aiColor4t<T> opSubtract(aiColor4t<T> o) {
			r=r.opSubtract(o.r); g=g.opSubtract(o.g); b=b.opSubtract(o.b); a=a.opSubtract(o.a);
			return this;
		}
		public aiColor4t<T> opMultiply(aiColor4t<T> o) {
			r=r.opMultiply(o.r); g=g.opMultiply(o.g); b=b.opMultiply(o.b); a=a.opMultiply(o.a);
			return this;
		}
		public aiColor4t<T> opDivide(aiColor4t<T> o) {
			r=r.opDivide(o.r); g=g.opDivide(o.g); b=b.opDivide(o.b); a=a.opDivide(o.a);
			return this;
		}
		public ai_real<T>[] array() {
			return new ai_real[]{r,g,b,a};
		}
		public ai_real<T> get(int index) {
			return array()[index];
		}
		public boolean opEquals(aiColor4t<T> o) {
			return r.opEquals(o.r) && g.opEquals(o.g) && b.opEquals(o.b) && a.opEquals(o.a);
		}
		public boolean opNotEquals(aiColor4t<T> o) {
			return !r.opEquals(o.r) || !g.opEquals(o.g) || !b.opEquals(o.b) || !a.opEquals(o.a);
		}
		public boolean opSmaller(aiColor4t<T> o) {
		    return r.opSmaller(o.r) || (
		            r.opEquals(o.r) && (
		                g.opSmaller(o.g) || (
		                    g.opEquals(o.g) && (
		                        b.opSmaller(o.b) || (
		                            b.opEquals(o.b) && (
		                                a.opSmaller(o.a)
		                            )
		                        )
		                    )
		                )
		            )
		        );
		}
		//Todo static functions
		public boolean IsBlack() {
			ai_real<T> epsilon = r.forValue( Math.pow(10, -3));
			return r.forValue(std.abs(r.getValue().doubleValue())).opSmaller(epsilon) && g.forValue(std.abs(g.getValue().doubleValue())).opSmaller(epsilon) && b.forValue(std.abs(b.getValue().doubleValue())).opSmaller(epsilon);
		}
	}
	
	public static class aiColor4D<T extends Number> extends aiColor4t<T> {
		
	}
	
}
