package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaterniont;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3t;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real_float;

public class matrix4x4 {

	public abstract static class aiMatrix4x4t<T extends ai_real> {
		public ai_real a1, a2, a3, a4;
		public ai_real b1, b2, b3, b4;
		public ai_real c1, c2, c3, c4;
		public ai_real d1, d2, d3, d4;
		
		public aiMatrix4x4t() {
			
		}
		
	    public aiMatrix4x4t (  ai_real _a1, ai_real _a2, ai_real _a3, ai_real _a4,
                ai_real _b1, ai_real _b2, ai_real _b3, ai_real _b4,
                ai_real _c1, ai_real _c2, ai_real _c3, ai_real _c4,
                ai_real _d1, ai_real _d2, ai_real _d3, ai_real _d4) {
	    	a1=_a1;
	    	a2=_a2;
	    	a3=_a3;
	    	a4=_a4;
	    	b1=_b1;
	    	b2=_b2;
	    	b3=_b3;
	    	b4=_b4;
	    	c1=_c1;
	    	c2=_c2;
	    	c3=_c3;
	    	c4=_c4;
	    	d1=_d1;
	    	d2=_d2;
	    	d3=_d3;
	    	d4=_d4;
	    }
	    
	    //Todo cast function.
	    
	    public aiMatrix4x4t(aiMatrix3x3t<ai_real> m) {
	    	a1=m.a1;a2=m.a2;a3=m.a3;a4=a1.forValue(0.0);
	    	b1=m.b1;b2=m.b2;b3=m.b3;b4=a1.forValue(0.0);
	    	c1=m.c1;c2=m.c2;c3=m.c3;c4=a1.forValue(0.0);
	    	d1=a1.forValue(0.0);d2=a1.forValue(0.0);d3=a1.forValue(0.0);d4=a1.forValue(0.0);
	    }
	    
	    public aiMatrix4x4t(aiVector3t<ai_real> scaling, aiQuaterniont<ai_real> rotation, aiVector3t<ai_real> position) {
	    	aiMatrix3x3t<ai_real> m = rotation.GetMatrix();
	    	
	    	
	    	
	    	
	    	
	    }
		
	}
	
	public static class aiMatrix4x4<T extends ai_real> extends aiMatrix4x4t<T> {
		
	}
	
}
