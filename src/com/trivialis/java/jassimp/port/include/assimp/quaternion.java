package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3;
import com.trivialis.java.jassimp.util.std;

public class quaternion {

	public static class aiQuaternion {

		public float w;
		public float x;
		public float y;
		public float z;

//		public aiQuaternion()
//		{
//			w= 1.0F;
//		}
//
//		public aiQuaternion(float pw, float px, float py, float pz)
//		{
//			w=pw; x=px; y=py; z=pz;
//		}

                //TODO : Test
		public aiQuaternion(  aiMatrix3x3 pRotMatrix)
		{
		    float t = pRotMatrix.a1+ (pRotMatrix.b2)+ (pRotMatrix.c3);

		    if( t > ( 0F))
		    {
		        float s = std.sqrt(1.0F+ (t))*( 2.0F);
		        x = (pRotMatrix.c2 - (pRotMatrix.b3)) / (s);
		        y = (pRotMatrix.a3 - (pRotMatrix.c1)) / (s);
		        z = (pRotMatrix.b1 - (pRotMatrix.a2)) / (s);
		        w =  0.25F * (s);
		    }
		    else if( pRotMatrix.a1 > (pRotMatrix.b2) && pRotMatrix.a1 > (pRotMatrix.c3))
		    {
		        float s = (float) (std.sqrt((double) ( 1.0F+ (pRotMatrix.a1) - (pRotMatrix.b2) - (pRotMatrix.c3)))*( 2.0F));
		        x =  0.25F * (s);
		        y = (pRotMatrix.b1+ (pRotMatrix.a2)) / (s);
		        z = (pRotMatrix.a3+ (pRotMatrix.c1)) / (s);
		        w = (pRotMatrix.c2 - (pRotMatrix.b3)) / (s);
		    }
		    else if( pRotMatrix.b2 > (pRotMatrix.c3))
		    {

		        float s = (float) (std.sqrt((double) (  1.0F+ (pRotMatrix.b2) - (pRotMatrix.a1) - (pRotMatrix.c3))) * ( 2.0F));
		        x = (pRotMatrix.b1+ (pRotMatrix.a2)) / (s);
		        y =  0.25F * (s);
		        z = (pRotMatrix.c2+ (pRotMatrix.b3)) / (s);
		        w = (pRotMatrix.a3 - (pRotMatrix.c1)) / (s);
		    } else
		    {

		        float s = (float) (std.sqrt((double) (  1.0F+ (pRotMatrix.c3) - (pRotMatrix.a1) - (pRotMatrix.b2))) * ( 2.0F));
		        x = (pRotMatrix.a3+ (pRotMatrix.c1)) / (s);
		        y = (pRotMatrix.c2+ (pRotMatrix.b3)) / (s);
		        z =  0.25F * (s);
		        w = (pRotMatrix.b1 - (pRotMatrix.a2)) / (s);
		    }
		}

//		public aiQuaternion( float fPitch, float fYaw, float fRoll )
//		{
//		    float fSinPitch = std.sin(fPitch * ( 0.5F));
//		    float fCosPitch = std.cos(fPitch * ( 0.5F));
//		    float fSinYaw = std.sin(fYaw * ( 0.5F));
//		    float fCosYaw = std.cos(fYaw * ( 0.5F));
//		    float fSinRoll = std.sin(fRoll * ( 0.5F));
//		    float fCosRoll = std.cos(fRoll * ( 0.5F));
//		    float fCosPitchCosYaw = fCosPitch * (fCosYaw);
//		    float fSinPitchSinYaw = fSinPitch * (fSinYaw);
//		    x = (fSinRoll * (fCosPitchCosYaw)) - (fCosRoll * (fSinPitchSinYaw));
//		    y = (fCosRoll * (fSinPitch) * (fCosYaw)).opAdd(fSinRoll * (fCosPitch) * (fSinYaw));
//		    z = (fCosRoll * (fCosPitch) * (fSinYaw)) - (fSinRoll * (fSinPitch) * (fCosYaw));
//		    w = (fCosRoll * (fCosPitchCosYaw)).opAdd(fSinRoll * (fSinPitchSinYaw));
//		}

