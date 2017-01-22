package com.trivialis.java.jassimp.port.include.assimp;

import java.util.ArrayList;

public abstract class IOStream {

	public abstract int FileSize();

	public abstract int Read(byte[] bs, int i, int fileSize);

}
