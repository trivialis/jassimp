package com.trivialis.java.jassimp.util;

public class string {

	public static int strcmp(IPointer<Character> s1, IPointer<Character> s2) {
		s2=s2.pointerCopy();
		s1=s1.pointerCopy();
	    while(s1.canInc() && (s1.get()==s2.get())) {
	        s1.pointerPostInc(); 
	        s2.pointerPostInc();
	    }
	    return s1.get()-s2.get();
	}
	
	public static int strncmp(IPointer<Character> s1, IPointer<Character> s2, int n) {
		s2=s2.pointerCopy();
		s1=s1.pointerCopy();
	    while(n-->0){
	        if(s1.pointerPostInc().get()!=s2.pointerPostInc().get())
	            return (s1.get() - 1) - (s2.get() - 1);
	    }
	    return 0;
	}
	
	public static void main(String[] args) {
		IPointer<Character> p1 = Pointer.valueOf(StringUtil.toCharacterArray("Hello world!".toCharArray()));
		IPointer<Character> p2 = Pointer.valueOf(StringUtil.toCharacterArray("Hello world!".toCharArray()));
		
		System.out.println(strcmp(p1, p2));
		System.out.println(strncmp(p1, p2, 5));
	}
	
}
