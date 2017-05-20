package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiVertexWeight;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;

public class mesh {

	public static class aiVertexWeight {

		public aiVertexWeight(int d, ai_real w)
		{
			// TODO Auto-generated constructor stub
		}

	}

	public static class aiBone {

		public aiString mName;
		public aiMatrix4x4 mOffsetMatrix;
		public int mNumWeights;
		public aiVertexWeight[] mWeights;

	}

	public static class aiFace {

		public int mNumIndices;
		public int[] mIndices;

	}

	public static class aiMesh {

		public int mMaterialIndex;
		public int mNumVertices;
		public int mNumFaces;
		public aiFace[] mFaces = new aiFace[0];
		public aiVector3D[] mVertices;
		public aiString mName = new aiString();
		public aiVector3D[] mNormals = new aiVector3D[0];
		public aiVector3D[][] mTextureCoords = new aiVector3D[AI_MAX_NUMBER_OF_TEXTURECOORDS][];
		public aiColor4D[][] mColors = new aiColor4D[AI_MAX_NUMBER_OF_COLOR_SETS][];
		public int mNumBones;
		public aiBone[] mBones;
		public aiVector3D[] mTangents;
		public aiVector3D[] mBitangents;
		
		public boolean HasNormals()
		{
			return mNormals!=null;
		}
		public boolean HasTextureCoords(int pIndex)
		{
			return pIndex >= AI_MAX_NUMBER_OF_TEXTURECOORDS ? false : mTextureCoords[pIndex] != null;
		}
		public boolean HasVertexColors(int pIndex)
		{
			return pIndex >= AI_MAX_NUMBER_OF_COLOR_SETS ? false : mColors[pIndex] != null;
		}
		public boolean HasTangentsAndBitangents()
		{
			return mTangents!=null;
		}

	}
	
	public static int AI_MAX_NUMBER_OF_TEXTURECOORDS = 0x8;
	public static int AI_MAX_NUMBER_OF_COLOR_SETS = 0x8;

}
