package com.trivialis.java.jassimp.port.include.assimp;

import java.util.ArrayList;

import com.trivialis.java.jassimp.util.IPointer;

public abstract class IOSystem {

	private ArrayList<String> m_pathStack;


	public IOStream Open(IPointer<StringBuilder> pFile) {
		return Open(pFile, "rb");
	}
	public abstract IOStream Open(IPointer<StringBuilder> pFile, String pMode);
	public abstract char getOsSeparator();
	public abstract boolean Exists(IPointer<StringBuilder> in);

//	public abstract void Close(IOStream pFile);

}
