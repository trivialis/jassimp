package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaternion;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;

public class anim {
	
	public static class aiVectorKey<T extends Number> {
		public double mTime;
		public aiVector3D<T> mValue;
		
		public aiVectorKey() {
		}
		
		public aiVectorKey(double time, aiVector3D<T> value) {
			mTime = time;
			mValue = value;
		}
		
		public boolean opEquals(aiVectorKey<T> o) {
			return o.mValue.opEquals(mValue);
		}
		
		public boolean opNotEquals(aiVectorKey<T> o) {
			return false;
		}
		
		public boolean opSmaller(aiVectorKey<T> o) {
			return false;
		}
		
		public boolean opBigger(aiVectorKey<T> o) {
			return false;
		}
	}
	
	public static class aiQuatKey<T extends Number> {
		public double mTime;
		public aiQuaternion<T> mValue;
		
		public aiQuatKey() {
		}
		
		public aiQuatKey(double time, aiQuaternion<T> value) {
			mTime = time;
			mValue = value;
		}
		
		public boolean opEquals(aiQuatKey<T> o) {
			return o.mValue.opEquals(mValue);
		}
		
		public boolean opNotEquals(aiQuatKey<T> o) {
			return false;
		}
		
		public boolean opSmaller(aiQuatKey<T> o) {
			return false;
		}
		
		public boolean opBigger(aiQuatKey<T> o) {
			return false;
		}
	}

}
