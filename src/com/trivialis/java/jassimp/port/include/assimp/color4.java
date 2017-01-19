package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.util.std;

public class color4 {

	public abstract static class aiColor4t {
		public ai_real r;
		public ai_real g;
		public ai_real b;
		public ai_real a;
		public aiColor4t() {

		}
		public aiColor4t(ai_real _r, ai_real _b, ai_real _g, ai_real _a) {
			r=_r;
			g=_g;
			b=_b;
			a=_a;
		}
		public aiColor4t(aiColor4t o) {
			r=o.r;
			g=o.g;
			b=o.b;
			a=o.a;
		}
		public aiColor4t opAdd(aiColor4t o) {
			r=r.opAdd(o.r); g=g.opAdd(o.g); b=b.opAdd(o.b); a=a.opAdd(o.a);
			return this;
		}
		public aiColor4t opSubtract(aiColor4t o) {
			r=r.opSubtract(o.r); g=g.opSubtract(o.g); b=b.opSubtract(o.b); a=a.opSubtract(o.a);
			return this;
		}
		public aiColor4t opMultiply(aiColor4t o) {
			r=r.opMultiply(o.r); g=g.opMultiply(o.g); b=b.opMultiply(o.b); a=a.opMultiply(o.a);
			return this;
		}
		public aiColor4t opDivide(aiColor4t o) {
			r=r.opDivide(o.r); g=g.opDivide(o.g); b=b.opDivide(o.b); a=a.opDivide(o.a);
			return this;
		}
		public ai_real[] array() {
			return new ai_real[]{r,g,b,a};
		}
		public ai_real get(int index) {
			return array()[index];
		}
		public boolean opEquals(aiColor4t o) {
			return r.opEquals(o.r) && g.opEquals(o.g) && b.opEquals(o.b) && a.opEquals(o.a);
		}
		public boolean opNotEquals(aiColor4t o) {
			return !r.opEquals(o.r) || !g.opEquals(o.g) || !b.opEquals(o.b) || !a.opEquals(o.a);
		}
		public boolean opSmaller(aiColor4t o) {
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
			ai_real epsilon = r.forValue(Math.pow(10, -3));
			return r.forValue(std.abs(r.getValue().doubleValue())).opSmaller(epsilon) && g.forValue(std.abs(g.getValue().doubleValue())).opSmaller(epsilon) && b.forValue(std.abs(b.getValue().doubleValue())).opSmaller(epsilon);
		}
	}

	public static class aiColor4D extends aiColor4t {

	}

}
