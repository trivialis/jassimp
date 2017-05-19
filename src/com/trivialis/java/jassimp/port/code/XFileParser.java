package com.trivialis.java.jassimp.port.code;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.trivialis.java.jassimp.port.code.Exceptional.DeadlyImportError;
import com.trivialis.java.jassimp.port.code.XFileHelper.AnimBone;
import com.trivialis.java.jassimp.port.code.XFileHelper.Animation;
import com.trivialis.java.jassimp.port.code.XFileHelper.Bone;
import com.trivialis.java.jassimp.port.code.XFileHelper.BoneWeight;
import com.trivialis.java.jassimp.port.code.XFileHelper.Face;
import com.trivialis.java.jassimp.port.code.XFileHelper.Material;
import com.trivialis.java.jassimp.port.code.XFileHelper.MatrixKey;
import com.trivialis.java.jassimp.port.code.XFileHelper.Mesh;
import com.trivialis.java.jassimp.port.code.XFileHelper.Node;
import com.trivialis.java.jassimp.port.code.XFileHelper.Scene;
import com.trivialis.java.jassimp.port.code.XFileHelper.TexEntry;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiQuatKey;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiVectorKey;
import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs;
import com.trivialis.java.jassimp.port.include.assimp.defs.Real;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.mesh;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
import com.trivialis.java.jassimp.port.include.assimp.vector2.aiVector2D;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.StringUtil;
import com.trivialis.java.jassimp.util.ctype;
import com.trivialis.java.jassimp.util.std;
import com.trivialis.java.jassimp.util.string;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class XFileParser {

	public static final int MSZIP_MAGIC = 0x4B43;
	public static final int MSZIP_BLOCK = 32786;
	private Object mMajorVersion;
	private boolean mIsBinaryFormat;
	private int mBinaryNumCount;
	private int mMinorVersion;
	private IPointer<Character> P;
	private IPointer<Character> End;
	private int mLineNumber;
	private Scene mScene;
	private int mBinaryFloatSize;





































	public XFileParser(Character[] pBuffer)
	{
		mMajorVersion = mMinorVersion = 0;
		mIsBinaryFormat = false;
		mBinaryNumCount = 0;
		P = End = null;
		mLineNumber = 0;
		mScene = null;


		ArrayList<Character> uncompressed = new ArrayList<Character>();

		P = Pointer.valueOf(pBuffer);
		End = P.pointerCopy().pointerAdjust(pBuffer.length - 1);

		if (string.strncmp(P, "xof ", 4) != 0)
			throw new Exceptional.DeadlyImportError("Header mismatch, file is not an XFile.");

		mMajorVersion = (P.pointerOffset(4).get() - 48) * 10 + (P.pointerOffset(5).get() - 48);
		mMinorVersion = (P.pointerOffset(6).get() - 48) * 10 + (P.pointerOffset(7).get() - 48);

		boolean compressed = false;

		if (string.strncmp(P.pointerOffset(8), "txt ", 4) == 0)
			mIsBinaryFormat = false;
		else if (string.strncmp(P.pointerOffset(8), "bin ", 4) == 0)
			mIsBinaryFormat = true;
		else if (string.strncmp(P.pointerOffset(8), "tzip", 4) == 0)
		{
			mIsBinaryFormat = false;
			compressed = true;
		} else if (string.strncmp(P.pointerOffset(8), "bzip", 4) == 0)
		{
			mIsBinaryFormat = true;
			compressed = true;
		} else ThrowException(TinyFormatter.format("Unsupported xfile format '", P.pointerOffset(8).get() + "", P.pointerOffset(9).get() + "", P.pointerOffset(10).get() + "", P.pointerOffset(11).get() + "", "'"));

		mBinaryFloatSize = (P.pointerOffset(12).get() - 48) * 1000 + (P.pointerOffset(13).get() - 48) * 100 + (P.pointerOffset(14).get() - 48) * 10 + (P.pointerOffset(15).get() - 48);

		if (mBinaryFloatSize != 32 && mBinaryFloatSize != 64)
			ThrowException(TinyFormatter.format("Unknown float size ", mBinaryFloatSize + "", " specified in xfile header."));

		mBinaryFloatSize /= 8;

		P = P.pointerAdjust(16);


		if (compressed)
		{
			throw new NotImplementedException();

























































































































		} else
		{
			ReadUntilEndOfLine();
		}
		
		////System.out.println(mLineNumber);
		////System.out.println((int) P.get().charValue());

		mScene = new Scene();
		ParseFile();

		if (mScene.mRootNode != null)
		{
			FilterHierarchy(mScene.mRootNode);
		}

	}




	public void destroy()
	{
		mScene = null;
	}


	private void ParseFile()
	{
//		//System.out.println(new String(new StringBuilder(new CharSequence() {
//			
//			@Override
//			public CharSequence subSequence(int start, int end)
//			{
//				return new CharSequence() {
//					
//					@Override
//					public CharSequence subSequence(int start, int end)
//					{
//						return null;
//					}
//					
//					@Override
//					public int length()
//					{
//						return end-start;
//					}
//					
//					@Override
//					public char charAt(int index)
//					{
//						return P.deep()[start+index];
//					}
//				};
//			}
//			
//			@Override
//			public int length()
//			{
//				// TODO Auto-generated method stub
//				return P.deep().length;
//			}
//			
//			@Override
//			public char charAt(int index)
//			{
//				return P.deep()[index];
//			}
//		})));
		
		
		boolean running = true;
		while (running)
		{
			////System.out.println("Getting next token");
			String objectName = GetNextToken();
			////System.out.println(objectName);
			
			if (objectName.length() == 0)
				break;
			
			//System.out.println(objectName);

			if (objectName.equals("template")){
				ParseDataObjectTemplate();}
			else if (objectName.equals("Frame"))
				ParseDataObjectFrame(null);
			else if (objectName.equals("Mesh"))
			{
				Mesh mesh = new Mesh();
				ParseDataObjectMesh(mesh);
				mScene.mGlobalMeshes.add(mesh);
			} else if (objectName.equals("AnimTicksPerSecond"))
				ParseDataObjectAnimTicksPerSecond();
			else if (objectName.equals("AnimationSet"))
				ParseDataObjectAnimationSet();
			else if (objectName.equals("Material"))
			{
				Material material = new Material();
				ParseDataObjectMaterial(material);
				mScene.mGlobalMaterials.add(material);
			} else if (objectName.equals("}"))
			{
				Logger.getLogger("default").warning("} found in dataObject");
			} else
			{
				Logger.getLogger("default").warning("Unknown data object in animation of .x file");
				ParseUnknownDataObject();
			}
		}










	}


	private void ParseDataObjectTemplate()
	{
		IPointer<String> name = Pointer.valueOf("");
		readHeadOfDataObject(name);
		
		////System.out.println(name.get());

		String guid = GetNextToken();
		////System.out.println(guid);

		boolean running = true;
		while (running)
		{
			String s = GetNextToken();
			////System.out.println("dat object template, next token: " + s);
			if ("}".equals(s))
				break;

			if (s.length() == 0)
				ThrowException("Unexpected end of file reached while parsing template definition");
		}



	}


	private void ParseDataObjectFrame(Node pParent)
	{
		IPointer<String> name = Pointer.valueOf("");
		readHeadOfDataObject(name);

		Node node = new Node(pParent);
		node.mName = name.get();
		if (pParent != null)
		{
			pParent.mChildren.add(node);
		} else
		{
			if (mScene.mRootNode != null)
			{
				if (!"$dummy_root".equals(mScene.mRootNode.mName))
				{
					Node exroot = mScene.mRootNode;
					mScene.mRootNode = new Node(null);
					mScene.mRootNode.mName = "$dummy_root";
					mScene.mRootNode.mChildren.add(exroot);
					exroot.mParent = mScene.mRootNode;
				}

				mScene.mRootNode.mChildren.add(node);
				node.mParent = mScene.mRootNode;
			} else
			{
				mScene.mRootNode = node;
			}
		}

		boolean running = true;
		while (running)
		{
			String objectName = GetNextToken();
			if (objectName.length() == 0)
				ThrowException("Unexpected end of file reached while parsing frame");

			if ("}".equals(objectName))
				break; // frame finished
			else if ("Frame".equals(objectName))
				ParseDataObjectFrame(node); // child frame
			else if ("FrameTransformMatrix".equals(objectName))
				ParseDataObjectTransformationMatrix(node.mTrafoMatrix);
			else if ("Mesh".equals(objectName))
			{
				Mesh mesh = new Mesh(name.get());
				node.mMeshes.add(mesh);
				ParseDataObjectMesh(mesh);
			} else
			{
				Logger.getLogger("default").warning("Unknown data object in frame in x file");
				ParseUnknownDataObject();
			}
		}














	}


	private void ParseDataObjectTransformationMatrix(aiMatrix4x4 pMatrix)
	{
		readHeadOfDataObject(Pointer.valueOf(""));
		pMatrix.a1 = ReadFloat();pMatrix.b1 = ReadFloat();
		pMatrix.c1 = ReadFloat();pMatrix.d1 = ReadFloat();
		pMatrix.a2 = ReadFloat();pMatrix.b2 = ReadFloat();
		pMatrix.c2 = ReadFloat();
		pMatrix.d2 = ReadFloat();
		pMatrix.a3 = ReadFloat();
		pMatrix.b3 = ReadFloat();
		pMatrix.c3 = ReadFloat();
		pMatrix.d3 = ReadFloat();
		pMatrix.a4 = ReadFloat();
		pMatrix.b4 = ReadFloat();
		pMatrix.c4 = ReadFloat();
		pMatrix.d4 = ReadFloat();
		CheckForSemicolon();
		CheckForClosingBrace();
	}


	private void ParseDataObjectMesh(Mesh pMesh)
	{
		IPointer<String> name = Pointer.valueOf("");
		readHeadOfDataObject(name);

		int numVertices = ReadInt();
		while(pMesh.mPositions.size()<numVertices) pMesh.mPositions.add(new aiVector3D());

		for (int a = 0; a < numVertices; a++)
			pMesh.mPositions.set(a, ReadVector3());

		int numPosFaces = ReadInt();
		while(pMesh.mPosFaces.size()<numPosFaces) pMesh.mPosFaces.add(new Face());
		for (int a = 0; a < numPosFaces; a++)
		{
			int numIndices = ReadInt();
			if (numIndices < 3)
			{
				ThrowException(TinyFormatter.format("Invalid index count ", numIndices + "", " for face ", a + "", "."));
			}

			Face face = pMesh.mPosFaces.get(a);
			for (int b = 0; b < numIndices; b++)
				face.mIndices.add(ReadInt());
			TestForSeparator();
		}

		boolean running = true;
		while (running)
		{
			String objectName = GetNextToken();

			if (objectName.length() == 0)
				ThrowException("Unexpected end of file while parsing mesh structure");
			else if ("}".equals(objectName))
				break;
			else if ("MeshNormals".equals(objectName))
				ParseDataObjectMeshNormals(pMesh);
			else if ("MeshTextureCoords".equals(objectName))
				ParseDataObjectMeshTextureCoords(pMesh);
			else if ("MeshVertexColors".equals(objectName))
				ParseDataObjectMeshVertexColors(pMesh);
			else if ("MeshMaterialList".equals(objectName))
				ParseDataObjectMeshMaterialList(pMesh);
			else if ("VertexDuplicationIndices".equals(objectName))
				ParseUnknownDataObject();
			else if ("XSkinMeshHeader".equals(objectName))
				ParseDataObjectSkinMeshHeader(pMesh);
			else if ("SkinWeights".equals(objectName))
				ParseDataObjectSkinWeights(pMesh);
			else
			{
				Logger.getLogger("default").warning("Unknown data object in mesh in x file");
				ParseUnknownDataObject();
			}
		}












	}


	private void ParseDataObjectSkinWeights(Mesh pMesh)
	{
		readHeadOfDataObject(Pointer.valueOf(""));

		IPointer<String> transformNodeName = Pointer.valueOf("");
		GetNextTokenAsString(transformNodeName);

		pMesh.mBones.add(new Bone());
		Bone bone = pMesh.mBones.get(pMesh.mBones.size() - 1);
		bone.mName = transformNodeName.get();
		int numWeights = ReadInt();
		while(bone.mWeights.size() < numWeights) bone.mWeights.add(new BoneWeight());
		for (int a = 0; a < numWeights; a++)
		{
			BoneWeight weight = new BoneWeight();
			weight.mVertex = ReadInt();
			bone.mWeights.add(weight);
		}
		for (int a = 0; a < numWeights; a++)
			bone.mWeights.get(a).mWeight = ReadFloat();
		bone.mOffsetMatrix.a1 = ReadFloat();
		bone.mOffsetMatrix.b1 = ReadFloat();
		bone.mOffsetMatrix.c1 = ReadFloat();
		bone.mOffsetMatrix.d1 = ReadFloat();
		bone.mOffsetMatrix.a2 = ReadFloat();
		bone.mOffsetMatrix.b2 = ReadFloat();
		bone.mOffsetMatrix.c2 = ReadFloat();
		bone.mOffsetMatrix.d2 = ReadFloat();
		bone.mOffsetMatrix.a3 = ReadFloat();
		bone.mOffsetMatrix.b3 = ReadFloat();
		bone.mOffsetMatrix.c3 = ReadFloat();
		bone.mOffsetMatrix.d3 = ReadFloat();
		bone.mOffsetMatrix.a4 = ReadFloat();
		bone.mOffsetMatrix.b4 = ReadFloat();
		bone.mOffsetMatrix.c4 = ReadFloat();
		bone.mOffsetMatrix.d4 = ReadFloat();
		CheckForSemicolon();
		CheckForClosingBrace();
	}


	private void ParseDataObjectSkinMeshHeader(Mesh pMesh)
	{
		readHeadOfDataObject(Pointer.valueOf(""));

		/* unsigned int maxSkinWeightsPerVertex = */ ReadInt();
		/* unsigned int maxSkinWeightsPerFace = */ ReadInt();
		/* unsigned int numBonesInMesh = */ReadInt();

		CheckForClosingBrace();
	}


	private void ParseDataObjectMeshNormals(Mesh pMesh)
	{
		readHeadOfDataObject(Pointer.valueOf(""));

		int numNormals = ReadInt();
		while(pMesh.mNormals.size() < numNormals) pMesh.mNormals.add(new aiVector3D());

		for (int a = 0; a < numNormals; a++)
			pMesh.mNormals.set(a, ReadVector3());

		int numFaces = ReadInt();
		if (numFaces != pMesh.mPosFaces.size())
			ThrowException("Normal face count does not match vertex face count.");

		for (int a = 0; a < numFaces; a++)
		{
			int numIndices = ReadInt();
			pMesh.mNormFaces.add(new Face());
			Face face = pMesh.mNormFaces.get(pMesh.mNormFaces.size() - 1);

			for (int b = 0; b < numIndices; b++)
				face.mIndices.add(ReadInt());

			TestForSeparator();
		}

		CheckForClosingBrace();



	}


	private void ParseDataObjectMeshTextureCoords(Mesh pMesh)
	{
		readHeadOfDataObject(Pointer.valueOf(""));
		if (pMesh.mNumTextures + 1 > mesh.AI_MAX_NUMBER_OF_TEXTURECOORDS)
			ThrowException("Too many sets of texture coordinates");

		ArrayList<aiVector2D> coords = pMesh.mTexCoords[pMesh.mNumTextures++];

		int numCoords = ReadInt();
		if (numCoords != pMesh.mPositions.size())
			ThrowException("Texture coord count does not match vertex count");

		while(coords.size()<numCoords) coords.add(new aiVector2D());
		for (int a = 0; a < numCoords; a++)
			coords.set(a, ReadVector2());

		CheckForClosingBrace();
	}


	private void ParseDataObjectMeshVertexColors(Mesh pMesh)
	{
		readHeadOfDataObject(Pointer.valueOf(""));
		if (pMesh.mNumColorSets + 1 > mesh.AI_MAX_NUMBER_OF_COLOR_SETS)
			ThrowException("Too many colorsets");
		ArrayList<aiColor4D> colors = pMesh.mColors[pMesh.mNumColorSets++];

		int numColors = ReadInt();
		if (numColors != pMesh.mPositions.size())
			ThrowException("Vertex color count does not match vertex count");

		colors.ensureCapacity(numColors + 1);
		for (int a = 0; a < numColors; a++)
		{
			int index = ReadInt();
			if (index >= pMesh.mPositions.size())
				ThrowException("Vertex color index out of bounds");

			colors.set(index, ReadRGBA());
			if (!mIsBinaryFormat)
			{
				FindNextNoneWhiteSpace();
				if (P.get() == ';' || P.get() == ',')
					P.postInc();
			}
		}

		CheckForClosingBrace();


	}


	private void ParseDataObjectMeshMaterialList(Mesh pMesh)
	{
		readHeadOfDataObject(Pointer.valueOf(""));

		ReadInt();

		int numMatIndices = ReadInt();

		if (numMatIndices != pMesh.mPosFaces.size() && numMatIndices != 1)
			ThrowException("Per-Face material index count does not match face count.");

		for (int a = 0; a < numMatIndices; a++)
			pMesh.mFaceMaterials.add(ReadInt());

		if (!mIsBinaryFormat)
		{
			if (P.getOffset() < End.getOffset() && P.get() == ';')
				P.postInc();
		}

		while (pMesh.mFaceMaterials.size() < pMesh.mPosFaces.size())
			pMesh.mFaceMaterials.add(pMesh.mFaceMaterials.get(0));

		boolean running = true;
		while (running)
		{
			String objectName = GetNextToken();
			if (objectName.length() == 0)
				ThrowException("Unexpected end of file while parsing mesh material list.");
			else if ("}".equals(objectName))
				break;
			else if ("{".equals(objectName))
			{
				String matName = GetNextToken();
				Material material = new Material();
				material.mIsReference = true;
				material.mName = matName;
				pMesh.mMaterials.add(material);

				CheckForClosingBrace();
			} else if ("Material".equals(objectName))
			{
				pMesh.mMaterials.add(new Material());
				ParseDataObjectMaterial(pMesh.mMaterials.get(pMesh.mMaterials.size() - 1));
			} else if (";".equals(objectName))
			{

			} else
			{
				Logger.getLogger("default").warning("Unknown data object in material list in x file");
				ParseUnknownDataObject();
			}
		}













	}


	private void ParseDataObjectMaterial(Material pMaterial)
	{
		IPointer<String> matName = Pointer.valueOf("");
		readHeadOfDataObject(matName);
		if (matName.get().isEmpty())
			matName.set("material" + mLineNumber);
		pMaterial.mName = matName.get();
		pMaterial.mIsReference = false;

		pMaterial.mDiffuse = ReadRGBA();
		pMaterial.mSpecularExponent = ReadFloat();
		pMaterial.mSpecular = ReadRGB();
		pMaterial.mEmissive = ReadRGB();

		boolean running = true;
		while (running)
		{
			String objectName = GetNextToken();
			if (objectName.length() == 0)
				ThrowException("Unexpected end of file while parsing mesh material");
			else if ("}".equals(objectName))
				break;
			else if ("TextureFilename".equals(objectName) || "TextureFileName".equals(objectName))
			{
				IPointer<String> texname = Pointer.valueOf("");
				ParseDataObjectTextureFilename(texname);
				pMaterial.mTextures.add(new TexEntry(texname.get()));
			} else if ("NormalmapFilename".equals(objectName) || "NormalmapFileName".equals(objectName))
			{
				IPointer<String> texname = Pointer.valueOf("");
				ParseDataObjectTextureFilename(texname);
				pMaterial.mTextures.add(new TexEntry(texname.get(), true));
			} else
			{
				Logger.getLogger("default").warning("Unknown data object in material in x file");
				ParseUnknownDataObject();
			}
		}







	}


	private void ParseDataObjectAnimTicksPerSecond()
	{
		readHeadOfDataObject(Pointer.valueOf(""));
		mScene.mAnimTicksPerSecond = ReadInt();
		CheckForClosingBrace();
	}


	private void ParseDataObjectAnimationSet()
	{
		IPointer<String> animName = Pointer.valueOf("");
		readHeadOfDataObject(animName);

		Animation anim = new Animation();
		mScene.mAnims.add(anim);
		anim.mName = animName.get();

		boolean running = true;
		while (running)
		{
			String objectName = GetNextToken();
			if (objectName.length() == 0)
				ThrowException("Unexpected end of file while parsing animation set.");
			else if ("}".equals(objectName))
				break; // animation set finished
			else if ("Animation".equals(objectName))
				ParseDataObjectAnimation(anim);
			else
			{
				Logger.getLogger("default").warning("Unknown data object in animation set in x file");
				ParseUnknownDataObject();
			}
		}


	}


	private void ParseDataObjectAnimation(Animation pAnim)
	{
		readHeadOfDataObject(Pointer.valueOf(""));
		AnimBone banim = new AnimBone();
		pAnim.mAnims.add(banim);

		boolean running = true;
		while (running)
		{
			String objectName = GetNextToken();

			if (objectName.length() == 0)
				ThrowException("Unexpected end of file while parsing animation.");
			else if ("}".equals(objectName))
				break; // animation finished
			else if ("AnimationKey".equals(objectName))
				ParseDataObjectAnimationKey(banim);
			else if ("AnimationOptions".equals(objectName))
				ParseUnknownDataObject(); // not interested
			else if ("{".equals(objectName))
			{
				// read frame name
				banim.mBoneName = GetNextToken();
				CheckForClosingBrace();
			} else
			{
				Logger.getLogger("default").warning("Unknown data object in animation in x file");
				ParseUnknownDataObject();
			}
		}




	}


	private void ParseDataObjectAnimationKey(AnimBone pAnimBone)
	{
		readHeadOfDataObject(Pointer.valueOf(""));

		// read key type
		int keyType = ReadInt();

		// read number of keys
		int numKeys = ReadInt();

		for (int a = 0; a < numKeys; a++)
		{
			// read time
			int time = ReadInt();

			// read keys
			switch (keyType)
			{
				case 0: // rotation quaternion
				{
					// read count
					if (ReadInt() != 4)
						ThrowException("Invalid number of arguments for quaternion key in animation");

					aiQuatKey key = new aiQuatKey();
					key.mTime = (double) time;
					key.mValue.w = ReadFloat();
					key.mValue.x = ReadFloat();
					key.mValue.y = ReadFloat();
					key.mValue.z = ReadFloat();
					pAnimBone.mRotKeys.add(key);

					CheckForSemicolon();
					break;
				}

				case 1: // scale vector
				case 2: // position vector
				{
					// read count
					if (ReadInt() != 3)
						ThrowException("Invalid number of arguments for vector key in animation");

					aiVectorKey key = new aiVectorKey();
					key.mTime = (double) time;
					key.mValue = ReadVector3();

					if (keyType == 2)
						pAnimBone.mPosKeys.add(key);
					else pAnimBone.mScaleKeys.add(key);

					break;
				}

				case 3: // combined transformation matrix
				case 4: // denoted both as 3 or as 4
				{
					// read count
					if (ReadInt() != 16)
						ThrowException("Invalid number of arguments for matrix key in animation");

					// read matrix
					MatrixKey key = new MatrixKey();
					key.mTime = (double) time;
					key.mMatrix.a1 = ReadFloat();key.mMatrix.b1 = ReadFloat();
					key.mMatrix.c1 = ReadFloat();key.mMatrix.d1 = ReadFloat();
					key.mMatrix.a2 = ReadFloat();key.mMatrix.b2 = ReadFloat();
					key.mMatrix.c2 = ReadFloat();key.mMatrix.d2 = ReadFloat();
					key.mMatrix.a3 = ReadFloat();key.mMatrix.b3 = ReadFloat();
					key.mMatrix.c3 = ReadFloat();key.mMatrix.d3 = ReadFloat();
					key.mMatrix.a4 = ReadFloat();key.mMatrix.b4 = ReadFloat();
					key.mMatrix.c4 = ReadFloat();
					key.mMatrix.d4 = ReadFloat();
					pAnimBone.mTrafoKeys.add(key);

					CheckForSemicolon();
					break;
				}

				default:
					ThrowException(TinyFormatter.format("Unknown key type ", keyType + "", " in animation."));
					break;
			} // end switch

			// key separator
			CheckForSeparator();
		}

		CheckForClosingBrace();
	}


	private void ParseDataObjectTextureFilename(IPointer<String> pName)
	{
		readHeadOfDataObject(Pointer.valueOf(""));
		GetNextTokenAsString(pName);
		CheckForClosingBrace();

		if (pName.get().length() == 0)
		{
			Logger.getLogger("default").warning("Length of texture file name is zero. Skipping this texture.");
		}

		while (pName.get().indexOf("\\\\") != std.string.npos)
			pName.set(pName.get().replace("\\\\", "\\"));


	}


	private void ParseUnknownDataObject()
	{
		boolean running = true;
		while (running)
		{
			String t = GetNextToken();
			if (t.length() == 0)
				ThrowException("Unexpected end of file while parsing unknown segment.");

			if ("{".equals(t))
				break;
		}

		int counter = 1;

		while (counter > 0)
		{
			String t = GetNextToken();

			if (t.length() == 0)
				ThrowException("Unexpected end of file while parsing unknown segment.");

			if ("{".equals(t))
				++counter;
			else if ("}".equals(t))
				--counter;
		}



	}



	private void CheckForClosingBrace()
	{
		if (!"}".equals(GetNextToken()))
			ThrowException("Closing brace expected.");
	}



	private void CheckForSemicolon()
	{
		if (mIsBinaryFormat)
			return;

		if (!";".equals(GetNextToken()))
			ThrowException("Semicolon expected.");
	}



	private void CheckForSeparator()
	{
		if (mIsBinaryFormat)
			return;

		String token = GetNextToken();
		if (!",".equals(token) && !";".equals(token))
			ThrowException("Separator character (';' or ',') expected. Got: " + token);
	}



	private void TestForSeparator()
	{
		if (mIsBinaryFormat)
			return;

		FindNextNoneWhiteSpace();
		if (P.getOffset() >= End.getOffset())
			return;

		if (P.get() == ';' || P.get() == ',')
			P.postInc();
	}



	private void readHeadOfDataObject(IPointer<String> poName)
	{
		String nameOrBrace = GetNextToken();
		////System.out.println(nameOrBrace);
		if (!"{".equals(nameOrBrace))
		{
			if (poName != null)
				poName.set(nameOrBrace);
			String a = GetNextToken();// //System.out.println((int) a.charAt(1));
			if (!a.equals("{"))
				ThrowException("Opening brace expected:" + a);
		}
	}


	private String GetNextToken()
	{
		String s = "";

		if (mIsBinaryFormat)
		{
			throw new NotImplementedException();
































































































		} else
		{
			////System.out.println("Getting token...");
			FindNextNoneWhiteSpace();
			if (P.getOffset() >= End.getOffset())
				return s;
			////System.out.println(s);
			while ((P.getOffset() < End.getOffset()) && !ctype.isspace(P.get()))
			{
				
				if (P.get() == ';' || P.get() == '}' || P.get() == '{' || P.get() == ',')
				{
					if (s == null || s.length() == 0)
						s = s + P.get();
					P.postInc();
                                        break;
				}
				s = s + P.get();
				P.postInc();

			}

		}
                
		return s;
	}


	private void FindNextNoneWhiteSpace()
	{
		if (mIsBinaryFormat)
			return;

		boolean running = true;
		while (running)
		{
			while (P.getOffset() < End.getOffset() && ctype.isspace(P.get()))
			{
				if (P.get() == '\n')
					mLineNumber++;
				P.postInc();
			}
			
			if (P.getOffset() >= End.getOffset())
				return;

			if ((P.pointerOffset(0).get() == '/' && P.pointerOffset(1).get() == '/') || P.pointerOffset(0).get() == '#')
				ReadUntilEndOfLine();
			else break;

		}

	}


	private void GetNextTokenAsString(IPointer<String> poString)
	{
		if (mIsBinaryFormat)
		{
			poString.set(GetNextToken());
			return;
		}

		FindNextNoneWhiteSpace();
		if (P.getOffset() >= End.getOffset())
			ThrowException("Unexpected end of file while parsing string");

		if (P.get() != '"')
			ThrowException("Expected quotation mark.");
		P.postInc();

		while (P.getOffset() < End.getOffset() && P.get() != '"')
			poString.set(poString.get() + P.postInc().get());

		if (P.getOffset() >= End.getOffset() - 1)
			ThrowException("Unexpected end of file while parsing string");

		if (P.pointerOffset(1).get() != ';' || P.pointerOffset(0).get() != '"')
			ThrowException("Expected quotation mark and semicolon at the end of a string.");
		P.pointerAdjust(2);
	}


	private void ReadUntilEndOfLine()
	{
		if (mIsBinaryFormat)
			return;

		while (P.opSmaller(End))
		{
			if (P.get() == '\n' || P.get() == '\r')
			{
				P.postInc();
				mLineNumber++;
				return;
			}
			P.postInc();
		}
	}

	private short ReadBinWord() {
		throw new NotImplementedException();






	}

	private void ReadBinDWord() {
		throw new NotImplementedException();






	}


	private int ReadInt()
	{
		if (mIsBinaryFormat)
		{
			throw new NotImplementedException();



















		} else
		{
			FindNextNoneWhiteSpace();

			boolean isNegative = false;
			if (P.get() == '-')
			{
				isNegative = true;
				P.postInc();
			}

			if (!ctype.isdigit(P.get()))
				ThrowException("Number expected.");


			int number = 0;
			while (P.getOffset() < End.getOffset())
			{
				if (!ctype.isdigit(P.get()))
					break;
				number = number * 10 + (P.get() - 48);
				P.postInc();
			}

			CheckForSeparator();
			return isNegative ? -1 * ((int) number) : number;
		}
	}


	private ai_real ReadFloat()
	{
		if (mIsBinaryFormat)
		{
			throw new NotImplementedException();




































		}

		FindNextNoneWhiteSpace();
		if (string.strncmp(P, "-1.#IND00", 9) == 0 || string.strncmp(P, "1.#IND00", 8) == 0)
		{
			//System.out.println(StringUtil.getCharactersAsString(P, 9));P.pointerAdjust(9);
			CheckForSeparator();
			return new ai_real(0.0);
		} else if (string.strncmp(P, "1.#QNAN0", 8) == 0)
		{
			P.pointerAdjust(8);
			return new ai_real(0.0);
		}

		IPointer<Real> result = Pointer.valueOf(new defs.Real(0.0));
		P = (fast_atof.fast_atoreal_move(P, result));

		CheckForSeparator();

		return new ai_real(0.0f).forValue(result.get().getValue());
	}



	private aiVector2D ReadVector2()
	{
		aiVector2D vector = new aiVector2D();
		vector.x = ReadFloat();
		vector.y = ReadFloat();
		TestForSeparator();
		return vector;
	}


	private aiVector3D ReadVector3()
	{
		aiVector3D vector = new aiVector3D();
		vector.x = ReadFloat();
		vector.y = ReadFloat();
		vector.z = ReadFloat();
		TestForSeparator();

		return vector;
	}


	private aiColor4D ReadRGBA()
	{
		aiColor4D color = new aiColor4D();
		color.r = ReadFloat();
		color.g = ReadFloat();
		color.b = ReadFloat();
		color.a = ReadFloat();
		TestForSeparator();

		return color;
	}


	private aiColor3D ReadRGB()
	{
		aiColor3D color = new aiColor3D();
		color.r = ReadFloat();
		color.g = ReadFloat();
		color.b = ReadFloat();
		TestForSeparator();

		return color;
	}

	private void ThrowException(String pText)
	{
		if (mIsBinaryFormat)
			throw new DeadlyImportError(pText);
		else throw new DeadlyImportError(TinyFormatter.format("Line ", mLineNumber + "", ": ", pText));
	}

	private void FilterHierarchy(Node pNode)
	{
		// if the node has just a single unnamed child containing a mesh, remove
		// the anonymous node between. The 3DSMax kwXport plugin seems to
		// produce this
		// mess in some cases
		if (pNode.mChildren.size() == 1 && pNode.mMeshes.isEmpty())
		{
			Node child = pNode.mChildren.get(0);
			if (child.mName.length() == 0 && child.mMeshes.size() > 0)
			{
				// transfer its meshes to us
				for (int a = 0; a < child.mMeshes.size(); a++)
					pNode.mMeshes.add(child.mMeshes.get(a));
				child.mMeshes.clear();

				// transfer the transform as well
				pNode.mTrafoMatrix = (aiMatrix4x4) pNode.mTrafoMatrix.opMultiply(child.mTrafoMatrix);

				// then kill it
				child = null;
				pNode.mChildren.clear();
			}
		}

		// recurse
		for (int a = 0; a < pNode.mChildren.size(); a++)
			FilterHierarchy(pNode.mChildren.get(a));
	}


	public Scene GetImportedData()
	{
		return mScene;
	}






}
