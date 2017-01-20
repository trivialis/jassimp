package com.trivialis.java.jassimp.util;

public class string {

	public static int strcmp(IPointer<Character> s1, IPointer<Character> s2) {
		s2=s2.pointerCopy();
		s1=s1.pointerCopy();
	    while(s1.canInc() && (s1.get()==s2.get())) {
	        s1.postInc();
	        s2.postInc();
	    }
	    return s1.get()-s2.get();
	}

	public static int strncmp(IPointer<Character> s1, IPointer<Character> s2, int n) {
		s2=s2.pointerCopy();
		s1=s1.pointerCopy();
	    while(n-->0){
	        if(s1.postInc().get()!=s2.postInc().get())
	            return (s1.get() - 1) - (s2.get() - 1);
	    }
	    return 0;
	}

	public static int strncmp(IPointer<Character> s1, String s2, int n) {
		IPointer<Character> ps2=Pointer.valueOf(StringUtil.toCharacterArray(s2.toCharArray()));
		s1=s1.pointerCopy();
	    while(n-->0){
	        if(s1.postInc().get()!=ps2.postInc().get())
	            return (s1.get() - 1) - (ps2.get() - 1);
	    }
	    return 0;
	}

	public static void main(String[] args) {
		IPointer<Character> p1 = Pointer.valueOf(StringUtil.toCharacterArray("Hello world!".toCharArray()));
		IPointer<Character> p2 = Pointer.valueOf(StringUtil.toCharacterArray("Hello world!".toCharArray()));

		System.out.println(strcmp(p1, p2));
		System.out.println(strncmp(p1, p2, 5));
	}

	public static void memcpy(char[] data, char[] data2, int length)
	{
		System.arraycopy(data2, 0, data, 0, length);
	}

	public static void memcpy(char[] data, int length, char[] app, int len)
	{
		System.arraycopy(app, 0, data, length, len);
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









}
