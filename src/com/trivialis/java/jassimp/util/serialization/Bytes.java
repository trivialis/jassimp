package com.trivialis.java.jassimp.util.serialization;

import java.nio.ByteBuffer;

import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.material.aiUVTransform;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
import com.trivialis.java.jassimp.port.include.assimp.vector2;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;

public class Bytes {

	public static byte[] serialize(int... iis) {
		ByteBuffer mediator = ByteBuffer.allocate(iis.length*4);
		for(int i : iis) mediator.putInt(i);
		return mediator.array();
	}

//	public static byte[] serialize(aiColor3D a) {
//		return ByteBuffer.allocate(3*4).putFloat(a.r.getValue().floatValue()).putFloat(a.g.getValue().floatValue()).putFloat(a.b.getValue().floatValue()).array();
//	}

//	public static byte[] serialize(ai_real a) {
//		return ByteBuffer.allocate(4).putFloat(a.getValue().floatValue()).array();
//	}

	public static byte[] serialize(aiColor3D... clr)
	{
		ByteBuffer mediator = ByteBuffer.allocate(clr.length * 4 * 3);
		for (aiColor3D a : clr)
			mediator.putFloat(a.r).putFloat(a.g).putFloat(a.b);
		return mediator.array();
	}

	public static byte[] serialize(aiColor4D... clr)
	{
		ByteBuffer mediator = ByteBuffer.allocate(clr.length*4*4);
		for(aiColor4D a : clr) mediator.putFloat(a.r).putFloat(a.g).putFloat(a.b).putFloat(a.a);
		return mediator.array();
	}

	public static byte[] serialize(double... doubles)
	{
		ByteBuffer mediator = ByteBuffer.allocate(doubles.length*8);
		for(double d : doubles) mediator.putDouble(d);
		return mediator.array();
	}

	public static byte[] serialize(float... floats)
	{
		ByteBuffer mediator = ByteBuffer.allocate(floats.length*4);
		for(float f : floats) mediator.putFloat(f);
		return mediator.array();
	}
	
	public static byte[] serialize(aiVector3D... clr)
	{
		ByteBuffer mediator = ByteBuffer.allocate(clr.length * 4 * 3);
		for (aiVector3D a : clr)
			mediator.putFloat(a.x).putFloat(a.y).putFloat(a.z);
		return mediator.array();
	}

	public static aiVector3D deserializeTo_aiVector3D(byte[] mData)
	{
		ByteBuffer mediator = ByteBuffer.wrap(mData);
		if(mData.length==12) {
			return new aiVector3D(mediator.getFloat(), mediator.getFloat(), mediator.getFloat());
		} else if(mData.length==24) {
			return new aiVector3D((float) mediator.getDouble(), (float) mediator.getDouble(), (float) mediator.getDouble());
		}
		throw new RuntimeException("Invalid length: " + mData.length);
	}

	public static aiUVTransform deserializeaiUVTransform(byte[] mData)
	{
		ByteBuffer mediator = ByteBuffer.wrap(mData);
		if(mData.length==Float.BYTES*5) {
			return new aiUVTransform(new vector2.aiVector2D(mediator.getFloat(), mediator.getFloat()), new vector2.aiVector2D(mediator.getFloat(), mediator.getFloat()), mediator.getFloat());
		}
		throw new RuntimeException("Invalid length: " + mData.length);
	}

}
