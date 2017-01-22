package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3t;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3t;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.std;

public class quaternion {

	public static class aiQuaterniont {

		public ai_real w;
		public ai_real x;
		public ai_real y;
		public ai_real z;

		public aiQuaterniont()
		{
			w=new ai_real(1.0);
		}

		public aiQuaterniont(ai_real pw, ai_real px, ai_real py, ai_real pz)
		{
			w=pw; x=px; y=py; z=pz;
		}

		public aiQuaterniont(  aiMatrix3x3t pRotMatrix)
		{
		    ai_real t = pRotMatrix.a1.opAdd(pRotMatrix.b2).opAdd(pRotMatrix.c3);

		    if( t.opBigger(new ai_real(0)))
		    {
		        ai_real s = new ai_real(std.sqrt((Double) new ai_real(1).opAdd(t).getValue())).opMultiply(new ai_real(2.0));
		        x = (pRotMatrix.c2.opSubtract(pRotMatrix.b3)).opDivide(s);
		        y = (pRotMatrix.a3.opSubtract(pRotMatrix.c1)).opDivide(s);
		        z = (pRotMatrix.b1.opSubtract(pRotMatrix.a2)).opDivide(s);
		        w = new ai_real(0.25).opMultiply(s);
		    }
		    else if( pRotMatrix.a1.opBigger(pRotMatrix.b2) && pRotMatrix.a1.opBigger(pRotMatrix.c3))
		    {
		        ai_real s = new ai_real(std.sqrt((Double) (new ai_real(1.0).opAdd(pRotMatrix.a1).opSubtract(pRotMatrix.b2).opSubtract(pRotMatrix.c3).getValue()))).opMultiply(new ai_real(2.0));
		        x = new ai_real(0.25).opMultiply(s);
		        y = (pRotMatrix.b1.opAdd(pRotMatrix.a2)).opDivide(s);
		        z = (pRotMatrix.a3.opAdd(pRotMatrix.c1)).opDivide(s);
		        w = (pRotMatrix.c2.opSubtract(pRotMatrix.b3)).opDivide(s);
		    }
		    else if( pRotMatrix.b2.opBigger(pRotMatrix.c3))
		    {

		        ai_real s = new ai_real(std.sqrt((Double) ( new ai_real(1.0).opAdd(pRotMatrix.b2).opSubtract(pRotMatrix.a1).opSubtract(pRotMatrix.c3)).getValue())).opMultiply(new ai_real(2.0));
		        x = (pRotMatrix.b1.opAdd(pRotMatrix.a2)).opDivide(s);
		        y = new ai_real(0.25).opMultiply(s);
		        z = (pRotMatrix.c2.opAdd(pRotMatrix.b3)).opDivide(s);
		        w = (pRotMatrix.a3.opSubtract(pRotMatrix.c1)).opDivide(s);
		    } else
		    {

		        ai_real s = new ai_real(std.sqrt((Double) ( new ai_real(1.0).opAdd(pRotMatrix.c3).opSubtract(pRotMatrix.a1).opSubtract(pRotMatrix.b2)).getValue())).opMultiply(new ai_real(2.0));
		        x = (pRotMatrix.a3.opAdd(pRotMatrix.c1)).opDivide(s);
		        y = (pRotMatrix.c2.opAdd(pRotMatrix.b3)).opDivide(s);
		        z = new ai_real(0.25).opMultiply(s);
		        w = (pRotMatrix.b1.opSubtract(pRotMatrix.a2)).opDivide(s);
		    }
		}

