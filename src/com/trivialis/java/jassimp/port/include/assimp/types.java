package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real_float;
import com.trivialis.java.jassimp.util.std;

public class types {

	public static class aiColor3D<T extends ai_real>
	{
	    private ai_real r;
		private ai_real g;
		private ai_real b;
		public aiColor3D () {
	    	
	    }
	    public aiColor3D(T _r, T _g, T _b) {
	    	r=_r;
	    	g=_g;
	    	b=_b;
	    }
	    public aiColor3D(aiColor3D<T> o) {
	    	r=o.r;
	    	g=o.g;
	    	b=o.b;
	    }
	    public boolean opEquals(aiColor3D<T> o) {
	    	return r.opEquals(o.r) && g.opEquals(o.g) && b.opEquals(o.b);
	    }

	    public boolean opNotEquals(aiColor3D<T> o) {
	    	return !r.opEquals(o.r) || !g.opEquals(o.g) || !b.opEquals(o.b);
	    }
	    
	    public boolean opSmaller(aiColor3D<T> o) {
	    	return r.opSmaller(o.r) || ( r.opEquals(o.r) && (g.opSmaller(o.g) || (g.opEquals(o.g) && b.opSmaller(o.b))));
	    }

	    public aiColor3D<ai_real> add(aiColor3D<T> c) {
	    	return new aiColor3D<ai_real>(r.opAdd(c.r),g.opAdd(c.g),b.opAdd(c.b));
	    }
	    
	    public aiColor3D<ai_real> subtract(aiColor3D<T> c) {
	    	return new aiColor3D<ai_real>(r.opSubtract(c.r),g.opSubtract(c.g),b.opSubtract(c.b));
	    }

	    public aiColor3D<ai_real> multiply(aiColor3D<T> c) {
	    	return new aiColor3D<ai_real>(r.opMultiply(c.r),g.opMultiply(c.g),b.opMultiply(c.b));
	    }
	    
	    public aiColor3D<ai_real> multiply(ai_real f) {
	    	return new aiColor3D<ai_real>(r.opMultiply(f),g.opMultiply(f),b.opMultiply(f));
	    }
	    
	    public ai_real[] array() {
	    	return new ai_real[]{r,g,b};
	    }
	    
	    public ai_real get(int i) {
	    	return array()[i];
	    }
	    private static final ai_real_float epsilon = new ai_real_float((float) Math.pow(10, -3));
	    public boolean IsBlack() {
	    	return std.fabs(r).opSmaller(epsilon) && std.fabs(g).opSmaller(epsilon)&&std.fabs(b).opSmaller(epsilon);
	    }
	    
	}
	
}
