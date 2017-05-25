package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.port.include.assimp.anim.aiQuatKey;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiVectorKey;
import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.mesh;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
import com.trivialis.java.jassimp.port.include.assimp.vector2.aiVector2D;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.util.ArrayUtil;
import java.util.ArrayList;

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
	    ArrayList<TexEntry> mTextures = new ArrayList<>();

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
		public ArrayList<BoneWeight> mWeights = new ArrayList<BoneWeight>();
		public aiMatrix4x4 mOffsetMatrix;
	}

	public static class Mesh {
		public String mName;
		public ArrayList<aiVector3D> mPositions = new ArrayList<aiVector3D>();
		public ArrayList<Face> mPosFaces = new ArrayList<Face>();
		public ArrayList<aiVector3D> mNormals = new ArrayList<aiVector3D>();
		public ArrayList<Face> mNormFaces = new ArrayList<Face>();
		public int mNumTextures;
		public ArrayList<aiVector2D>[] mTexCoords = ArrayUtil.Generator.populateArray(new ArrayList[mesh.AI_MAX_NUMBER_OF_TEXTURECOORDS], new ArrayUtil.Generator<ArrayList>(){});
		public int mNumColorSets;
		public ArrayList<aiColor4D>[] mColors = ArrayUtil.Generator.populateArray(new ArrayList[mesh.AI_MAX_NUMBER_OF_COLOR_SETS], new ArrayUtil.Generator<ArrayList>() {});

		public ArrayList<Integer> mFaceMaterials = new ArrayList<Integer>();
		public ArrayList<Material> mMaterials = new ArrayList<Material>();

		public ArrayList<Bone> mBones = new ArrayList<Bone>();

		public Mesh() {
			mName = "";
		}

		public Mesh(String pName) {
			mName = pName;
		}
	}

	public static class Node {
		public String mName = "";
		public aiMatrix4x4 mTrafoMatrix = new aiMatrix4x4();
		public Node mParent;
		public ArrayList<Node> mChildren = new ArrayList<>();
		public ArrayList<Mesh> mMeshes = new ArrayList<Mesh>();

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
		public ArrayList<aiVectorKey> mPosKeys = new ArrayList<aiVectorKey>();
		public ArrayList<aiQuatKey> mRotKeys = new ArrayList<aiQuatKey>();
		public ArrayList<aiVectorKey> mScaleKeys = new ArrayList<aiVectorKey>();
		public ArrayList<MatrixKey> mTrafoKeys = new ArrayList<MatrixKey>();
	}

	public static class Animation {
		public String mName;
		public ArrayList<AnimBone> mAnims = new ArrayList<AnimBone>();
		public void destroy() {
			mAnims.clear();
		}
	}

	public static class Scene {
		public Node mRootNode;

		public ArrayList<Mesh> mGlobalMeshes = new ArrayList<Mesh>();
		public ArrayList<Material> mGlobalMaterials = new ArrayList<Material>();

		public ArrayList<Animation> mAnims = new ArrayList<Animation>();
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
