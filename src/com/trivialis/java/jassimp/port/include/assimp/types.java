package com.trivialis.java.jassimp.port.include.assimp;

import java.util.Arrays;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.util.std;

public class types {

	public static class aiColor3D
	{
	    private ai_real r;
		private ai_real g;
		private ai_real b;
		public aiColor3D () {

	    }
	    public aiColor3D(ai_real _r, ai_real _g, ai_real _b) {
	    	r=_r;
	    	g=_g;
	    	b=_b;
	    }
	    public aiColor3D(aiColor3D o) {
	    	r=o.r;
	    	g=o.g;
	    	b=o.b;
	    }
	    public boolean opEquals(aiColor3D o) {
	    	return r.opEquals(o.r) && g.opEquals(o.g) && b.opEquals(o.b);
	    }

	    public boolean opNotEquals(aiColor3D o) {
	    	return !r.opEquals(o.r) || !g.opEquals(o.g) || !b.opEquals(o.b);
	    }

	    public boolean opSmaller(aiColor3D o) {
	    	return r.opSmaller(o.r) || ( r.opEquals(o.r) && (g.opSmaller(o.g) || (g.opEquals(o.g) && b.opSmaller(o.b))));
	    }

	    public aiColor3D add(aiColor3D c) {
	    	return new aiColor3D(r.opAdd(c.r),g.opAdd(c.g),b.opAdd(c.b));
	    }

	    public aiColor3D subtract(aiColor3D c) {
	    	return new aiColor3D(r.opSubtract(c.r),g.opSubtract(c.g),b.opSubtract(c.b));
	    }

	    public aiColor3D multiply(aiColor3D c) {
	    	return new aiColor3D(r.opMultiply(c.r),g.opMultiply(c.g),b.opMultiply(c.b));
	    }

	    public aiColor3D multiply(ai_real f) {
	    	return new aiColor3D(r.opMultiply(f),g.opMultiply(f),b.opMultiply(f));
	    }

	    public ai_real[] array() {
	    	return (ai_real[]) Arrays.asList(r,g,b).toArray();
	    }

	    public ai_real get(int i) {
	    	return array()[i];
	    }
	    public boolean IsBlack() {
	    	ai_real epsilon = r.forValue(Math.pow(10, -3));
	    	return r.forValue(std.abs(r.getValue().doubleValue())).opSmaller(epsilon) && g.forValue(std.abs(g.getValue().doubleValue())).opSmaller(epsilon)&& b.forValue(std.abs(b.getValue().doubleValue())).opSmaller(epsilon);
	    }

	}

}
