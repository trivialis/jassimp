package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.StringUtil;
import com.trivialis.java.jassimp.util.ctype;

public class StringComparison {

	public static int ASSIMP_strincmp(IPointer<Character> s1, IPointer<Character> s2, int n) {
		assert(s1!=null && s2!=null);
		if(n==0) return 0;


	    char c1, c2;
	    int p = 0;
	    do
	    {
	        if (p++ >= n)return 0;
	        c1 = (char) ctype.tolower(s1.postInc().get());
	        c2 = (char) ctype.tolower(s2.postInc().get());
	    }
	    while ( c1!=0 && s1.canInc() && (c1 == c2) );

	    return c1 - c2;
	}

	public static int ASSIMP_strincmp(IPointer<Character> c, String string, int n)
	{
		return ASSIMP_strincmp(c, Pointer.valueOf(StringUtil.toCharacterArray(string.toCharArray())), n);
	}

}
