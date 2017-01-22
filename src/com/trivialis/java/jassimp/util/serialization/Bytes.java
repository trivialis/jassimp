package com.trivialis.java.jassimp.util.serialization;

import java.nio.ByteBuffer;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;

public class Bytes {

	public static byte[] serialize(int... iis) {
		ByteBuffer mediator = ByteBuffer.allocate(iis.length*4);
		for(int i : iis) mediator.putInt(i);
		return mediator.array();
	}

//	public static byte[] serialize(aiColor3D a) {
//		return ByteBuffer.allocate(3*4).putFloat(a.r.getValue().floatValue()).putFloat(a.g.getValue().floatValue()).putFloat(a.b.getValue().floatValue()).array();
//	}

	public static byte[] serialize(ai_real a) {
		return ByteBuffer.allocate(4).putFloat(a.getValue().floatValue()).array();
	}

	public static byte[] serialize(aiColor3D... clr)
	{
		ByteBuffer mediator = ByteBuffer.allocate(clr.length*4*3);
		for(aiColor3D a : clr) mediator.putFloat(a.r.getValue().floatValue()).putFloat(a.g.getValue().floatValue()).putFloat(a.b.getValue().floatValue());
		return mediator.array();
	}

}
