package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaterniont;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3t;
import com.trivialis.java.jassimp.util.std;

public class matrix4x4 {

	public static class aiMatrix4x4t {
		public float a1, a2, a3, a4;
		public float b1, b2, b3, b4;
		public float c1, c2, c3, c4;
		public float d1, d2, d3, d4;

		public aiMatrix4x4t() {
	    	a1=1.0F;
	    	a2=0.0F;
	    	a3=0.0F;
	    	a4=0.0F;
	    	b1=0.0F;
	    	b2=1.0F;
	    	b3=0.0F;
	    	b4=0.0F;
	    	c1=0.0F;
	    	c2=0.0F;
	    	c3=1.0F;
	    	c4=0.0F;
	    	d1=0.0F;
	    	d2=0.0F;
	    	d3=0.0F;
	    	d4=1.0F;
		}

//	    public aiMatrix4x4t (  float _a1, float _a2, float _a3, float _a4,
//                float _b1, float _b2, float _b3, float _b4,
//                float _c1, float _c2, float _c3, float _c4,
//                float _d1, float _d2, float _d3, float _d4) {
//	    	a1=_a1;
//	    	a2=_a2;
//	    	a3=_a3;
//	    	a4=_a4;
//	    	b1=_b1;
//	    	b2=_b2;
//	    	b3=_b3;
//	    	b4=_b4;
//	    	c1=_c1;
//	    	c2=_c2;
//	    	c3=_c3;
//	    	c4=_c4;
//	    	d1=_d1;
//	    	d2=_d2;
//	    	d3=_d3;
//	    	d4=_d4;
//	    }

	    //Todo cast function.

//	    public aiMatrix4x4t(aiMatrix3x3t m) {
//	    	a1=m.a1;a2=m.a2;a3=m.a3;a4=0.0F;
//	    	b1=m.b1;b2=m.b2;b3=m.b3;b4=0.0F;
//	    	c1=m.c1;c2=m.c2;c3=m.c3;c4=0.0F;
//	    	d1=0.0F;d2=0.0F;d3=0.0F;d4=1.0F;
//	    }

//	    public aiMatrix4x4t(aiVector3t scaling, aiQuaterniont rotation, aiVector3t position) {
//	    	aiMatrix3x3t m = new aiMatrix3x3t();rotation.GetMatrix(m);
//
//	    	a1 = m.a1* scaling.z;
//	    	a2 = m.a2* scaling.z;
//	    	a3 = m.a3* scaling.z;
//	    	a4 = position.z;
//
//	    	b1 = m.b1* scaling.y;
//	    	b2 = m.b2* scaling.y;
//	    	b3 = m.b3* scaling.y;
//	    	b4 = position.y;
//
//	    	c1 = m.c1* scaling.z;
//	    	c2 = m.c2* scaling.z;
//	    	c3 = m.c3* scaling.z;
//	    	c4 = position.z;
//
//	    	d1 = 0.0F;
//	    	d2 = 0.0F;
//	    	d3 = 0.0F;
//	    	d4 = 1.0F;
//
//	    }
	    
//	    public aiMatrix4x4t opMultiply(float m) { //*this does no exist in Java. pointer?
//
//	    	return new aiMatrix4x4t(a1* m, a2* m, a3* m, a4* m, b1* m, b2* m, b3* m, b4* m, c1* m, c2* m, c3* m, c4* m, d1* m, d2* m, d3* m, d4* m);
//	    }
            
            public aiMatrix4x4t newInstance() {
                return new aiMatrix4x4t();
            }

