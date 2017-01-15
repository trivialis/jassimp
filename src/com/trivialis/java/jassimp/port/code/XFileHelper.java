package com.trivialis.java.jassimp.port.code;

import java.util.ArrayList;

import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;

public class XFileHelper {

	public static class Face {
		public ArrayList<Integer> mIndices = new ArrayList<Integer>();
	}
	
	public static class TexEntry {
		public String mName;
		public boolean mIsNormalMap;
		
		public TexEntry() {
			mIsNormalMap = false;
		}
		
		public TexEntry(String pName) {
			this(pName, false);
		}
		
		public TexEntry(String pName, boolean pIsNormalMap) {
			mName = pName;
			mIsNormalMap = pIsNormalMap;
		}
	}
	
	public static class Material {
	    String mName;
	    boolean mIsReference;
	    aiColor4D<ai_real> mDiffuse;
	    ai_real mSpecularExponent;
	    aiColor3D<ai_real> mSpecular;
	    aiColor3D<ai_real> mEmissive;
	    ArrayList<TexEntry> mTextures;

	    int sceneIndex; 
	    
	    public Material() {
	    	mIsReference = false;
	    	mSpecularExponent = null;
	    	sceneIndex = Integer.MAX_VALUE;
	    }
	}
	
	public static class BoneWeight {
		public int mVertex;
		public ai_real mWeight;
	}
	
	public static class Bone {
		public String mName;
		public ArrayList<BoneWeight> mWeights;
		public aiMatrix4x4 mOffsetMatrix;
	}
	
	
}
