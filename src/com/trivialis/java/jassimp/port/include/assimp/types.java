package com.trivialis.java.jassimp.port.include.assimp;

import java.util.Arrays;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.util.std;

public class types {

	public static class aiColor3D<T extends Number>
	{
	    private ai_real<T> r;
		private ai_real<T> g;
		private ai_real<T> b;
		public aiColor3D () {
	    	
	    }
	    public aiColor3D(ai_real<T> _r, ai_real<T> _g, ai_real<T> _b) {
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

	    public aiColor3D<T> add(aiColor3D<T> c) {
	    	return new aiColor3D<T>(r.opAdd(c.r),g.opAdd(c.g),b.opAdd(c.b));
	    }
	    
	    public aiColor3D<T> subtract(aiColor3D<T> c) {
	    	return new aiColor3D<T>(r.opSubtract(c.r),g.opSubtract(c.g),b.opSubtract(c.b));
	    }

	    public aiColor3D<T> multiply(aiColor3D<T> c) {
	    	return new aiColor3D<T>(r.opMultiply(c.r),g.opMultiply(c.g),b.opMultiply(c.b));
	    }
	    
	    public aiColor3D<T> multiply(ai_real<T> f) {
	    	return new aiColor3D<T>(r.opMultiply(f),g.opMultiply(f),b.opMultiply(f));
	    }
	    
	    public ai_real<T>[] array() {
	    	return (ai_real<T>[]) Arrays.asList(r,g,b).toArray();
	    }
	    
	    public ai_real<T> get(int i) {
	    	return array()[i];
	    }
	    public boolean IsBlack() {
	    	ai_real<T> epsilon = r.forValue(Math.pow(10, -3));
	    	return r.forValue(std.abs(r.getValue().doubleValue())).opSmaller(epsilon) && g.forValue(std.abs(g.getValue().doubleValue())).opSmaller(epsilon)&& b.forValue(std.abs(b.getValue().doubleValue())).opSmaller(epsilon);
	    }
	    
	}
	
}
