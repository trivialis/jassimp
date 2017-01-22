package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.code.MaterialSystem;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterialProperty;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiAnimation;
import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiVertexWeight;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiBone;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiFace;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiMesh;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiNode;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
import com.trivialis.java.jassimp.port.include.assimp.types.aiReturn;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.util.IPointer;

public class scene {

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
		public aiFace[] mFaces;
		public aiVector3D[] mVertices;
		public aiString mName;
		public aiVector3D[] mNormals;
		public aiVector3D[][] mTextureCoords;
		public aiColor4D[][] mColors;
		public int mNumBones;
		public aiBone[] mBones;
		public boolean HasNormals()
		{
			// TODO Auto-generated method stub
			return false;
		}
		public boolean HasTextureCoords(int e)
		{
			// TODO Auto-generated method stub
			return false;
		}
		public boolean HasVertexColors(int e)
		{
			// TODO Auto-generated method stub
			return false;
		}

	}

	public static class aiScene {

		public aiNode mRootNode;
		public int mNumMaterials;
		public aiMaterial[] mMaterials;
		public aiMesh[] mMeshes;
		public int mNumMeshes;
		public int mNumAnimations;
		public aiAnimation[] mAnimations;

	}

	public static class aiNode {

		public aiString mName;
		public aiNode mParent;
		public aiMatrix4x4 mTransformation;
		public int mNumChildren;
		public aiNode[] mChildren;
		public int mNumMeshes;
		public int[] mMeshes;

	}



}
