package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaternion;
import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaterniont;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.util.IPointer;

public class anim {

	public static class aiVectorKey {
		public double mTime;

		public aiVector3D mValue;


		public aiVectorKey() {
		}

		public aiVectorKey(double time, aiVector3D value) {

			mTime = time;
			mValue = value;
		}

//		public boolean opEquals(aiVectorKey o) {
//			return o.mValue.opEquals(mValue);
//		}
//
//		public boolean opNotEquals(aiVectorKey o) {
//			return !o.mValue.opEquals(mValue);
//		}
//
//		public boolean opSmaller(aiVectorKey o) {
//			return mTime < o.mTime;
//		}
//
//		public boolean opBigger(aiVectorKey o) {
//			return mTime > o.mTime;
//		}
	}

	public static class aiQuatKey {
		public double mTime;

		public aiQuaternion mValue;


		public aiQuatKey() {
		}


		public aiQuatKey(double time, aiQuaternion value) {
			mTime = time;
			mValue = value;
		}
//
//		public boolean opEquals(aiVectorKey o) {
//			return o.mValue.opEquals(mValue);
//		}
//
//		public boolean opNotEquals(aiVectorKey o) {
//			return !o.mValue.opEquals(mValue);
//		}
//
//		public boolean opSmaller(aiVectorKey o) {
//			return mTime < o.mTime;
//		}
//
//		public boolean opBigger(aiVectorKey o) {
//			return mTime > o.mTime;
//		}
	}

	public static class aiMeshKey {
		public double mTime;
		public int mValue;

		public aiMeshKey() {

		}

		public aiMeshKey(double time, int value) {
			mTime=time;mValue=value;
		}

//		public boolean opEquals(aiMeshKey o) {
//			return o.mValue == this.mValue;
//		}
//
//		public boolean notEquals(aiMeshKey o) {
//			return o.mValue != this.mValue;
//		}
//
//		public boolean opSmaller(aiMeshKey o) {
//			return mTime < o.mTime;
//		}
//
//		public boolean opBigger(aiMeshKey o) {
//			return mTime > o.mTime;
//		}
	}

	public static enum aiAnimBehaviour {
	    aiAnimBehaviour_DEFAULT(0x0),
	    aiAnimBehaviour_CONSTANT(0x1),
	    aiAnimBehaviour_LINEAR(0x2),
	    aiAnimBehaviour_REPEAT(0x3);

	    public int mB;

		private aiAnimBehaviour(int b) {
	    	mB=b;
	    }
	}

	public static class aiNodeAnim {
		public aiString mNodeName;

		public int mNumPositionKeys;
		public aiVectorKey[] mPositionKeys;
		public int mNumRotationKeys;
		public aiQuatKey[] mRotationKeys;
		public int mNumScalingKeys;
		public aiVectorKey[] mScalingKeys;
		public aiAnimBehaviour mPreState;
		public aiAnimBehaviour mPostState;

		public aiNodeAnim() {
			mNodeName = new aiString(); //I guess necessary in Java
			mPositionKeys = new aiVectorKey[0];
			mRotationKeys = new aiQuatKey[0];
			mScalingKeys = new aiVectorKey[0];
			mPreState=aiAnimBehaviour.aiAnimBehaviour_DEFAULT;
			mPostState=aiAnimBehaviour.aiAnimBehaviour_DEFAULT;
		}

		public void destroy() {

		}

	}

	public static class aiMeshAnim {
		public aiString mName;
		public int mNumKeys;
		public aiMeshKey mKeys;

		public aiMeshAnim() {
			mName = new aiString(); //I guess necessary in Java
			mKeys = new aiMeshKey();
		}
	}

	public static class aiAnimation {
		public aiString mName = new aiString();
		public double mDuration;
		public double mTicksPerSecond;
		public int mNumChannels;
		public aiNodeAnim[] mChannels;
		public int mNumMeshChannels;
		public aiMeshAnim[] mMeshChannels;

		public aiAnimation() {
			mDuration = -1.;
			mTicksPerSecond = 0.;
			mNumChannels = 0;
			mChannels=new aiNodeAnim[mNumChannels];
			mNumMeshChannels=0;
			mMeshChannels = new aiMeshAnim[mNumMeshChannels];
		}

		public void destroy() {
			mChannels=null;
			mMeshChannels=null;
			mName=null; //I guess java needs this
		}
	}

	public abstract static class Interpolator {
//		public Number cast(Number a , Number b, ai_real d) {
//			return new ai_real(a).opAdd(d.opMultiply(new ai_real(b.doubleValue()-a.doubleValue()))).getValue();
//		}
//
//		public void cast(IPointer<aiQuaterniont> out, aiQuaterniont a, aiQuaterniont b, ai_real d) {
//			aiQuaterniont.Interpolate(out, a,b,d);
//		}

//		public aiQuaternion cast(aiQuaternion a, aiQuaternion b, ai_real d) {
//			return aiQuaternion.Interpolate(a,b,d);
//		}

//		public int cast(int a, int b, ai_real d) {
//			return d.opBigger(new ai_real(0.5F))?b:a;
//		}
//
//		public aiVector3D cast(aiVectorKey a, aiVectorKey b, ai_real d) {
//			return cast(a.mValue, b.mValue, d);
//		}
//
//		private aiVector3D cast(aiVector3D a, aiVector3D b, ai_real d)
//		{
//			return (aiVector3D) a.opAdd((b.opSubtract(a)).opMultiply(d));
//		}
//
//		public void cast(IPointer<aiQuaterniont> out, aiQuatKey a, aiQuatKey b, ai_real d) {
//			cast(out, a.mValue, b.mValue, d);
//		}

//		public int cast(aiMeshKey a, aiMeshKey b, ai_real d) {
//			return (new ai_real(a.mValue).opAdd(d.opMultiply(new ai_real(b.mValue-a.mValue)))).getValue().intValue();
//		}

	}

}