		public aiQuaterniont( ai_real fPitch, ai_real fYaw, ai_real fRoll )
		{
		    ai_real fSinPitch = std.sin(fPitch.opMultiply(new ai_real(0.5)));
		    ai_real fCosPitch = std.cos(fPitch.opMultiply(new ai_real(0.5)));
		    ai_real fSinYaw = std.sin(fYaw.opMultiply(new ai_real(0.5)));
		    ai_real fCosYaw = std.cos(fYaw.opMultiply(new ai_real(0.5)));
		    ai_real fSinRoll = std.sin(fRoll.opMultiply(new ai_real(0.5)));
		    ai_real fCosRoll = std.cos(fRoll.opMultiply(new ai_real(0.5)));
		    ai_real fCosPitchCosYaw = fCosPitch.opMultiply(fCosYaw);
		    ai_real fSinPitchSinYaw = fSinPitch.opMultiply(fSinYaw);
		    x = (fSinRoll.opMultiply(fCosPitchCosYaw)).opSubtract(fCosRoll.opMultiply(fSinPitchSinYaw));
		    y = (fCosRoll.opMultiply(fSinPitch).opMultiply(fCosYaw)).opAdd(fSinRoll.opMultiply(fCosPitch).opMultiply(fSinYaw));
		    z = (fCosRoll.opMultiply(fCosPitch).opMultiply(fSinYaw)).opSubtract(fSinRoll.opMultiply(fSinPitch).opMultiply(fCosYaw));
		    w = (fCosRoll.opMultiply(fCosPitchCosYaw)).opAdd(fSinRoll.opMultiply(fSinPitchSinYaw));
		}

		public <Matrix extends aiMatrix3x3t> Matrix GetMatrix(Matrix resMatrix)
		{

		    resMatrix.a1 = new ai_real(1.0).opSubtract((new ai_real(2.0).opMultiply((y.opMultiply(y).opAdd(z.opMultiply(z))))));
		    resMatrix.a2 = new ai_real(2.0).opMultiply((x.opMultiply(y)).opSubtract((z.opMultiply(w))));
		    resMatrix.a3 = new ai_real(2.0).opMultiply((x.opMultiply(z)).opAdd((y.opMultiply(w))));
		    resMatrix.b1 = new ai_real(2.0).opMultiply((x.opMultiply(y)).opAdd((z.opMultiply(w))));
		    resMatrix.b2 = new ai_real(1.0).opSubtract((new ai_real(2.0).opMultiply(((x.opMultiply(x)).opAdd((z.opMultiply(z)))))));
		    resMatrix.b3 = new ai_real(2.0).opMultiply((y.opMultiply(z)).opSubtract((x.opMultiply(w))));
		    resMatrix.c1 = new ai_real(2.0).opMultiply((x.opMultiply(z)).opSubtract((y.opMultiply(w))));
		    resMatrix.c2 = new ai_real(2.0).opMultiply((y.opMultiply(z)).opAdd((x.opMultiply(w))));
		    resMatrix.c3 = new ai_real(1.0).opSubtract((new ai_real(2.0).opMultiply(((x.opMultiply(x)).opAdd((y.opMultiply(y)))))));

		    return resMatrix;
		}

		public aiQuaterniont( aiVector3t axis, ai_real angle)
		{
		    axis.Normalize();

		    ai_real sin_a = std.sin( angle.opDivide(new ai_real(2)));
		    ai_real cos_a = std.cos( angle.opDivide(new ai_real(2)));
		    x    = axis.x.opMultiply(sin_a);
		    y    = axis.y.opMultiply(sin_a);
		    z    = axis.z.opMultiply(sin_a);
		    w    = cos_a;
		}

		public aiQuaterniont( aiVector3t normalized)
		{
		    x = normalized.x;
		    y = normalized.y;
		    z = normalized.z;

		    ai_real t = new ai_real(1.0).opSubtract((x.opMultiply(x))).opSubtract((y.opMultiply(y))).opSubtract((z.opMultiply(z)));

		    if (t.opSmaller(new ai_real(0.0))) {
		        w = new ai_real(0.0);
		    }
		    else w = std.sqrt(t);
		}

