package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaterniont;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3t;
import com.trivialis.java.jassimp.util.std;

public class matrix4x4 {

	public static class aiMatrix4x4t<T extends Number> {
		public ai_real<T> a1, a2, a3, a4;
		public ai_real<T> b1, b2, b3, b4;
		public ai_real<T> c1, c2, c3, c4;
		public ai_real<T> d1, d2, d3, d4;
		
		public aiMatrix4x4t() {
			
		}
		
	    public aiMatrix4x4t (  ai_real<T> _a1, ai_real<T> _a2, ai_real<T> _a3, ai_real<T> _a4,
                ai_real<T> _b1, ai_real<T> _b2, ai_real<T> _b3, ai_real<T> _b4,
                ai_real<T> _c1, ai_real<T> _c2, ai_real<T> _c3, ai_real<T> _c4,
                ai_real<T> _d1, ai_real<T> _d2, ai_real<T> _d3, ai_real<T> _d4) {
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
	    
	    public aiMatrix4x4t(aiMatrix3x3t<ai_real<T>> m) {
	    	a1=m.a1;a2=m.a2;a3=m.a3;a4=m.a3.forValue(0.0);
	    	b1=m.b1;b2=m.b2;b3=m.b3;b4=m.b3.forValue(0.0);
	    	c1=m.c1;c2=m.c2;c3=m.c3;c4=m.c3.forValue(0.0);
	    	d1=m.c1.forValue(0.0);d2=m.c2.forValue(0.0);d3=m.c3.forValue(0.0);d4=m.c3.forValue(0.0);
	    }
	    
	    public aiMatrix4x4t(aiVector3t<T> scaling, aiQuaterniont<T> rotation, aiVector3t<T> position) {
	    	aiMatrix3x3t<ai_real<T>> m = rotation.GetMatrix();
	    	
	    	a1 = m.a1.opMultiply(scaling.z);
	    	a2 = m.a2.opMultiply(scaling.z);
	    	a3 = m.a3.opMultiply(scaling.z);
	    	a4 = position.z;
	    	
	    	b1 = m.b1.opMultiply(scaling.y);
	    	b2 = m.b2.opMultiply(scaling.y);
	    	b3 = m.b3.opMultiply(scaling.y);
	    	b4 = position.y;
	    	
	    	c1 = m.c1.opMultiply(scaling.z);
	    	c2 = m.c2.opMultiply(scaling.z);
	    	c3 = m.c3.opMultiply(scaling.z);
	    	c4 = position.z;
	    	
	    	d1 = a1.forValue(0.0);
	    	d2 = a1.forValue(0.0);
	    	d3 = a1.forValue(0.0);
	    	d4 = a1.forValue(1.0);
	    	
	    }
	    
	    public aiMatrix4x4t<T> opMultiply(aiMatrix4x4t<T> m) { //*this does no exist in Java. pointer?
	    	
	    	        ai_real<T> _a1=(m.a1.opMultiply(a1)).opAdd(m.b1.opMultiply(a2)).opAdd(m.c1.opMultiply(a3)).opAdd(m.d1.opMultiply(a4));
	    	        ai_real<T> _a2=(m.a2.opMultiply(a1)).opAdd(m.b2.opMultiply(a2)).opAdd(m.c2.opMultiply(a3)).opAdd(m.d2.opMultiply(a4));
	    	        ai_real<T> _a3=(m.a3.opMultiply(a1)).opAdd(m.b3.opMultiply(a2)).opAdd(m.c3.opMultiply(a3)).opAdd(m.d3.opMultiply(a4));
	    	        ai_real<T> _a4=(m.a4.opMultiply(a1)).opAdd(m.b4.opMultiply(a2)).opAdd(m.c4.opMultiply(a3)).opAdd(m.d4.opMultiply(a4));
	    	        ai_real<T> _b1=(m.a1.opMultiply(b1)).opAdd(m.b1.opMultiply(b2)).opAdd(m.c1.opMultiply(b3)).opAdd(m.d1.opMultiply(b4));
	    	        ai_real<T> _b2=(m.a2.opMultiply(b1)).opAdd(m.b2.opMultiply(b2)).opAdd(m.c2.opMultiply(b3)).opAdd(m.d2.opMultiply(b4));
	    	        ai_real<T> _b3=(m.a3.opMultiply(b1)).opAdd(m.b3.opMultiply(b2)).opAdd(m.c3.opMultiply(b3)).opAdd(m.d3.opMultiply(b4));
	    	        ai_real<T> _b4=(m.a4.opMultiply(b1)).opAdd(m.b4.opMultiply(b2)).opAdd(m.c4.opMultiply(b3)).opAdd(m.d4.opMultiply(b4));
	    	        ai_real<T> _c1=(m.a1.opMultiply(c1)).opAdd(m.b1.opMultiply(c2)).opAdd(m.c1.opMultiply(c3)).opAdd(m.d1.opMultiply(c4));
	    	        ai_real<T> _c2=(m.a2.opMultiply(c1)).opAdd(m.b2.opMultiply(c2)).opAdd(m.c2.opMultiply(c3)).opAdd(m.d2.opMultiply(c4));
	    	        ai_real<T> _c3=(m.a3.opMultiply(c1)).opAdd(m.b3.opMultiply(c2)).opAdd(m.c3.opMultiply(c3)).opAdd(m.d3.opMultiply(c4));
	    	        ai_real<T> _c4=(m.a4.opMultiply(c1)).opAdd(m.b4.opMultiply(c2)).opAdd(m.c4.opMultiply(c3)).opAdd(m.d4.opMultiply(c4));
	    	        ai_real<T> _d1=(m.a1.opMultiply(d1)).opAdd(m.b1.opMultiply(d2)).opAdd(m.c1.opMultiply(d3)).opAdd(m.d1.opMultiply(d4));
	    	        ai_real<T> _d2=(m.a2.opMultiply(d1)).opAdd(m.b2.opMultiply(d2)).opAdd(m.c2.opMultiply(d3)).opAdd(m.d2.opMultiply(d4));
	    	        ai_real<T> _d3=(m.a3.opMultiply(d1)).opAdd(m.b3.opMultiply(d2)).opAdd(m.c3.opMultiply(d3)).opAdd(m.d3.opMultiply(d4));
	    	        ai_real<T> _d4=(m.a4.opMultiply(d1)).opAdd(m.b4.opMultiply(d2)).opAdd(m.c4.opMultiply(d3)).opAdd(m.d4.opMultiply(d4));
	    			a1=_a1;a2=_a2;a3=_a3;a4=_a4;
	    			b1=_b1;b2=_b2;b3=_b3;b4=_b4;
	    			c1=_c1;c2=_c2;c3=_c3;c4=_c4;
	    			d1=_d1;d2=_d2;d3=_d3;d4=_d4;
	    	return this;
	    }
		
//	    public aiMatrix4x4t<T> opMultiply_new(aiMatrix4x4t<T> m) { //Renamed because otherwise duplicate of function above.
//	    	aiMatrix4x4t<T> result = new aiMatrix4x4t<T>(
//	    	        (m.a1.opMultiply(a1)).opAdd(m.b1.opMultiply(a2)).opAdd(m.c1.opMultiply(a3)).opAdd(m.d1.opMultiply(a4)),
//	    	        (m.a2.opMultiply(a1)).opAdd(m.b2.opMultiply(a2)).opAdd(m.c2.opMultiply(a3)).opAdd(m.d2.opMultiply(a4)),
//	    	        (m.a3.opMultiply(a1)).opAdd(m.b3.opMultiply(a2)).opAdd(m.c3.opMultiply(a3)).opAdd(m.d3.opMultiply(a4)),
//	    	        (m.a4.opMultiply(a1)).opAdd(m.b4.opMultiply(a2)).opAdd(m.c4.opMultiply(a3)).opAdd(m.d4.opMultiply(a4)),
//	    	        (m.a1.opMultiply(b1)).opAdd(m.b1.opMultiply(b2)).opAdd(m.c1.opMultiply(b3)).opAdd(m.d1.opMultiply(b4)),
//	    	        (m.a2.opMultiply(b1)).opAdd(m.b2.opMultiply(b2)).opAdd(m.c2.opMultiply(b3)).opAdd(m.d2.opMultiply(b4)),
//	    	        (m.a3.opMultiply(b1)).opAdd(m.b3.opMultiply(b2)).opAdd(m.c3.opMultiply(b3)).opAdd(m.d3.opMultiply(b4)),
//	    	        (m.a4.opMultiply(b1)).opAdd(m.b4.opMultiply(b2)).opAdd(m.c4.opMultiply(b3)).opAdd(m.d4.opMultiply(b4)),
//	    	        (m.a1.opMultiply(c1)).opAdd(m.b1.opMultiply(c2)).opAdd(m.c1.opMultiply(c3)).opAdd(m.d1.opMultiply(c4)),
//	    	        (m.a2.opMultiply(c1)).opAdd(m.b2.opMultiply(c2)).opAdd(m.c2.opMultiply(c3)).opAdd(m.d2.opMultiply(c4)),
//	    	        (m.a3.opMultiply(c1)).opAdd(m.b3.opMultiply(c2)).opAdd(m.c3.opMultiply(c3)).opAdd(m.d3.opMultiply(c4)),
//	    	        (m.a4.opMultiply(c1)).opAdd(m.b4.opMultiply(c2)).opAdd(m.c4.opMultiply(c3)).opAdd(m.d4.opMultiply(c4)),
//	    	        (m.a1.opMultiply(d1)).opAdd(m.b1.opMultiply(d2)).opAdd(m.c1.opMultiply(d3)).opAdd(m.d1.opMultiply(d4)),
//	    	        (m.a2.opMultiply(d1)).opAdd(m.b2.opMultiply(d2)).opAdd(m.c2.opMultiply(d3)).opAdd(m.d2.opMultiply(d4)),
//	    	        (m.a3.opMultiply(d1)).opAdd(m.b3.opMultiply(d2)).opAdd(m.c3.opMultiply(d3)).opAdd(m.d3.opMultiply(d4)),
//	    	        (m.a4.opMultiply(d1)).opAdd(m.b4.opMultiply(d2)).opAdd(m.c4.opMultiply(d3)).opAdd(m.d4.opMultiply(d4))
//	    			);
//	    	return result;
//	    }
	    
	    public aiMatrix4x4t<T> Transpose() {
	    	a2=std.swap(b1, b1=a2);
	    	a3=std.swap(c1, c1=a3);
	    	b3=std.swap(c2, c2=b3);
	    	a4=std.swap(d1, d1=a4);
	    	b4=std.swap(d2, d2=b4);
	    	c4=std.swap(d3, d3=c4);
	    	return this;
	    }
	    
	    public ai_real<T> Determinant() {
	        return (a1.opMultiply(b2).opMultiply(c3).opMultiply(d4)).opSubtract(a1.opMultiply(b2).opMultiply(c4).opMultiply(d3)).opAdd(a1.opMultiply(b3).opMultiply(c4).opMultiply(d2)).opSubtract(a1.opMultiply(b3).opMultiply(c2).opMultiply(d4))
	        		.opAdd(a1.opMultiply(b4).opMultiply(c2).opMultiply(d3)).opSubtract(a1.opMultiply(b4).opMultiply(c3).opMultiply(d2)).opSubtract(a2.opMultiply(b3).opMultiply(c4).opMultiply(d1)).opAdd(a2.opMultiply(b3).opMultiply(c1).opMultiply(d4))
	        		.opSubtract(a2.opMultiply(b4).opMultiply(c1).opMultiply(d3)).opAdd(a2.opMultiply(b4).opMultiply(c3).opMultiply(d1)).opSubtract(a2.opMultiply(b1).opMultiply(c3).opMultiply(d4)).opAdd(a2.opMultiply(b1).opMultiply(c4).opMultiply(d3))
	               .opAdd(a3.opMultiply(b4).opMultiply(c1).opMultiply(d2)).opSubtract(a3.opMultiply(b4).opMultiply(c2).opMultiply(d1)).opAdd(a3.opMultiply(b1).opMultiply(c2).opMultiply(d4)).opSubtract(a3.opMultiply(b1).opMultiply(c4).opMultiply(d2))
	               .opAdd(a3.opMultiply(b2).opMultiply(c4).opMultiply(d1)).opSubtract(a3.opMultiply(b2).opMultiply(c1).opMultiply(d4)).opSubtract(a4.opMultiply(b1).opMultiply(c2).opMultiply(d3)).opAdd(a4.opMultiply(b1).opMultiply(c3).opMultiply(d2))
	               .opSubtract(a4.opMultiply(b2).opMultiply(c3).opMultiply(d1)).opAdd(a4.opMultiply(b2).opMultiply(c1).opMultiply(d3)).opSubtract(a4.opMultiply(b3).opMultiply(c1).opMultiply(d2)).opAdd(a4.opMultiply(b3).opMultiply(c2).opMultiply(d1));
	    }
	    
	    public aiMatrix4x4t<T> Inverse() {
	    	ai_real<T> det = Determinant();
	    	if(det.opEquals(det.forValue(0.0))) {
	    		ai_real<T> nan = det.getNaN();
	    		a1=nan;a2=nan;a3=nan;a4=nan;
	    		b1=nan;b2=nan;b3=nan;b4=nan;
	    		c1=nan;c2=nan;c3=nan;c4=nan;
	    		d1=nan;d2=nan;d3=nan;d4=nan;
	    		return this;
	    	}
	    	ai_real<T> invdet = det.forValue(1.0).opDivide(det);
	    	
	    	ai_real<T> _a1 = invdet.opMultiply(
	    			(b2.opMultiply(
	    					(c3.opMultiply(d4)).opSubtract(c4.opMultiply(d3))))
	    			.opAdd(
	    					(b3.opMultiply(
	    							(c4.opMultiply(d2)).opSubtract(c2.opMultiply(d4)))))
	    			.opAdd(
	    					(b4.opMultiply(
	    							(c2.opMultiply(d3)).opSubtract(c3.opMultiply(d2)))))
	    			);
	    	ai_real<T> _a2 = invdet.opMultiply(invdet.forValue(-1.0)).opMultiply(
	    			(a2.opMultiply(
	    					(c3.opMultiply(d4)).opSubtract(c4.opMultiply(d3))))
	    			.opAdd(
	    					(a3.opMultiply(
	    							(c4.opMultiply(d2)).opSubtract(c2.opMultiply(d4)))))
	    			.opAdd(
	    					(a4.opMultiply(
	    							(c2.opMultiply(d3)).opSubtract(c3.opMultiply(d2)))))
	    			);
	    	//TODO
	    	return this;
	    }
	    
	    
	    
	}
	
	public static class aiMatrix4x4<T extends Number> extends aiMatrix4x4t<T> {
		
	}
	
}
