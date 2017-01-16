package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.port.code.XFileHelper.Scene;
import com.trivialis.java.jassimp.util.IPointer;

public class XFileParser {
	
	public static final int MSZIP_MAGIC = 0x4B43;
	public static final int MSZIP_BLOCK = 32786;
	private Object mMajorVersion;
	private boolean mIsBinaryFormat;
	private int mBinaryNumCount;
	private int mMinorVersion;
	private IPointer<Character> P;
	private IPointer<Character> End;
	private int mLineNumber;
	private Scene mScene;

	public XFileParser(IPointer<Character> pBuffer)
	{
	    mMajorVersion = mMinorVersion = 0;
	    mIsBinaryFormat = false;
	    mBinaryNumCount = 0;
	    P = End = null;
	    mLineNumber = 0;
	    mScene = null;
	    
	}


}
