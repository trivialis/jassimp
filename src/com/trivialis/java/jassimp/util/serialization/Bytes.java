package com.trivialis.java.jassimp.util.serialization;

import java.nio.ByteBuffer;

import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
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
			mediator.putFloat(a.r.getValue().floatValue()).putFloat(a.g.getValue().floatValue()).putFloat(a.b.getValue().floatValue());
		return mediator.array();
	}

	public static byte[] serialize(aiColor4D... clr)
	{
		ByteBuffer mediator = ByteBuffer.allocate(clr.length*4*3);
		for(aiColor4D a : clr) mediator.putFloat(a.r.getValue().floatValue()).putFloat(a.g.getValue().floatValue()).putFloat(a.b.getValue().floatValue()).putFloat(a.a.getValue().floatValue());
		return mediator.array();
	}

	public static byte[] serialize(ai_real... ai_reals)
	{
		int totalsize = 0;
		for(ai_real a : ai_reals) {
			if(a.getValue() instanceof Double) {
				totalsize+=8;
			} else if(a.getValue() instanceof Float) {
				totalsize+=4;
			}  else if(a.getValue() instanceof Integer) {
				totalsize+=4;
			}  else if(a.getValue() instanceof Long) {
				totalsize+=8;
			}
		}
		ByteBuffer mediator = ByteBuffer.allocate(totalsize);
		for(ai_real a : ai_reals) {
			if(a.getValue() instanceof Double) {
				mediator.putDouble(a.getValue().doubleValue());
			} else if(a.getValue() instanceof Float) {
				mediator.putFloat(a.getValue().floatValue());
			} else if(a.getValue() instanceof Integer) {
				mediator.putInt(a.getValue().intValue());
			} else if(a.getValue() instanceof Long) {
				mediator.putLong(a.getValue().longValue());
			}
		}
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

	public static aiVector3D deserializeTo_aiVector3D(byte[] mData)
	{
		ByteBuffer mediator = ByteBuffer.wrap(mData);
		if(mData.length==12) {
			return new aiVector3D(new ai_real(mediator.getFloat()), new ai_real(mediator.getFloat()), new ai_real(mediator.getFloat()));
		} else if(mData.length==24) {
			return new aiVector3D(new ai_real(mediator.getDouble()), new ai_real(mediator.getDouble()), new ai_real(mediator.getDouble()));
		}
		throw new RuntimeException();
	}

}
