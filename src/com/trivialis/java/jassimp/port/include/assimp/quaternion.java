package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3t;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.std;

public class quaternion {

	public static class aiQuaterniont {

		public float w;
		public float x;
		public float y;
		public float z;

//		public aiQuaterniont()
//		{
//			w= 1.0F;
//		}
//
//		public aiQuaterniont(float pw, float px, float py, float pz)
//		{
//			w=pw; x=px; y=py; z=pz;
//		}

                //TODO : Test
		public aiQuaterniont(  aiMatrix3x3t pRotMatrix)
		{
		    float t = pRotMatrix.a1+ (pRotMatrix.b2)+ (pRotMatrix.c3);

		    if( t > ( 0F))
		    {
		        float s = new ai_real(std.sqrt((Double)  1F+ (t).getValue())).opMultiply( 2.0F);
		        x = (pRotMatrix.c2 - (pRotMatrix.b3)) / (s);
		        y = (pRotMatrix.a3 - (pRotMatrix.c1)) / (s);
		        z = (pRotMatrix.b1 - (pRotMatrix.a2)) / (s);
		        w =  0.25F * (s);
		    }
		    else if( pRotMatrix.a1 > (pRotMatrix.b2) && pRotMatrix.a1 > (pRotMatrix.c3))
		    {
		        float s = new ai_real(std.sqrt((Double) ( 1.0F+ (pRotMatrix.a1) - (pRotMatrix.b2) - (pRotMatrix.c3).getValue()))).opMultiply( 2.0F);
		        x =  0.25F * (s);
		        y = (pRotMatrix.b1+ (pRotMatrix.a2)) / (s);
		        z = (pRotMatrix.a3+ (pRotMatrix.c1)) / (s);
		        w = (pRotMatrix.c2 - (pRotMatrix.b3)) / (s);
		    }
		    else if( pRotMatrix.b2 > (pRotMatrix.c3))
		    {

		        float s = new ai_real(std.sqrt((Double) (  1.0F+ (pRotMatrix.b2) - (pRotMatrix.a1) - (pRotMatrix.c3)).getValue())).opMultiply( 2.0F);
		        x = (pRotMatrix.b1+ (pRotMatrix.a2)) / (s);
		        y =  0.25F * (s);
		        z = (pRotMatrix.c2+ (pRotMatrix.b3)) / (s);
		        w = (pRotMatrix.a3 - (pRotMatrix.c1)) / (s);
		    } else
		    {

		        float s = new ai_real(std.sqrt((Double) (  1.0F+ (pRotMatrix.c3) - (pRotMatrix.a1) - (pRotMatrix.b2)).getValue())).opMultiply( 2.0F);
		        x = (pRotMatrix.a3+ (pRotMatrix.c1)) / (s);
		        y = (pRotMatrix.c2+ (pRotMatrix.b3)) / (s);
		        z =  0.25F * (s);
		        w = (pRotMatrix.b1 - (pRotMatrix.a2)) / (s);
		    }
		}

//		public aiQuaterniont( float fPitch, float fYaw, float fRoll )
//		{
//		    float fSinPitch = std.sin(fPitch.opMultiply( 0.5F));
//		    float fCosPitch = std.cos(fPitch.opMultiply( 0.5F));
//		    float fSinYaw = std.sin(fYaw.opMultiply( 0.5F));
//		    float fCosYaw = std.cos(fYaw.opMultiply( 0.5F));
//		    float fSinRoll = std.sin(fRoll.opMultiply( 0.5F));
//		    float fCosRoll = std.cos(fRoll.opMultiply( 0.5F));
//		    float fCosPitchCosYaw = fCosPitch * (fCosYaw);
//		    float fSinPitchSinYaw = fSinPitch * (fSinYaw);
//		    x = (fSinRoll * (fCosPitchCosYaw)).opSubtract(fCosRoll * (fSinPitchSinYaw));
//		    y = (fCosRoll * (fSinPitch) * (fCosYaw)).opAdd(fSinRoll * (fCosPitch) * (fSinYaw));
//		    z = (fCosRoll * (fCosPitch) * (fSinYaw)).opSubtract(fSinRoll * (fSinPitch) * (fCosYaw));
//		    w = (fCosRoll * (fCosPitchCosYaw)).opAdd(fSinRoll * (fSinPitchSinYaw));
//		}

