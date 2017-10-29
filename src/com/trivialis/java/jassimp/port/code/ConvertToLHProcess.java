package com.trivialis.java.jassimp.port.code;

import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.trivialis.java.jassimp.port.include.assimp.anim.aiAnimation;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiNodeAnim;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterialProperty;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiBone;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiFace;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiMesh;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiNode;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.util.string;
import com.trivialis.java.jassimp.util.serialization.Bytes;

public class ConvertToLHProcess {

	public static class MakeLeftHandedProcess {

		public void Execute(aiScene pScene)
		{
		    // Check for an existent root node to proceed
		    assert(pScene.mRootNode != null);
		    Logger.getLogger("default").log(Level.FINEST, "MakeLeftHandedProcess begin");

		    // recursively convert all the nodes
		    ProcessNode( pScene.mRootNode, new aiMatrix4x4());

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
		    pNode.mTransformation.c1 = pNode.mTransformation.c1* -1;
		    pNode.mTransformation.c2 = pNode.mTransformation.c2* -1;
		    pNode.mTransformation.c3 = pNode.mTransformation.c3* -1;
		    pNode.mTransformation.c4 = pNode.mTransformation.c4* -1;

		    // now invert the Z axis again to keep the matrix determinant positive.
		    // The local meshes will be inverted accordingly so that the result should look just fine again.
		    pNode.mTransformation.a3 = pNode.mTransformation.a3* -1;
		    pNode.mTransformation.b3 = pNode.mTransformation.b3* -1;
		    pNode.mTransformation.c3 = pNode.mTransformation.c3* -1;
		    pNode.mTransformation.d3 = pNode.mTransformation.d3* -1; // useless, but anyways...

		    // continue for all children
                    aiMatrix4x4 t = (aiMatrix4x4) pParentGlobalRotation.opMultiply(pNode.mTransformation);
		    for( int a = 0; a < pNode.mNumChildren; ++a ) {
		        ProcessNode( pNode.mChildren[ a ], t);
		    }
		}

		public void ProcessMesh(aiMesh pMesh)
		{
		    // mirror positions, normals and stuff along the Z axis
		    for( int a = 0; a < pMesh.mNumVertices; ++a)
		    {
		        pMesh.mVertices[a].z = pMesh.mVertices[a].z * -1.0F;
		        if( pMesh.HasNormals())
		            pMesh.mNormals[a].z = pMesh.mNormals[a].z * -1.0F;
		        if( pMesh.HasTangentsAndBitangents())
		        {
		            pMesh.mTangents[a].z = pMesh.mTangents[a].z * -1.0F;
		            pMesh.mBitangents[a].z = pMesh.mBitangents[a].z * -1.0F;
		        }
		    }

		    // mirror offset matrices of all bones
		    for( int a = 0; a < pMesh.mNumBones; ++a)
		    {
		        aiBone bone = pMesh.mBones[a];
		        bone.mOffsetMatrix.a3 = bone.mOffsetMatrix.a3* -1;
		        bone.mOffsetMatrix.b3 = bone.mOffsetMatrix.b3* -1;
		        bone.mOffsetMatrix.d3 = bone.mOffsetMatrix.d3* -1;
		        bone.mOffsetMatrix.c1 = bone.mOffsetMatrix.c1* -1;
		        bone.mOffsetMatrix.c2 = bone.mOffsetMatrix.c2* -1;
		        bone.mOffsetMatrix.c4 = bone.mOffsetMatrix.c4* -1;
		    }

		    // mirror bitangents as well as they're derived from the texture coords
		    if( pMesh.HasTangentsAndBitangents())
		    {
		        for( int a = 0; a < pMesh.mNumVertices; a++){//System.out.println(pMesh.mBitangents[a].x);System.out.println(pMesh.mBitangents[a] * -1.0F);
		            pMesh.mBitangents[a]=(aiVector3D) pMesh.mBitangents[a].opMultiply(-1.0F);}
		    }
		}

		public void ProcessMaterial( aiMaterial _mat)
		{
		    aiMaterial mat = (aiMaterial)_mat;
		    for (int a = 0; a < mat.mNumProperties;++a)   {
		        aiMaterialProperty prop = mat.mProperties.get(a);

		        // Mapping axis for UV mappings?
		        if (string.strcmp( prop.mKey.data, "$tex.mapaxis".getBytes(Charset.forName("UTF-8")))==0)    {
		            assert( prop.mDataLength >= Float.BYTES*3); if(prop.mDataLength<Float.BYTES*3) throw new RuntimeException("Invalid datalength: " + prop.mDataLength); /* something is wrong with the validation if we end up here */
		            aiVector3D pff = Bytes.deserializeTo_aiVector3D(prop.mData);

		            pff.z = pff.z * -1.0F;
		        }
		    }
		}

		public void ProcessAnimation( aiNodeAnim pAnim)
		{
		    // position keys
		    for( int a = 0; a < pAnim.mNumPositionKeys; a++)
		        pAnim.mPositionKeys[a].mValue.z = pAnim.mPositionKeys[a].mValue.z * -1.0F;

		    // rotation keys
		    for( int a = 0; a < pAnim.mNumRotationKeys; a++)
		    {
		        /* That's the safe version, but the float errors add up. So we try the short version instead
		        aiMatrix3x3 rotmat = pAnim.mRotationKeys[a].mValue.GetMatrix();
		        rotmat.a3 = -rotmat.a3; rotmat.b3 = -rotmat.b3;
		        rotmat.c1 = -rotmat.c1; rotmat.c2 = -rotmat.c2;
		        aiQuaternion rotquat( rotmat);
		        pAnim.mRotationKeys[a].mValue = rotquat;
		        */
		        pAnim.mRotationKeys[a].mValue.x =pAnim.mRotationKeys[a].mValue.x * -1.0F;
		        pAnim.mRotationKeys[a].mValue.y =pAnim.mRotationKeys[a].mValue.y * -1.0F;
		    }
		}

	}

	   public static class FlipWindingOrderProcess {

               public void Execute(aiScene pScene) {
//		 DefaultLogger::get()->debug("FlipWindingOrderProcess begin");
                   for (int i = 0; i < pScene.mNumMeshes; ++i) {
                       ProcessMesh(pScene.mMeshes[i]);
                   }
//                    DefaultLogger::get()->debug("FlipWindingOrderProcess finished");
               }

               public void ProcessMesh(aiMesh pMesh) {
                   // invert the order of all faces in this mesh
                   for (int a = 0; a < pMesh.mNumFaces; a++) {
                       aiFace face = pMesh.mFaces[a];
                       for (int b = 0; b < face.mNumIndices / 2; b++) {
                           int aa = face.mIndices[b];
                           int bb = face.mIndices[face.mNumIndices - 1 - b];
                           face.mIndices[b] = bb;
                           face.mIndices[face.mNumIndices - 1 - b] = aa;
                       }
                   }
               }

	}

}
