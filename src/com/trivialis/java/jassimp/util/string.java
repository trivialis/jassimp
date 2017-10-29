package com.trivialis.java.jassimp.util;

import java.nio.ByteBuffer;
import java.util.Objects;

import com.trivialis.java.jassimp.port.include.assimp.types.aiString;

public class string {

	public static int strcmp(char[] data, char[] data2)
	{
		int s1=0;
		int s2=0;
		while(s1< data.length && (data[s1]==data2[s2])){
			s1++;
			s2++;
		}
		return data[s1]-data2[s2];
	}

	public static int strcmp(IPointer<Character> s1, IPointer<Character> s2) {
		s2=s2.pointerCopy();
		s1=s1.pointerCopy();
	    while(s1.canInc() && (Objects.equals(s1.get(), s2.get()))) {
	        s1.postInc();
	        s2.postInc();
	    }
	    return s1.get()-s2.get();
	}

	public static int strncmp(String s1, String s2, int n)
	{
		int i1=0;
		int i2=0;
		while(n-->0) {
			if(s1.charAt(i1)!=s2.charAt(i2))
				return (s1.charAt(i1)-(s2.charAt(i2)));
			i1++;i2++;
		}
		return 0;
	}

	public static int strncmp(IPointer<Character> s1, IPointer<Character> s2, int n) {
		s2=s2.pointerCopy();
		s1=s1.pointerCopy();
	    while(n-->0){
	        if(!Objects.equals(s1.get(), s2.get()))
	            return (s1.get()) - (s2.get());
	        s1.postInc();s2.postInc();
	    }
	    return 0;
	}
	
	public static int strncmp(IPointer<Character> s1, String s2, int n) {
		IPointer<Character> ps2=Pointer.valueOf(StringUtil.toCharacterArray(s2.toCharArray()));
		s1=s1.pointerCopy();
	    while(n-->0){
	        if(!Objects.equals(s1.get(), ps2.get()))
	            return (s1.get()) - (ps2.get());
	        s1.postInc();ps2.postInc();
	    }
	    return 0;
	}

	public static void main(String[] args) {
		String s1 = "Hello_world!";
		String s2 = "Hello world!";
		
		IPointer<Character> p1 = Pointer.valueOf(StringUtil.toCharacterArray(s1.toCharArray()));
		IPointer<Character> p2 = Pointer.valueOf(StringUtil.toCharacterArray(s2.toCharArray()));

		System.out.println(strcmp(s1.toCharArray(),s2.toCharArray()));
		System.out.println(strncmp(s1, s2, 10));
		System.out.println(strncmp(p1, s2, 10));
		System.out.println(strcmp(p1, p2));
		System.out.println(strncmp(p1, p2, 10));
	}

	public static <T extends Object> void memcpy(T[] data, T[] data2, int length)
	{
		System.arraycopy(data2, 0, data, 0, length);
	}

	public static <T extends Object> void memcpy(T[] data, int length, T[] app, int len)
	{
		System.arraycopy(app, 0, data, length, len);
	}

	public static void memcpy(char[] data, char[] data2, int length)
	{
		System.arraycopy(data2, 0, data, 0, length);
	}

	public static int strlen(byte[] sz)
	{
		return sz.length;
	}

	public static int strlen(char[] sz)
	{
		return sz.length;
	}

	//http://stackoverflow.com/a/18677609/6647217
	public static int memcmp(char[] data, char[] data2, int length)
	{
	    for(int i = 0; i < length; i++){
	        if(data[i] != data2[i]){
	            if((data[i] >= 0 && data2[i] >= 0)||(data[i] < 0 && data2[i] < 0))
	                return data[i] - data2[i];
	            if(data[i] < 0 && data2[i] >= 0)
	                return 1;
	            if(data2[i] < 0 && data[i] >=0)
	                return -1;
	        }
	    }
	    return 0;
	}

	public static int memcmp(byte[] data, byte[] data2, int length)
	{
	    for(int i = 0; i < length; i++){
	        if(data[i] != data2[i]){
	            if((data[i] >= 0 && data2[i] >= 0)||(data[i] < 0 && data2[i] < 0))
	                return data[i] - data2[i];
	            if(data[i] < 0 && data2[i] >= 0)
	                return 1;
	            if(data2[i] < 0 && data[i] >=0)
	                return -1;
	        }
	    }
	    return 0;
	}


	public static void memcpy(byte[] data, int length, byte[] app, int len)
	{
		System.arraycopy(app, 0, data, length, len);
	}

	public static int strcmp(aiString name, String mName)
	{
		return strcmp(Pointer.valueOf(StringUtil.toCharacterArray(name.data)), Pointer.valueOf(StringUtil.toCharacterArray(mName.toCharArray())));
	}

	public static void strcpy(char[] data, char[] data2)
	{
		System.arraycopy(data, 0, data2, 0, data2.length);
	}

	public static void memcpy(byte[] data, int[] data2, int pSizeInBytes)
	{
		ByteBuffer mediator = ByteBuffer.wrap(data);
		for(int i = 0; i<data.length&&i<data2.length; i++)
		{
			mediator.putInt(data2[i]);
		}
	}

	public static void memcpy(byte[] data, byte[] data2, int length)
	{
		for(int i = 0; i<length&i<length; i++)
		{
			data[i]=
					data2[i];
		}

	}

	public static int strcmp(byte[] data, byte[] data2)
	{
		int s1=0;
		int s2=0;
		while(s1< data.length && (data[s1]==data2[s2])){
			s1++;
			s2++;
		}
		return data[s1]-data2[s2];
	}

	public static void strcpy(byte[] data, byte[] data2)
	{
		System.arraycopy(data, 0, data2, 0, data2.length);
	}


















}