                //TODO: Test
		public <Matrix extends aiMatrix3x3> Matrix GetMatrix(Matrix resMatrix)
		{

		    resMatrix.a1 =  1.0F-(
                            ( 2.0F
                                    *(
                                            ((y * (y))+((z * (z))))
                                        )
                                    )
                    );
		    resMatrix.a2 =  2.0F * ((x * (y)) - ((z * (w))));
		    resMatrix.a3 =  2.0F * ((x * (z))+((y * (w))));
		    resMatrix.b1 =  2.0F * ((x * (y))+((z * (w))));
		    resMatrix.b2 =  1.0F - (( 2.0F * (((x * (x))+((z * (z)))))));
		    resMatrix.b3 =  2.0F * ((y * (z)) - ((x * (w))));
		    resMatrix.c1 =  2.0F * ((x * (z)) - ((y * (w))));
		    resMatrix.c2 =  2.0F * ((y * (z))+((x * (w))));
		    resMatrix.c3 =  1.0F - (( 2.0F * (((x * (x))+((y * (y)))))));

		    return resMatrix;
		}

//		public aiQuaternion( aiVector3t axis, float angle)
//		{
//		    axis.Normalize();
//
//		    float sin_a = std.sin( angle / ( 2.0fF));
//		    float cos_a = std.cos( angle / ( 2.0fF));
//		    x    = axis.x * (sin_a);
//		    y    = axis.y * (sin_a);
//		    z    = axis.z * (sin_a);
//		    w    = cos_a;
//		}

//		public aiQuaternion( aiVector3t normalized)
//		{
//		    x = normalized.x;
//		    y = normalized.y;
//		    z = normalized.z;
//
//		    float t =  1.0F - ((x * (x))) - ((y * (y))) - ((z * (z)));
//
//		    if (t.opSmaller( 0.0F)) {
//		        w =  0.0F;
//		    }
//		    else w = std.sqrt(t);
//		}

//		public static void Interpolate(IPointer<aiQuaternion> pOut,  aiQuaternion pStart,  aiQuaternion pEnd, float pFactor)
//		{
//
//		    float cosom = ((pStart.x * (pEnd.x))).opAdd((pStart.y * (pEnd.y))).opAdd((pStart.z * (pEnd.z))).opAdd((pStart.w * (pEnd.w)));
//
//
//		    aiQuaternion end = pEnd;
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

//		public aiQuaternion Normalize()
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
//		public aiQuaternion opMultiply ( aiQuaternion t)
//		{
//		    return new aiQuaternion(((w * (t.w))) - ((x * (t.x))) - ((y * (t.y))) - ((z * (t.z))),
//		        ((w * (t.x))).opAdd((x * (t.w))).opAdd((y * (t.z))) - ((z * (t.y))),
//		        ((w * (t.y))).opAdd((y * (t.w))).opAdd((z * (t.x))) - ((x * (t.z))),
//		        ((w * (t.z))).opAdd((z * (t.w))).opAdd((x * (t.y))) - ((y * (t.x))));
//		}

//		public aiQuaternion Conjugate ()
//		{
//		    x = x.opNegate();
//		    y = y.opNegate();
//		    z = z.opNegate();
//		    return this;
//		}
//
//		public aiVector3t Rotate ( aiVector3t v)
//		{
//		    aiQuaternion q2 = new aiQuaternion( 0.fF,v.x,v.y,v.z);
//		    aiQuaternion q = this;
//		    aiQuaternion qinv = q;
//		    qinv.Conjugate();
//
//		    q = q * (q2) * (qinv);
//		    return new aiVector3t(q.x,q.y,q.z);
//		}
//
//		public boolean opEquals ( aiQuaternion o)
//		{
//		    return x == o.x && y == o.y && z == o.z && w == o.w;
//		}
//
//		public boolean opNotEquals ( aiQuaternion o)
//		{
//		    return !opEquals(o);
//		}

//		public boolean Equal( aiQuaternion o, float epsilon)  {
//		    return
//		        new ai_real(std.abs((Double) x - (o.x).getValue())).opSmallerOrEqual(epsilon) &&
//		        new ai_real(std.abs((Double) y - (o.y).getValue())).opSmallerOrEqual(epsilon) &&
//				new ai_real(std.abs((Double) z - (o.z).getValue())).opSmallerOrEqual(epsilon) &&
//				new ai_real(std.abs((Double) w - (o.w).getValue())).opSmallerOrEqual(epsilon);
//		}


	}


}
