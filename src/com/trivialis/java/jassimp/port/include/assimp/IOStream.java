package com.trivialis.java.jassimp.port.include.assimp;

public abstract class IOStream {

	public abstract int FileSize();

	public abstract int Read(byte[] bs, int i, int fileSize);

}
