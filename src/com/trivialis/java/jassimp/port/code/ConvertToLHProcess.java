package com.trivialis.java.jassimp.port.code;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.trivialis.java.jassimp.port.include.assimp.anim.aiAnimation;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiNodeAnim;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiBone;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiMesh;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiNode;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;

public class ConvertToLHProcess {

	public static class MakeLeftHandedProcess {

		public void Execute(aiScene pScene)
		{
		    // Check for an existent root node to proceed
		    assert(pScene.mRootNode != null);
		    Logger.getLogger("default").log(Level.FINEST, "MakeLeftHandedProcess begin");

		    // recursively convert all the nodes
		    ProcessNode( pScene.mRootNode, new matrix4x4.aiMatrix4x4());

		    // process the meshes accordingly
		    for( int a = 0; a < pScene.mNumMeshes; ++a)
		        ProcessMesh( pScene.mMeshes[a]);

		    // process the materials accordingly
		    for( int a = 0; a < pScene.mNumMaterials; ++a)
		        ProcessMaterial( pScene.mMaterials[a]);

		    // transform all animation channels as well
		    for( int a = 0; a < pScene.mNumAnimations; a++)
		    {
		        aiAnimation anim = pScene.mAnimations[a];
		        for( int b = 0; b < anim.mNumChannels; b++)
		        {
		            aiNodeAnim nodeAnim = anim.mChannels[b];
		            ProcessAnimation( nodeAnim);
		        }
		    }
		    Logger.getLogger("default").log(Level.FINEST, "MakeLeftHandedProcess finished");
		}

		public void ProcessNode(aiNode pNode, aiMatrix4x4 pParentGlobalRotation)
		{
		    // mirror all base vectors at the local Z axis
		    pNode.mTransformation.c1 = pNode.mTransformation.c1.opNegate();
		    pNode.mTransformation.c2 = pNode.mTransformation.c2.opNegate();
		    pNode.mTransformation.c3 = pNode.mTransformation.c3.opNegate();
		    pNode.mTransformation.c4 = pNode.mTransformation.c4.opNegate();

		    // now invert the Z axis again to keep the matrix determinant positive.
		    // The local meshes will be inverted accordingly so that the result should look just fine again.
		    pNode.mTransformation.a3 = pNode.mTransformation.a3.opNegate();
		    pNode.mTransformation.b3 = pNode.mTransformation.b3.opNegate();
		    pNode.mTransformation.c3 = pNode.mTransformation.c3.opNegate();
		    pNode.mTransformation.d3 = pNode.mTransformation.d3.opNegate(); // useless, but anyways...

		    // continue for all children
		    for( int a = 0; a < pNode.mNumChildren; ++a ) {
		        ProcessNode( pNode.mChildren[ a ], (aiMatrix4x4) pParentGlobalRotation.opMultiply(pNode.mTransformation));
		    }
		}

		public void ProcessMesh(aiMesh pMesh)
		{
		    // mirror positions, normals and stuff along the Z axis
		    for( int a = 0; a < pMesh.mNumVertices; ++a)
		    {
		        pMesh.mVertices[a].z = pMesh.mVertices[a].z.opMultiply(new ai_real(-1.0f));
		        if( pMesh.HasNormals())
		            pMesh.mNormals[a].z = pMesh.mNormals[a].z.opMultiply(new ai_real(-1.0f));
		        if( pMesh.HasTangentsAndBitangents())
		        {
		            pMesh.mTangents[a].z = pMesh.mTangents[a].z.opMultiply(new ai_real(-1.0f));
		            pMesh.mBitangents[a].z = pMesh.mBitangents[a].z.opMultiply(new ai_real(-1.0f));
		        }
		    }

		    // mirror offset matrices of all bones
		    for( int a = 0; a < pMesh.mNumBones; ++a)
		    {
		        aiBone bone = pMesh.mBones[a];
		        bone.mOffsetMatrix.a3 = bone.mOffsetMatrix.a3.opNegate();
		        bone.mOffsetMatrix.b3 = bone.mOffsetMatrix.b3.opNegate();
		        bone.mOffsetMatrix.d3 = bone.mOffsetMatrix.d3.opNegate();
		        bone.mOffsetMatrix.c1 = bone.mOffsetMatrix.c1.opNegate();
		        bone.mOffsetMatrix.c2 = bone.mOffsetMatrix.c2.opNegate();
		        bone.mOffsetMatrix.c4 = bone.mOffsetMatrix.c4.opNegate();
		    }

		    // mirror bitangents as well as they're derived from the texture coords
		    if( pMesh.HasTangentsAndBitangents())
		    {
		        for( int a = 0; a < pMesh.mNumVertices; a++)
		            pMesh.mBitangents[a]=(aiVector3D) pMesh.mBitangents[a].opMultiply(new ai_real(-1.0f));
		    }
		}




	}

	public static class FlipWindingOrderProcess {

		public void Execute(aiScene pScene)
		{
			// TODO Auto-generated method stub

		}

	}

}
