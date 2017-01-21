package com.trivialis.java.jassimp.port.include.assimp;

import java.util.Arrays;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.util.std;
import com.trivialis.java.jassimp.util.string;

public class types {

	public static class aiColor3D
	{
	    public ai_real r;
	    public ai_real g;
		public ai_real b;
		public aiColor3D () {

	    }
	    public aiColor3D(ai_real _r, ai_real _g, ai_real _b) {
	    	r=_r;
	    	g=_g;
	    	b=_b;
	    }
	    public aiColor3D(aiColor3D o) {
	    	r=o.r;
	    	g=o.g;
	    	b=o.b;
	    }
	    public boolean opEquals(aiColor3D o) {
	    	return r.opEquals(o.r) && g.opEquals(o.g) && b.opEquals(o.b);
	    }

	    public boolean opNotEquals(aiColor3D o) {
	    	return !r.opEquals(o.r) || !g.opEquals(o.g) || !b.opEquals(o.b);
	    }


	    public boolean opSmaller(aiColor3D o) {
	    	return r.opSmaller(o.r) || ( r.opEquals(o.r) && (g.opSmaller(o.g) || (g.opEquals(o.g) && b.opSmaller(o.b))));
	    }

	    public aiColor3D add(aiColor3D c) {
	    	return new aiColor3D(r.opAdd(c.r),g.opAdd(c.g),b.opAdd(c.b));
	    }

	    public aiColor3D subtract(aiColor3D c) {
	    	return new aiColor3D(r.opSubtract(c.r),g.opSubtract(c.g),b.opSubtract(c.b));
	    }

	    public aiColor3D multiply(aiColor3D c) {
	    	return new aiColor3D(r.opMultiply(c.r),g.opMultiply(c.g),b.opMultiply(c.b));
	    }

	    public aiColor3D multiply(ai_real f) {
	    	return new aiColor3D(r.opMultiply(f),g.opMultiply(f),b.opMultiply(f));
	    }

	    public ai_real[] array() {
	    	return (ai_real[]) Arrays.asList(r,g,b).toArray();
	    }

	    public ai_real get(int i) {
	    	return array()[i];
	    }
	    public boolean IsBlack() {
	    	ai_real epsilon = r.forValue(Math.pow(10, -3));
	    	return r.forValue(std.abs(r.getValue().doubleValue())).opSmaller(epsilon) && g.forValue(std.abs(g.getValue().doubleValue())).opSmaller(epsilon)&& b.forValue(std.abs(b.getValue().doubleValue())).opSmaller(epsilon);
	    }

	}

	public static final int MAXLEN=1024;

	public static class aiString {



		public int length;
		public char[] data;

		public aiString() {
			length = 0;
			data[0]='\0';
		}

		public aiString(aiString rOther) {
			length=rOther.length;
			length=length>=MAXLEN?MAXLEN-1:length;
			string.memcpy(data, rOther.data, length);
			data[length]='\0';
		}

		public aiString(String pString) {
			length=pString.length();
			length=length>=MAXLEN?MAXLEN-1:length;
			string.memcpy(data, pString.toCharArray(), length);
			data[length]='\0';
		}

		public void Set(String pString) {
			if(pString.length() > MAXLEN-1) {
				return;
			}
			length=pString.length();
			string.memcpy(data, pString.toCharArray(), length);
		}

		public void Set(char[] sz) {
			int len = string.strlen(sz);
			if(len>MAXLEN-1) {
				return;
			}
			length=len;
			string.memcpy(data, sz, len);
			data[len]=0;
		}

		public aiString opIs(char[] sz) {
			Set(sz);
			return this;
		}

		public aiString opIs(String pString) {
			Set(pString);
			return this;
		}

		public boolean opEquals(aiString other) {
			return (length==other.length && 0 == string.memcmp(data, other.data, length));
		}

		public boolean opNotEquals(aiString other) {
			return (length!=other.length || 0 != string.memcmp(data, other.data, length));
		}

		public void Append(char[] app) {
			int len = string.strlen(app);
			if(len==0) {
				return;
			}
			if(length + len >= MAXLEN) {
				return;
			}

			string.memcpy(data, length, app, len);
			length+=len;
		}

		public void Clear() {
			length = 0;
			data[0] = '\0';
		}

		public char[] C_Str() {
			return data;
		}
	}

	public enum aiReturn {
		aiReturn_SUCCESS(0x0),
		aiReturn_FAILURE(-0x1),
		aiReturn_OUTOFMEMORY(-0x3);

		public int value;

		private aiReturn(int val) {
			value = val;
		}

	}

	public enum aiOrigin {
		aiOrigin_SET(0x0),
		aiOrigin_CUR(0x1),
		aiOrigin_END(0x2);

		public int value;

		private aiOrigin(int val) {
			value=val;
		}
	}

	public enum aiDefaultLogStream {
		aiDefaultLogStream_FILE(0x1),
		aiDefaultLogStream_STDOUT(0x2),
		aiDefaultLogStream_STDERR(0x4),
		aiDefaultLogStream_DEBUGGER(0x8);

		public int value;

		private aiDefaultLogStream(int val) {
			value=val;
		}
	}

	public static class aiMemoryInfo {
		public int textures;
		public int materials;
		public int meshes;
		public int nodes;
		public int animations;
		public int cameras;
		public int lights;
		public int total;

		public aiMemoryInfo() {
			textures=0;
			materials=0;
			meshes=0;
			nodes=0;
			animations=0;
			cameras=0;
			lights=0;
			total=0;
		}
	}




}
