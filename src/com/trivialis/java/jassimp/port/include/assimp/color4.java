package com.trivialis.java.jassimp.port.include.assimp;

public class color4 {

	public static class aiColor4D {
		public float r;
		public float g;
		public float b;
		public float a;
		
		public aiColor4D() {

		}
		public aiColor4D(float _r, float _b, float _g, float _a) {
			r=_r;
			g=_g;
			b=_b;
			a=_a;
		}
		public aiColor4D(aiColor4D o) {
			r=o.r;
			g=o.g;
			b=o.b;
			a=o.a;
		}
//		public aiColor4t opAdd(aiColor4t o) {
//			r=r.opAdd(o.r); g=g.opAdd(o.g); b=b.opAdd(o.b); a=a.opAdd(o.a);
//			return this;
//		}
//		public aiColor4t opSubtract(aiColor4t o) {
//			r=r.opSubtract(o.r); g=g.opSubtract(o.g); b=b.opSubtract(o.b); a=a.opSubtract(o.a);
//			return this;
//		}
//		public aiColor4t opMultiply(aiColor4t o) {
//			r=r.opMultiply(o.r); g=g.opMultiply(o.g); b=b.opMultiply(o.b); a=a.opMultiply(o.a);
//			return this;
//		}
//		public aiColor4t opDivide(aiColor4t o) {
//			r=r.opDivide(o.r); g=g.opDivide(o.g); b=b.opDivide(o.b); a=a.opDivide(o.a);
//			return this;
//		}
//		public ai_real[] array() {
//			return new ai_real[]{r,g,b,a};
//		}
//		public float get(int index) {
//			return array()[index];
//		}
		public boolean opEquals(aiColor4D o) {
			return r == (o.r) && g == (o.g) && b == (o.b) && a == (o.a);
		}
//		public boolean opNotEquals(aiColor4t o) {
//			return !r == (o.r) || !g == (o.g) || !b == (o.b) || !a == (o.a);
//		}
//		public boolean opSmaller(aiColor4t o) {
//		    return r.opSmaller(o.r) || (
//		            r == (o.r) && (
//		                g.opSmaller(o.g) || (
//		                    g == (o.g) && (
//		                        b.opSmaller(o.b) || (
//		                            b == (o.b) && (
//		                                a.opSmaller(o.a)
//		                            )
//		                        )
//		                    )
//		                )
//		            )
//		        );
//		}
//		//Todo static functions
//		public boolean IsBlack() {
//			float epsilon = new ai_real(Math.pow(10, -3));
//			return new ai_real(std.abs(r.getValue().doubleValue())).opSmaller(epsilon) && new ai_real(std.abs(g.getValue().doubleValue())).opSmaller(epsilon) && new ai_real(std.abs(b.getValue().doubleValue())).opSmaller(epsilon);
//		}
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + Float.floatToIntBits(a);
			result = prime * result + Float.floatToIntBits(b);
			result = prime * result + Float.floatToIntBits(g);
			result = prime * result + Float.floatToIntBits(r);
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
			aiColor4D other = (aiColor4D) obj;
			if(!this.opEquals(other)) {
				return false;
			}
			return true;
		}
		
	}
}
