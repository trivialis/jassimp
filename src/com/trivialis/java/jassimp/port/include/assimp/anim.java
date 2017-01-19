package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaternion;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.util.Pointer;

public class anim {

	public static class aiVectorKey {
		public double mTime;
		public aiVector3D<Number> mValue;

		public aiVectorKey() {
		}

		public aiVectorKey(double time, aiVector3D<Number> value) {
			mTime = time;
			mValue = value;
		}

		public boolean opEquals(aiVectorKey o) {
			return o.mValue.opEquals(mValue);
		}

		public boolean opNotEquals(aiVectorKey o) {
			return false;
		}

		public boolean opSmaller(aiVectorKey o) {
			return false;
		}

		public boolean opBigger(aiVectorKey o) {
			return false;
		}
	}

	public static class aiQuatKey {
		public double mTime;
		public aiQuaternion<Number> mValue;

		public aiQuatKey() {
		}

		public aiQuatKey(double time, aiQuaternion<Number> value) {
			mTime = time;
			mValue = value;
		}

		public boolean opEquals(aiQuatKey o) {
			return o.mValue.opEquals(mValue);
		}

		public boolean opNotEquals(aiQuatKey o) {
			return false;
		}

		public boolean opSmaller(aiQuatKey o) {
			return false;
		}

		public boolean opBigger(aiQuatKey o) {
			return false;
		}
	}

	public static class aiMeshKey {
		public double mTime;
		public int mValue;

		public aiMeshKey() {

		}

		public aiMeshKey(double time, int value) {
			mTime=time;mValue=value;
		}

		public boolean opEquals(aiMeshKey o) {
			return o.mValue == this.mValue;
		}

		public boolean notEquals(aiMeshKey o) {
			return o.mValue != this.mValue;
		}

		public boolean opSmaller(aiMeshKey o) {
			return mTime < o.mTime;
		}

		public boolean opBigger(aiMeshKey o) {
			return mTime > o.mTime;
		}
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
		public aiVectorKey mPositionKeys;
		public int mNumRotationKeys;
		public aiQuatKey mRotationKeys;
		public int mNumScalingKeys;
		public aiVectorKey mScalingKeys;
		public aiAnimBehaviour mPreState;
		public aiAnimBehaviour mPostState;

		public aiNodeAnim() {
			mNodeName = new aiString(); //I guess necessary in Java
			mPositionKeys = new aiVectorKey();
			mRotationKeys = new aiQuatKey();
			mScalingKeys = new aiVectorKey();
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
		public Number cast(Number a , Number b, ai_real<Number> d) {
			return d.forValue(a).opAdd(d.opMultiply(d.forValue(b.doubleValue()-a.doubleValue()))).getValue();
		}

		public aiQuaternion<Number> cast(aiQuaternion<Number> a, aiQuaternion<Number> b, ai_real<Number> d) {
			return aiQuaternion.Interpolate(a,b,d);
		}

		public int cast(int a, int b, ai_real<Number> d) {
			return d.opBigger2(new ai_real<Float>(0.5F))?b:a;
		}

		public aiVector3D<Number> cast(aiVectorKey a, aiVectorKey b, ai_real<Number> d) {
			return cast(a.mValue, b.mValue, d);
		}

		private aiVector3D<Number> cast(aiVector3D<Number> a, aiVector3D<Number> b, ai_real<Number> d)
		{
			return (aiVector3D<Number>) a.opAdd((b.opSubtract(a)).opMultiply(d));
		}

		public aiQuaternion<Number> cast(aiQuatKey a, aiQuatKey b, ai_real<Number> d) {
			return cast(a.mValue, b.mValue, d);
		}

		public int cast(aiMeshKey a, aiMeshKey b, ai_real<Number> d) {
			return (new ai_real<Integer>(a.mValue).opAdd2(d.opMultiply2(new ai_real<Double>((double) (b.mValue-a.mValue))))).getValue();
		}

	}

}