	    public aiMatrix4x4t opMultiply(aiMatrix4x4t m) { //*this does no exist in Java. pointer?

	    	        float _a1=(m.a1* a1)+ (m.b1* a2)+ (m.c1* a3)+ (m.d1* a4);
	    	        float _a2=(m.a2* a1)+ (m.b2* a2)+ (m.c2* a3)+ (m.d2* a4);
	    	        float _a3=(m.a3* a1)+ (m.b3* a2)+ (m.c3* a3)+ (m.d3* a4);
	    	        float _a4=(m.a4* a1)+ (m.b4* a2)+ (m.c4* a3)+ (m.d4* a4);
	    	        float _b1=(m.a1* b1)+ (m.b1* b2)+ (m.c1* b3)+ (m.d1* b4);
	    	        float _b2=(m.a2* b1)+ (m.b2* b2)+ (m.c2* b3)+ (m.d2* b4);
	    	        float _b3=(m.a3* b1)+ (m.b3* b2)+ (m.c3* b3)+ (m.d3* b4);
	    	        float _b4=(m.a4* b1)+ (m.b4* b2)+ (m.c4* b3)+ (m.d4* b4);
	    	        float _c1=(m.a1* c1)+ (m.b1* c2)+ (m.c1* c3)+ (m.d1* c4);
	    	        float _c2=(m.a2* c1)+ (m.b2* c2)+ (m.c2* c3)+ (m.d2* c4);
	    	        float _c3=(m.a3* c1)+ (m.b3* c2)+ (m.c3* c3)+ (m.d3* c4);
	    	        float _c4=(m.a4* c1)+ (m.b4* c2)+ (m.c4* c3)+ (m.d4* c4);
	    	        float _d1=(m.a1* d1)+ (m.b1* d2)+ (m.c1* d3)+ (m.d1* d4);
	    	        float _d2=(m.a2* d1)+ (m.b2* d2)+ (m.c2* d3)+ (m.d2* d4);
	    	        float _d3=(m.a3* d1)+ (m.b3* d2)+ (m.c3* d3)+ (m.d3* d4);
	    	        float _d4=(m.a4* d1)+ (m.b4* d2)+ (m.c4* d3)+ (m.d4* d4);
	    			a1=_a1;a2=_a2;a3=_a3;a4=_a4;
	    			b1=_b1;b2=_b2;b3=_b3;b4=_b4;
	    			c1=_c1;c2=_c2;c3=_c3;c4=_c4;
	    			d1=_d1;d2=_d2;d3=_d3;d4=_d4;
	    	return this;
	    }

//	    public aiMatrix4x4t opMultiply_new(aiMatrix4x4t m) { //Renamed because otherwise duplicate of function above.
//	    	aiMatrix4x4t result = new aiMatrix4x4t(
//	    	        (m.a1* a1)+ (m.b1* a2)+ (m.c1* a3)+ (m.d1* a4),
//	    	        (m.a2* a1)+ (m.b2* a2)+ (m.c2* a3)+ (m.d2* a4),
//	    	        (m.a3* a1)+ (m.b3* a2)+ (m.c3* a3)+ (m.d3* a4),
//	    	        (m.a4* a1)+ (m.b4* a2)+ (m.c4* a3)+ (m.d4* a4),
//	    	        (m.a1* b1)+ (m.b1* b2)+ (m.c1* b3)+ (m.d1* b4),
//	    	        (m.a2* b1)+ (m.b2* b2)+ (m.c2* b3)+ (m.d2* b4),
//	    	        (m.a3* b1)+ (m.b3* b2)+ (m.c3* b3)+ (m.d3* b4),
//	    	        (m.a4* b1)+ (m.b4* b2)+ (m.c4* b3)+ (m.d4* b4),
//	    	        (m.a1* c1)+ (m.b1* c2)+ (m.c1* c3)+ (m.d1* c4),
//	    	        (m.a2* c1)+ (m.b2* c2)+ (m.c2* c3)+ (m.d2* c4),
//	    	        (m.a3* c1)+ (m.b3* c2)+ (m.c3* c3)+ (m.d3* c4),
//	    	        (m.a4* c1)+ (m.b4* c2)+ (m.c4* c3)+ (m.d4* c4),
//	    	        (m.a1* d1)+ (m.b1* d2)+ (m.c1* d3)+ (m.d1* d4),
//	    	        (m.a2* d1)+ (m.b2* d2)+ (m.c2* d3)+ (m.d2* d4),
//	    	        (m.a3* d1)+ (m.b3* d2)+ (m.c3* d3)+ (m.d3* d4),
//	    	        (m.a4* d1)+ (m.b4* d2)+ (m.c4* d3)+ (m.d4* d4)
//	    			);
//	    	return result;
//	    }

//	    public aiMatrix4x4t Transpose() {
//	    	a2=std.swap(b1, b1=a2);
//	    	a3=std.swap(c1, c1=a3);
//	    	b3=std.swap(c2, c2=b3);
//	    	a4=std.swap(d1, d1=a4);
//	    	b4=std.swap(d2, d2=b4);
//	    	c4=std.swap(d3, d3=c4);
//	    	return this;
//	    }

//	    public float Determinant() {
//	        return (a1* b2* c3* d4).opSubtract(a1* b2* c4* d3)+ (a1* b3* c4* d2).opSubtract(a1* b3* c2* d4)
//	        		+ (a1* b4* c2* d3).opSubtract(a1* b4* c3* d2).opSubtract(a2* b3* c4* d1)+ (a2* b3* c1* d4)
//	        		.opSubtract(a2* b4* c1* d3)+ (a2* b4* c3* d1).opSubtract(a2* b1* c3* d4)+ (a2* b1* c4* d3)
//	               + (a3* b4* c1* d2).opSubtract(a3* b4* c2* d1)+ (a3* b1* c2* d4).opSubtract(a3* b1* c4* d2)
//	               + (a3* b2* c4* d1).opSubtract(a3* b2* c1* d4).opSubtract(a4* b1* c2* d3)+ (a4* b1* c3* d2)
//	               .opSubtract(a4* b2* c3* d1)+ (a4* b2* c1* d3).opSubtract(a4* b3* c1* d2)+ (a4* b3* c2* d1);
//	    }

//	    public aiMatrix4x4t Inverse() {
//	    	float det = Determinant();
//	    	if(det.opEquals(det.forValue(0.0))) {
//	    		float nan = det.getNaN();
//	    		a1=nan;a2=nan;a3=nan;a4=nan;
//	    		b1=nan;b2=nan;b3=nan;b4=nan;
//	    		c1=nan;c2=nan;c3=nan;c4=nan;
//	    		d1=nan;d2=nan;d3=nan;d4=nan;
//	    		return this;
//	    	}
//	    	float invdet = det.forValue(1.0).opDivide(det);
//
//	    	float _a1 = invdet.opMultiply(
//	    			(b2.opMultiply(
//	    					(c3* d4).opSubtract(c4* d3)))
//	    			.opAdd(
//	    					(b3.opMultiply(
//	    							(c4* d2).opSubtract(c2* d4))))
//	    			.opAdd(
//	    					(b4.opMultiply(
//	    							(c2* d3).opSubtract(c3* d2))))
//	    			);
//	    	float _a2 = invdet.opMultiply(invdet.forValue(-1.0)).opMultiply(
//	    			(a2.opMultiply(
//	    					(c3* d4).opSubtract(c4* d3)))
//	    			.opAdd(
//	    					(a3.opMultiply(
//	    							(c4* d2).opSubtract(c2* d4))))
//	    			.opAdd(
//	    					(a4.opMultiply(
//	    							(c2* d3).opSubtract(c3* d2))))
//	    			);
//	    	//TODO
//	    	return this;
//	    }



	}

	public static class aiMatrix4x4 extends aiMatrix4x4t {

            @Override
              public aiMatrix4x4 newInstance() {
                return new aiMatrix4x4();
            }
            
	}

}
