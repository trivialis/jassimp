package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.port.include.assimp.IOStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DefaultIOStream extends IOStream {

	private FileInputStream bis;
	private File mFile;
	private String mFilename;
	private int mCachedSize;

	public DefaultIOStream() {
		mFile=null;
		mFilename="";
		mCachedSize=Integer.MAX_VALUE;
	}

	public DefaultIOStream(File pFile, String strFilename) {
		mFile=pFile;
		mFilename=strFilename;
		mCachedSize=Integer.MAX_VALUE;
		try
		{
			bis=new FileInputStream(mFile);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void destroy() {
		if(bis!=null) {
			try
			{
				bis.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			bis=null;
			mFile=null;
		}
	}


	@Override
	public int Read(byte[] bs, int i, int fileSize)
	{
		try
		{
			return bis.read(bs, 0, fileSize);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
	}


	@Override
	public int FileSize()
	{
		if(mFile==null||mFilename.isEmpty()) {
			return 0;
		}

		if(Integer.MAX_VALUE==mCachedSize) {
			mCachedSize=(int) mFile.length();
		}

		return mCachedSize;
	}

}
