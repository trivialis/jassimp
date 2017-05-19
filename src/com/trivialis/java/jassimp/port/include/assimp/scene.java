package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.anim.aiAnimation;
import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiMesh;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiVertexWeight;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;

public class scene {
	


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

		public aiString mName = new aiString();
		public aiNode mParent;
		public aiMatrix4x4 mTransformation = new aiMatrix4x4();
		public int mNumChildren;
		public aiNode[] mChildren;
		public int mNumMeshes;
		public int[] mMeshes;

	}



}
