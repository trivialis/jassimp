package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaternion;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;

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
		public aiQuaternion mValue;

		public aiQuatKey() {
		}

		public aiQuatKey(double time, aiQuaternion value) {
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

}