		public static void Interpolate(IPointer<aiQuaterniont> pOut,  aiQuaterniont pStart,  aiQuaterniont pEnd, ai_real pFactor)
		{

		    ai_real cosom = ((pStart.x.opMultiply(pEnd.x))).opAdd((pStart.y.opMultiply(pEnd.y))).opAdd((pStart.z.opMultiply(pEnd.z))).opAdd((pStart.w.opMultiply(pEnd.w)));


		    aiQuaterniont end = pEnd;
		    if( cosom.opSmaller(new ai_real(0.0)))
		    {
		        cosom = cosom.opNegate();
		        end.x = end.x.opNegate();
		        end.y = end.y.opNegate();
		        end.z = end.z.opNegate();
		        end.w = end.w.opNegate();
		    }


		    ai_real sclp, sclq;
		    if( (new ai_real(1.0).opSubtract(cosom)).opBigger(new ai_real(0.0001)))
		    {

		        ai_real omega, sinom;
		        omega = std.acos( cosom);
		        sinom = std.sin( omega);
		        sclp  = std.sin( (new ai_real(1.0).opSubtract(pFactor)).opMultiply(omega)).opDivide(sinom);
		        sclq  = std.sin( (pFactor.opMultiply(omega))).opDivide(sinom);
		    } else
		    {

		        sclp = new ai_real(1.0).opSubtract(pFactor);
		        sclq = pFactor;
		    }

		    pOut.get().x = ((sclp.opMultiply(pStart.x))).opAdd((sclq.opMultiply(end.x)));
		    pOut.get().y = ((sclp.opMultiply(pStart.y))).opAdd((sclq.opMultiply(end.y)));
		    pOut.get().z = ((sclp.opMultiply(pStart.z))).opAdd((sclq.opMultiply(end.z)));
		    pOut.get().w = ((sclp.opMultiply(pStart.w))).opAdd((sclq.opMultiply(end.w)));
		}

		public aiQuaterniont Normalize()
		{

		    ai_real mag = std.sqrt((x.opMultiply(x)).opAdd((y.opMultiply(y)).opAdd(z.opMultiply(z)).opAdd((w.opMultiply(w)))));
		    if (!mag.opEquals(new ai_real(0.0)))
		    {
		        ai_real invMag = new ai_real(1.0).opDivide(mag);
		        x = x.opMultiply(invMag);
		        y = y.opMultiply(invMag);
		        z = z.opMultiply(invMag);
		        w = w.opMultiply(invMag);
		    }
		    return this;
		}

		public aiQuaterniont opMultiply ( aiQuaterniont t)
		{
		    return new aiQuaterniont(((w.opMultiply(t.w))).opSubtract((x.opMultiply(t.x))).opSubtract((y.opMultiply(t.y))).opSubtract((z.opMultiply(t.z))),
		        ((w.opMultiply(t.x))).opAdd((x.opMultiply(t.w))).opAdd((y.opMultiply(t.z))).opSubtract((z.opMultiply(t.y))),
		        ((w.opMultiply(t.y))).opAdd((y.opMultiply(t.w))).opAdd((z.opMultiply(t.x))).opSubtract((x.opMultiply(t.z))),
		        ((w.opMultiply(t.z))).opAdd((z.opMultiply(t.w))).opAdd((x.opMultiply(t.y))).opSubtract((y.opMultiply(t.x))));
		}

		public aiQuaterniont Conjugate ()
		{
		    x = x.opNegate();
		    y = y.opNegate();
		    z = z.opNegate();
		    return this;
		}

		public aiVector3t Rotate ( aiVector3t v)
		{
		    aiQuaterniont q2 = new aiQuaterniont(new ai_real(0.f),v.x,v.y,v.z);
		    aiQuaterniont q = this;
		    aiQuaterniont qinv = q;
		    qinv.Conjugate();

		    q = q.opMultiply(q2).opMultiply(qinv);
		    return new aiVector3t(q.x,q.y,q.z);
		}

		public boolean opEquals ( aiQuaterniont o)
		{
		    return x == o.x && y == o.y && z == o.z && w == o.w;
		}

		public boolean opNotEquals ( aiQuaterniont o)
		{
		    return !opEquals(o);
		}

		public boolean Equal( aiQuaterniont o, ai_real epsilon)  {
		    return
		        new ai_real(std.abs((Double) x.opSubtract(o.x).getValue())).opSmallerOrEqual(epsilon) &&
		        new ai_real(std.abs((Double) y.opSubtract(o.y).getValue())).opSmallerOrEqual(epsilon) &&
				new ai_real(std.abs((Double) z.opSubtract(o.z).getValue())).opSmallerOrEqual(epsilon) &&
				new ai_real(std.abs((Double) w.opSubtract(o.w).getValue())).opSmallerOrEqual(epsilon);
		}


	}

	public static class aiQuaternion extends aiQuaterniont {

		public aiQuaternion(aiMatrix3x3 m)
		{
			super(m);
		}



	}

}
