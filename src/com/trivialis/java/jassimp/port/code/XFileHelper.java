package com.trivialis.java.jassimp.port.code;

import java.util.ArrayList;

import com.trivialis.java.jassimp.port.include.assimp.anim.aiQuatKey;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiVectorKey;
import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.mesh;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
import com.trivialis.java.jassimp.port.include.assimp.vector2.aiVector2D;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;

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
	    aiColor4D mDiffuse;
	    ai_real mSpecularExponent;
	    aiColor3D mSpecular;
	    aiColor3D mEmissive;
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

	public static class Mesh {
		public String mName;
		public ArrayList<aiVector3D> mPositions;
		public ArrayList<Face> mPosFaces;
		public ArrayList<aiVector3D> mNormals;
		public ArrayList<Face> mNormFaces;
		public int mNumTextures;
		public ArrayList<aiVector2D>[] mTexCoords = new ArrayList[mesh.AI_MAX_NUMBER_OF_TEXTURECOORDS];
		public int mNumColorSets;
		public ArrayList<aiColor4D>[] mColors = new ArrayList[mesh.AI_MAX_NUMBER_OF_COLOR_SETS];

		public ArrayList<Integer> mFaceMaterials;
		public ArrayList<Material> mMaterials;

		public ArrayList<Bone> mBones;

		public Mesh() {
			mName = "";
		}

		public Mesh(String pName) {
			mName = pName;
		}
	}

	public static class Node {
		public String mName;
		public aiMatrix4x4 mTrafoMatrix = new aiMatrix4x4();
		public Node mParent;
		public ArrayList<Node> mChildren;
		public ArrayList<Mesh> mMeshes;

		public Node() {
			mParent = null;
		}
		public Node(Node pParent) {
			mParent = pParent;
		}
		public void destroy() {
			mChildren.clear();
			mMeshes.clear();
		}
	}

	public static class MatrixKey {
		public double mTime;
		public aiMatrix4x4 mMatrix;
	}

	public static class AnimBone {
		public String mBoneName;
		public ArrayList<aiVectorKey> mPosKeys;
		public ArrayList<aiQuatKey> mRotKeys;
		public ArrayList<aiVectorKey> mScaleKeys;
		public ArrayList<MatrixKey> mTrafoKeys;
	}

	public static class Animation {
		public String mName;
		public ArrayList<AnimBone> mAnims;
		public void destroy() {
			mAnims.clear();
		}
	}

	public static class Scene {
		public Node mRootNode;

		public ArrayList<Mesh> mGlobalMeshes;
		public ArrayList<Material> mGlobalMaterials;

		public ArrayList<Animation> mAnims;
		public int mAnimTicksPerSecond;

		public Scene() {

		}

		public void destroy(){
			mRootNode=null;
			mGlobalMeshes.clear();
			mAnims.clear();
		}
	}

}