                //TODO: Test
		public <Matrix extends aiMatrix3x3t> Matrix GetMatrix(Matrix resMatrix)
		{

		    resMatrix.a1 =  1.0F-(
                            ( 2.0F
                                    *(
                                            ((y * (y))+((z * (z))))
                                        )
                                    )
                    );
		    resMatrix.a2 =  2.0F.opMultiply((x * (y)).opSubtract((z * (w))));
		    resMatrix.a3 =  2.0F.opMultiply((x * (z))+((y * (w))));
		    resMatrix.b1 =  2.0F.opMultiply((x * (y))+((z * (w))));
		    resMatrix.b2 =  1.0F.opSubtract(( 2.0F.opMultiply(((x * (x))+((z * (z)))))));
		    resMatrix.b3 =  2.0F.opMultiply((y * (z)).opSubtract((x * (w))));
		    resMatrix.c1 =  2.0F.opMultiply((x * (z)).opSubtract((y * (w))));
		    resMatrix.c2 =  2.0F.opMultiply((y * (z))+((x * (w))));
		    resMatrix.c3 =  1.0F.opSubtract(( 2.0F.opMultiply(((x * (x))+((y * (y)))))));

		    return resMatrix;
		}

//		public aiQuaterniont( aiVector3t axis, float angle)
//		{
//		    axis.Normalize();
//
//		    float sin_a = std.sin( angle / ( 2.0fF));
//		    float cos_a = std.cos( angle / ( 2.0fF));
//		    x    = axis.x.opMultiply(sin_a);
//		    y    = axis.y.opMultiply(sin_a);
//		    z    = axis.z.opMultiply(sin_a);
//		    w    = cos_a;
//		}

//		public aiQuaterniont( aiVector3t normalized)
//		{
//		    x = normalized.x;
//		    y = normalized.y;
//		    z = normalized.z;
//
//		    float t =  1.0F.opSubtract((x * (x))).opSubtract((y * (y))).opSubtract((z * (z)));
//
//		    if (t.opSmaller( 0.0F)) {
//		        w =  0.0F;
//		    }
//		    else w = std.sqrt(t);
//		}

//		public static void Interpolate(IPointer<aiQuaterniont> pOut,  aiQuaterniont pStart,  aiQuaterniont pEnd, float pFactor)
//		{
//
//		    float cosom = ((pStart.x * (pEnd.x))).opAdd((pStart.y * (pEnd.y))).opAdd((pStart.z * (pEnd.z))).opAdd((pStart.w * (pEnd.w)));
//
//
//		    aiQuaterniont end = pEnd;
//		    if( cosom.opSmaller( 0.0F))
//		    {
//		        cosom = cosom.opNegate();
//		        end.x = end.x.opNegate();
//		        end.y = end.y.opNegate();
//		        end.z = end.z.opNegate();
//		        end.w = end.w.opNegate();
//		    }
//
//
//		    float sclp, sclq;
//		    if( ( 1.0F - (cosom)) > ( 0.0001F))
//		    {
//
//		        float omega, sinom;
//		        omega = std.acos( cosom);
//		        sinom = std.sin( omega);
//		        sclp  = std.sin( ( 1.0F - (pFactor)) * (omega)) / (sinom);
//		        sclq  = std.sin( (pFactor * (omega))) / (sinom);
//		    } else
//		    {
//
//		        sclp =  1.0F - (pFactor);
//		        sclq = pFactor;
//		    }
//
//		    pOut.get().x = ((sclp * (pStart.x))).opAdd((sclq * (end.x)));
//		    pOut.get().y = ((sclp * (pStart.y))).opAdd((sclq * (end.y)));
//		    pOut.get().z = ((sclp * (pStart.z))).opAdd((sclq * (end.z)));
//		    pOut.get().w = ((sclp * (pStart.w))).opAdd((sclq * (end.w)));
//		}

//		public aiQuaterniont Normalize()
//		{
//
//		    float mag = std.sqrt((x * (x)).opAdd((y * (y)).opAdd(z * (z)).opAdd((w * (w)))));
//		    if (!mag.opEquals( 0.0F))
//		    {
//		        float invMag =  1.0F / (mag);
//		        x = x * (invMag);
//		        y = y * (invMag);
//		        z = z * (invMag);
//		        w = w * (invMag);
//		    }
//		    return this;
//		}
//
//		public aiQuaterniont opMultiply ( aiQuaterniont t)
//		{
//		    return new aiQuaterniont(((w * (t.w))).opSubtract((x * (t.x))).opSubtract((y * (t.y))).opSubtract((z * (t.z))),
//		        ((w * (t.x))).opAdd((x * (t.w))).opAdd((y * (t.z))).opSubtract((z * (t.y))),
//		        ((w * (t.y))).opAdd((y * (t.w))).opAdd((z * (t.x))).opSubtract((x * (t.z))),
//		        ((w * (t.z))).opAdd((z * (t.w))).opAdd((x * (t.y))).opSubtract((y * (t.x))));
//		}

//		public aiQuaterniont Conjugate ()
//		{
//		    x = x.opNegate();
//		    y = y.opNegate();
//		    z = z.opNegate();
//		    return this;
//		}
//
//		public aiVector3t Rotate ( aiVector3t v)
//		{
//		    aiQuaterniont q2 = new aiQuaterniont( 0.fF,v.x,v.y,v.z);
//		    aiQuaterniont q = this;
//		    aiQuaterniont qinv = q;
//		    qinv.Conjugate();
//
//		    q = q * (q2) * (qinv);
//		    return new aiVector3t(q.x,q.y,q.z);
//		}
//
//		public boolean opEquals ( aiQuaterniont o)
//		{
//		    return x == o.x && y == o.y && z == o.z && w == o.w;
//		}
//
//		public boolean opNotEquals ( aiQuaterniont o)
//		{
//		    return !opEquals(o);
//		}

//		public boolean Equal( aiQuaterniont o, float epsilon)  {
//		    return
//		        new ai_real(std.abs((Double) x - (o.x).getValue())).opSmallerOrEqual(epsilon) &&
//		        new ai_real(std.abs((Double) y - (o.y).getValue())).opSmallerOrEqual(epsilon) &&
//				new ai_real(std.abs((Double) z - (o.z).getValue())).opSmallerOrEqual(epsilon) &&
//				new ai_real(std.abs((Double) w - (o.w).getValue())).opSmallerOrEqual(epsilon);
//		}


	}

	public static class aiQuaternion extends aiQuaterniont {

		public aiQuaternion(aiMatrix3x3 m)
		{
			super(m);
		}



	}

}
