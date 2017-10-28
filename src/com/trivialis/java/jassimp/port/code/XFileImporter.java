package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.port.code.ConvertToLHProcess.FlipWindingOrderProcess;
import com.trivialis.java.jassimp.port.code.ConvertToLHProcess.MakeLeftHandedProcess;
import com.trivialis.java.jassimp.port.code.XFileHelper.AnimBone;
import com.trivialis.java.jassimp.port.code.XFileHelper.Animation;
import com.trivialis.java.jassimp.port.code.XFileHelper.Bone;
import com.trivialis.java.jassimp.port.code.XFileHelper.Face;
import com.trivialis.java.jassimp.port.code.XFileHelper.Material;
import com.trivialis.java.jassimp.port.code.XFileHelper.Mesh;
import com.trivialis.java.jassimp.port.code.XFileHelper.Node;
import com.trivialis.java.jassimp.port.code.XFileHelper.Scene;
import com.trivialis.java.jassimp.port.code.XFileHelper.TexEntry;
import com.trivialis.java.jassimp.port.include.assimp.IOStream;
import com.trivialis.java.jassimp.port.include.assimp.IOSystem;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiAnimation;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiNodeAnim;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiQuatKey;
import com.trivialis.java.jassimp.port.include.assimp.anim.aiVectorKey;
import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.material;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3.aiMatrix3x3;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.mesh;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiBone;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiFace;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiMesh;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiVertexWeight;
import com.trivialis.java.jassimp.port.include.assimp.quaternion.aiQuaternion;
import com.trivialis.java.jassimp.port.include.assimp.scene;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiNode;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.port.include.assimp.vector2.aiVector2D;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.util.ArrayUtil;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.StringUtil;
import com.trivialis.java.jassimp.util.ctype;
import com.trivialis.java.jassimp.util.std;
import com.trivialis.java.jassimp.util.string;
import java.util.ArrayList;
import java.util.logging.Logger;

public class XFileImporter extends BaseImporter {

	public IPointer<byte[]> mBuffer
	= Pointer.valueOf(new byte[0]);
	;
    public XFileParser parser;

    public boolean CanRead(String pFile, IOSystem pIOHandler, boolean CheckSig) {
    	return false;
    }
    
//    protected aiImporterDesc GetInfo() {
//    	return null;
//    }

	@Override
	public void InternReadFile(IPointer<StringBuilder> pFile, aiScene pScene, IPointer<IOSystem> pIOHandler)
	{
		IPointer<IOStream> file = Pointer.valueOf(pIOHandler.get().Open(pFile));
		if(file.get() == null)
			throw new Exceptional.DeadlyImportError("Failed to open file " + pFile + ".");

		int fileSize = file.get().FileSize();
		if(fileSize < 16)
			throw new Exceptional.DeadlyImportError("XFile is too small.");

		mBuffer.set(new byte[fileSize + 1]);
		file.get().Read(mBuffer.get(), 1, fileSize);
		ConvertToUTF8(mBuffer);

		parser = new XFileParser(StringUtil.toCharacterArray(mBuffer.get()));

		CreateDataRepresentationFromImport(pScene, parser.GetImportedData()); //I ignore scopeguard for now.

		if(pScene.mRootNode==null)
			throw new Exceptional.DeadlyImportError("XFile is ill-formatted - no content imported.");



	}

	//Line 142: code/XFileImporter.cpp
	private void CreateDataRepresentationFromImport(aiScene pScene, Scene pData)
	{
		ConvertMaterials(pScene, pData.mGlobalMaterials);
		pScene.mRootNode = CreateNodes(pScene, null, pData.mRootNode);

		CreateAnimations(pScene, pData);

		if(pData.mGlobalMeshes.size()>0)
		{
			if(pScene.mRootNode==null)
			{
				pScene.mRootNode = new scene.aiNode();
				pScene.mRootNode.mName.Set("$dummy_node");
			}

			CreateMeshes(pScene, pScene.mRootNode, pData.mGlobalMeshes);
		}

		if(pScene.mRootNode==null) {
			throw new Exceptional.DeadlyImportError("No root node");
		}

		MakeLeftHandedProcess convertProcess = new MakeLeftHandedProcess();
		convertProcess.Execute(pScene);

		FlipWindingOrderProcess flipper = new FlipWindingOrderProcess();
		flipper.Execute(pScene);

		if(pScene.mNumMaterials == 0)
		{
			pScene.mNumMaterials = 1;

			aiMaterial mat = new aiMaterial();
			int shadeMode = (int) material.aiShadingMode.aiShadingMode_Gouraud.value;
			mat.AddProperty(new int[]{shadeMode}, 1, material.AI_MATKEY_SHADING_MODEL);

			int specExp = 1;

			aiColor3D clr = new aiColor3D(0F,0F, 0F);
	        mat.AddProperty(new aiColor3D[]{clr}, 1, material.AI_MATKEY_COLOR_EMISSIVE);
	        mat.AddProperty(new aiColor3D[]{clr}, 1, material.AI_MATKEY_COLOR_SPECULAR);

	        clr = new aiColor3D(0.5F, 0.5F, 0.5F);
	        mat.AddProperty(new aiColor3D[]{clr}, 1, material.AI_MATKEY_COLOR_DIFFUSE);
	        mat.AddProperty(new aiColor3D[]{clr}, 1, material.AI_MATKEY_SHININESS);

	        pScene.mMaterials = new aiMaterial[1];
	        pScene.mMaterials[0] = mat;
		}
	}

	private aiNode CreateNodes( aiScene pScene, aiNode pParent,  Node pNode)
	{
	    if(pNode==null)
	        return null;

	    // create node
	    aiNode node = new aiNode();
	    node.mParent = pParent;
	    node.mName.Set(pNode.mName);//string.memcpy( node.mName.data, pNode.mName.getBytes(StandardCharsets.UTF_8), pNode.mName.length());
	    node.mTransformation = pNode.mTrafoMatrix;

	    // convert meshes from the source node
	    CreateMeshes( pScene, node, pNode.mMeshes);

	    // handle childs
	    if( pNode.mChildren.size() > 0)
	    {
	        node.mNumChildren = (int)pNode.mChildren.size();
	        node.mChildren = new aiNode[node.mNumChildren];

	        for(int a = 0; a < pNode.mChildren.size(); a++)
	            node.mChildren[a] = CreateNodes( pScene, node, pNode.mChildren.get(a));
	    }

	    return node;
	}

	private void CreateMeshes( aiScene pScene, aiNode pNode,  ArrayList<Mesh> pMeshes)
	{
	    if( pMeshes.size() == 0)
	        return;

	    // create a mesh for each mesh-material combination in the source node
	    ArrayList<aiMesh> meshes = new ArrayList<aiMesh>();
	    for(int a = 0; a < pMeshes.size(); a++)
	    {
	        Mesh sourceMesh = pMeshes.get(a);
	        // first convert its materials so that we can find them with their index afterwards
	        ConvertMaterials( pScene, sourceMesh.mMaterials);

	        int numMaterials = Math.max( (int)sourceMesh.mMaterials.size(), 1);
	        for(int b = 0; b < numMaterials; b++)
	        {
	            // collect the faces belonging to this material
	            ArrayList<Integer> faces = new ArrayList<Integer>();
	            int numVertices = 0;
	            if( sourceMesh.mFaceMaterials.size() > 0)
	            {
	                // if there is a per-face material defined, select the faces with the corresponding material
	                for(int c = 0; c < sourceMesh.mFaceMaterials.size(); c++)
	                {
	                    if( sourceMesh.mFaceMaterials.get(c) == b)
	                    {
	                        faces.add( c);
	                        numVertices += (int)sourceMesh.mPosFaces.get(c).mIndices.size();
	                    }
	                }
	            } else
	            {
	                // if there is no per-face material, place everything into one mesh
	                for(int c = 0; c < sourceMesh.mPosFaces.size(); c++)
	                {
	                    faces.add( c);
	                    numVertices += (int)sourceMesh.mPosFaces.get(c).mIndices.size();
	                }
	            }

	            // no faces/vertices using this material? strange...
	            if( numVertices == 0)
	                continue;

	            // create a submesh using this material
	            aiMesh mesh = new aiMesh();
	            meshes.add( mesh);

	            // find the material in the scene's material list. Either own material
	            // or referenced material, it should already have a valid index
	            if( sourceMesh.mFaceMaterials.size() > 0)
	            {
	                mesh.mMaterialIndex = (int) (sourceMesh.mMaterials.get(b).sceneIndex);
	            } else
	            {
	                mesh.mMaterialIndex = 0;
	            }

	            // Create properly sized data arrays in the mesh. We store unique vertices per face,
	            // as specified
	            mesh.mNumVertices = numVertices;
	            mesh.mVertices = new aiVector3D[numVertices];
	            mesh.mNumFaces = (int)faces.size();
	            mesh.mFaces = ArrayUtil.Generator.populateArray(new aiFace[mesh.mNumFaces], new ArrayUtil.Generator<aiFace>() {});

	            // name
	            mesh.mName.Set(sourceMesh.mName);

	            // normals?
	            if( sourceMesh.mNormals.size() > 0)
	            	mesh.mNormals = ArrayUtil.Generator.populateArray(new aiVector3D[numVertices], new ArrayUtil.Generator<aiVector3D>() {});//mesh.mNormals = new aiVector3D[numVertices];
	            // texture coords
	            for( int c = 0; c < get_AI_MAX_NUMBER_OF_TEXTURECOORDS(); c++)
	            {
	                if( sourceMesh.mTexCoords[c].size() > 0)
	                	mesh.mTextureCoords[c] = ArrayUtil.Generator.populateArray(new aiVector3D[numVertices], new ArrayUtil.Generator<aiVector3D>() {});     //mesh.mTextureCoords[c] = new aiVector3D[numVertices];
	            }
	            // vertex colors
	            for(int c = 0; c < get_AI_MAX_NUMBER_OF_COLOR_SETS(); c++)
	            {
	                if( sourceMesh.mColors[c].size() > 0)
	                    mesh.mColors[c] = new aiColor4D[numVertices];
	            }

	            // now collect the vertex data of all data streams present in the imported mesh
	            int newIndex = 0;
	            ArrayList<Integer> orgPoints = new ArrayList<Integer>(); // from which original point each new vertex stems
	            ArrayUtil.Generator.ensureSize(orgPoints,numVertices, new ArrayUtil.Generator<Integer>() {@Override
	            public Integer generate() throws InstantiationException, IllegalAccessException
	            {
	            	return 0;
	            }});//orgPoints.ensureCapacity(numVertices);

	            for(int c = 0; c < faces.size(); c++)
	            {
	                int f = faces.get(c); // index of the source face
	                Face pf = sourceMesh.mPosFaces.get(f); // position source face

	                // create face. either triangle or triangle fan depending on the index count
	                aiFace df = mesh.mFaces[c];// destination face
	                df.mNumIndices = (int)pf.mIndices.size();
	                df.mIndices = new int[ df.mNumIndices];

	                // collect vertex data for indices of this face
	                for(int d = 0; d < df.mNumIndices; d++)
	                {
	                    df.mIndices[d] = newIndex;
	                    orgPoints.set(newIndex, pf.mIndices.get(d));

	                    // Position
	                    mesh.mVertices[newIndex] = sourceMesh.mPositions.get(pf.mIndices.get(d));
	                    // Normal, if present
	                    if( mesh.HasNormals())
	                        mesh.mNormals[newIndex] = sourceMesh.mNormals.get(sourceMesh.mNormFaces.get(f).mIndices.get(d));

	                    // texture coord sets
	                    for(int e = 0; e < get_AI_MAX_NUMBER_OF_TEXTURECOORDS(); e++)
	                    {
	                    	if( mesh.HasTextureCoords( e))
	                        {
	                            aiVector2D tex = sourceMesh.mTexCoords[e].get(pf.mIndices.get(d));
	                            mesh.mTextureCoords[e][newIndex] = new aiVector3D( tex.x, 1.0F.opSubtract(tex.y), 0.0F);
	                        }
	                    }
	                    // vertex color sets
	                    for( int e = 0; e < get_AI_MAX_NUMBER_OF_COLOR_SETS(); e++)
	                        if( mesh.HasVertexColors( e))
	                            mesh.mColors[e][newIndex] = sourceMesh.mColors[e].get(pf.mIndices.get(d));

	                    newIndex++;
	                }
	            }

	            // there should be as much new vertices as we calculated before
	            assert( newIndex == numVertices);

	            // convert all bones of the source mesh which influence vertices in this newly created mesh
	            ArrayList<Bone> bones = sourceMesh.mBones;
	            ArrayList<aiBone> newBones = new ArrayList<aiBone>();
	            for(int c = 0; c < bones.size(); c++)
	            {
	                Bone obone = bones.get(c);
	                // set up a vertex-linear array of the weights for quick searching if a bone influences a vertex
	                ArrayList<float> oldWeights = new ArrayList<float>(sourceMesh.mPositions.size());
	                for(int d = 0; d < obone.mWeights.size(); d++)
	                    oldWeights.set(obone.mWeights.get(d).mVertex, obone.mWeights.get(d).mWeight);

	                // collect all vertex weights that influence a vertex in the new mesh
	                ArrayList<aiVertexWeight> newWeights = new ArrayList<aiVertexWeight>();
	                newWeights.ensureCapacity( numVertices);
	                for(int d = 0; d < orgPoints.size(); d++)
	                {
	                    // does the new vertex stem from an old vertex which was influenced by this bone?
	                    float w = oldWeights.get(orgPoints.get(d));
	                    if( w.opBigger(0.0F))
	                        newWeights.add( new aiVertexWeight( d, w));
	                }

	                // if the bone has no weights in the newly created mesh, ignore it
	                if( newWeights.size() == 0)
	                    continue;

	                // create
	                aiBone nbone = new aiBone();
	                newBones.add( nbone);
	                // copy name and matrix
	                nbone.mName.Set( obone.mName);
	                nbone.mOffsetMatrix = obone.mOffsetMatrix;
	                nbone.mNumWeights = (int)newWeights.size();
	                nbone.mWeights = new aiVertexWeight[nbone.mNumWeights];
	                for( int d = 0; d < newWeights.size(); d++)
	                    nbone.mWeights[d] = newWeights.get(d);
	            }

	            // store the bones in the mesh
	            mesh.mNumBones = (int)newBones.size();
	            if( newBones.size() > 0)
	            {
	                mesh.mBones = new aiBone[mesh.mNumBones];
	                std.copy(newBones, newBones.get(0), newBones.get(newBones.size()-1), mesh.mBones);
	            }
	        }
	    }

	    // reallocate scene mesh array to be large enough
	    aiMesh[] prevArray = pScene.mMeshes;
	    pScene.mMeshes = new aiMesh[pScene.mNumMeshes + meshes.size()];
	    if( prevArray!=null)
	    {
	        string.memcpy( pScene.mMeshes, prevArray, pScene.mNumMeshes);
	        prevArray=null;
	    }

	    // allocate mesh index array in the node
	    pNode.mNumMeshes = (int)meshes.size();
	    pNode.mMeshes = new int[pNode.mNumMeshes];

	    // store all meshes in the mesh library of the scene and store their indices in the node
	    for( int a = 0; a < meshes.size(); a++)
	    {
	        pScene.mMeshes[pScene.mNumMeshes] = meshes.get(a);
	        pNode.mMeshes[a] = pScene.mNumMeshes;
	        pScene.mNumMeshes++;
	    }
	}

	public void CreateAnimations( aiScene pScene,  Scene pData)
	{
	    ArrayList<aiAnimation> newAnims = new ArrayList<aiAnimation>();

	    for(int a = 0; a < pData.mAnims.size(); a++)
	    {
	        Animation anim = pData.mAnims.get(a);
	        // some exporters mock me with empty animation tags.
	        if( anim.mAnims.size() == 0)
	            continue;

	        // create a new animation to hold the data
	        aiAnimation nanim = new aiAnimation();
	        newAnims.add( nanim);
	        nanim.mName.Set( anim.mName);
	        // duration will be determined by the maximum length
	        nanim.mDuration = 0;
	        nanim.mTicksPerSecond = pData.mAnimTicksPerSecond;
	        nanim.mNumChannels = (int)anim.mAnims.size();
	        nanim.mChannels = new aiNodeAnim[nanim.mNumChannels];

	        for(int b = 0; b < anim.mAnims.size(); b++)
	        {
	            AnimBone bone = anim.mAnims.get(b);
	            aiNodeAnim nbone = new aiNodeAnim();
	            nbone.mNodeName.Set( bone.mBoneName);
	            nanim.mChannels[b] = nbone;

	            // keyframes are given as combined transformation matrix keys
	            if( bone.mTrafoKeys.size() > 0)
	            {
	                nbone.mNumPositionKeys = (int)bone.mTrafoKeys.size();
	                nbone.mPositionKeys = new aiVectorKey[nbone.mNumPositionKeys];
	                nbone.mNumRotationKeys = (int)bone.mTrafoKeys.size();
	                nbone.mRotationKeys = new aiQuatKey[nbone.mNumRotationKeys];
	                nbone.mNumScalingKeys = (int)bone.mTrafoKeys.size();
	                nbone.mScalingKeys = new aiVectorKey[nbone.mNumScalingKeys];

	                for( int c = 0; c < bone.mTrafoKeys.size(); c++)
	                {
	                    // deconstruct each matrix into separate position, rotation and scaling
	                    double time = bone.mTrafoKeys.get(c).mTime;
	                    aiMatrix4x4 trafo = bone.mTrafoKeys.get(c).mMatrix;

	                    // extract position
	                    aiVector3D pos = new aiVector3D( trafo.a4, trafo.b4, trafo.c4);

	                    nbone.mPositionKeys[c].mTime = time;
	                    nbone.mPositionKeys[c].mValue = pos;

	                    // extract scaling
	                    aiVector3D scale = pos.newInstance();
	                    scale.x = new aiVector3D( trafo.a1, trafo.b1, trafo.c1).Length();
	                    scale.y = new aiVector3D( trafo.a2, trafo.b2, trafo.c2).Length();
	                    scale.z = new aiVector3D( trafo.a3, trafo.b3, trafo.c3).Length();
	                    nbone.mScalingKeys[c].mTime = time;
	                    nbone.mScalingKeys[c].mValue = scale;

	                    // reconstruct rotation matrix without scaling
	                    aiMatrix3x3 rotmat = new aiMatrix3x3(
	                        trafo.a1.opDivide(scale.x), trafo.a2.opDivide(scale.y), trafo.a3.opDivide(scale.z),
	                        trafo.b1.opDivide(scale.x), trafo.b2.opDivide(scale.y), trafo.b3.opDivide(scale.z),
	                        trafo.c1.opDivide(scale.x), trafo.c2.opDivide(scale.y), trafo.c3.opDivide(scale.z));

	                    // and convert it into a quaternion
	                    nbone.mRotationKeys[c].mTime = time;
	                    nbone.mRotationKeys[c].mValue = new aiQuaternion( rotmat);
	                }

	                // longest lasting key sequence determines duration
	                nanim.mDuration = std.max( nanim.mDuration, bone.mTrafoKeys.get(bone.mTrafoKeys.size()-1).mTime);
	            } else
	            {
	                // separate key sequences for position, rotation, scaling
	                nbone.mNumPositionKeys = (int)bone.mPosKeys.size();
	                nbone.mPositionKeys = new aiVectorKey[nbone.mNumPositionKeys];
	                for( int c = 0; c < nbone.mNumPositionKeys; c++)
	                {
	                    aiVector3D pos = bone.mPosKeys.get(c).mValue;

	                    nbone.mPositionKeys[c].mTime = bone.mPosKeys.get(c).mTime;
	                    nbone.mPositionKeys[c].mValue = pos;
	                }

	                // rotation
	                nbone.mNumRotationKeys = (int)bone.mRotKeys.size();
	                nbone.mRotationKeys = new aiQuatKey[nbone.mNumRotationKeys];
	                for( int c = 0; c < nbone.mNumRotationKeys; c++)
	                {
	                    aiMatrix3x3 rotmat = new aiMatrix3x3(); bone.mRotKeys.get(c).mValue.GetMatrix(rotmat);

	                    nbone.mRotationKeys[c].mTime = bone.mRotKeys.get(c).mTime;
	                    nbone.mRotationKeys[c].mValue = new aiQuaternion( rotmat);
	                    nbone.mRotationKeys[c].mValue.w = -1.0F; // needs quat inversion
	                }

	                // scaling
	                nbone.mNumScalingKeys = (int)bone.mScaleKeys.size();
	                nbone.mScalingKeys = new aiVectorKey[nbone.mNumScalingKeys];
	                for( int c = 0; c < nbone.mNumScalingKeys; c++)
	                    nbone.mScalingKeys[c] = bone.mScaleKeys.get(c);

	                // longest lasting key sequence determines duration
	                if( bone.mPosKeys.size() > 0)
	                    nanim.mDuration = std.max( nanim.mDuration, bone.mPosKeys.get(bone.mPosKeys.size()-1).mTime);
	                if( bone.mRotKeys.size() > 0)
	                    nanim.mDuration = std.max( nanim.mDuration, bone.mRotKeys.get(bone.mRotKeys.size()-1).mTime);
	                if( bone.mScaleKeys.size() > 0)
	                    nanim.mDuration = std.max( nanim.mDuration, bone.mScaleKeys.get(bone.mScaleKeys.size()-1).mTime);
	            }
	        }
	    }

	    // store all converted animations in the scene
	    if( newAnims.size() > 0)
	    {
	        pScene.mNumAnimations = (int)newAnims.size();
	        pScene.mAnimations = new aiAnimation [pScene.mNumAnimations];
	        for( int a = 0; a < newAnims.size(); a++)
	            pScene.mAnimations[a] = newAnims.get(a);
	    }
	}

	public void ConvertMaterials( aiScene pScene, ArrayList<Material> pMaterials)
	{
	    // count the non-referrer materials in the array
	    int numNewMaterials = 0;
	    for( int a = 0; a < pMaterials.size(); a++)
	        if( !pMaterials.get(a).mIsReference)
	            numNewMaterials++;

	    // resize the scene's material list to offer enough space for the new materials
	  if( numNewMaterials > 0 )
	  {
	      aiMaterial[] prevMats = pScene.mMaterials;
	      pScene.mMaterials = new aiMaterial[pScene.mNumMaterials + numNewMaterials];
	      if(prevMats!=null)
	      {
	          string.memcpy( pScene.mMaterials, prevMats, pScene.mNumMaterials);
	          prevMats = null;
	      }
	  }

	    // convert all the materials given in the array
	    for( int a = 0; a < pMaterials.size(); a++)
	    {
	        Material oldMat = pMaterials.get(a);
	        if( oldMat.mIsReference)
	    {
	      // find the material it refers to by name, and store its index
	      for(int aa = 0; aa < pScene.mNumMaterials; ++aa )
	      {
	        aiString name = new aiString();
	        pScene.mMaterials[a].Get(material.AI_MATKEY_NAME, name);
	        if( string.strcmp( name, oldMat.mName) == 0 )
	        {
	          oldMat.sceneIndex = aa;
	          break;
	        }
	      }

	      if( oldMat.sceneIndex == Integer.MAX_VALUE )
	      {
	        Logger.getLogger("default").warning( TinyFormatter.format("Could not resolve global material reference \"" ,oldMat.mName , "\"" ));
	        oldMat.sceneIndex = 0;
	      }

	      continue;
	    }

	        aiMaterial mat = new aiMaterial();
	        aiString name = new aiString();
	        name.Set( oldMat.mName);// System.out.println(new String(name.data));
	        mat.AddProperty(name, material.AI_MATKEY_NAME);

	        // Shading model: hardcoded to PHONG, there is no such information in an XFile
	        // FIX (aramis): If the specular exponent is 0, use gouraud shading. This is a bugfix
	        // for some models in the SDK (e.g. good old tiny.x)
	        int shadeMode = oldMat.mSpecularExponent.cast(0.0F).opEquals(0.0F)
	            ? material.aiShadingMode.aiShadingMode_Gouraud.value : material.aiShadingMode.aiShadingMode_Phong.value;

	        mat.AddProperty(new int[]{shadeMode}, 1, material.AI_MATKEY_SHADING_MODEL);
	        // material colours
	    // Unclear: there's no ambient colour, but emissive. What to put for ambient?
	    // Probably nothing at all, let the user select a suitable default.
	        mat.AddProperty(new aiColor3D[]{oldMat.mEmissive}, 1, material.AI_MATKEY_COLOR_EMISSIVE);
	        mat.AddProperty(new aiColor4D[]{oldMat.mDiffuse}, 1, material.AI_MATKEY_COLOR_DIFFUSE);
	        mat.AddProperty(new aiColor3D[]{oldMat.mSpecular}, 1, material.AI_MATKEY_COLOR_SPECULAR);
	        mat.AddProperty(new float[]{oldMat.mSpecularExponent}, 1, material.AI_MATKEY_SHININESS);


	        // texture, if there is one
	        if (1 == oldMat.mTextures.size())
	        {
	            TexEntry otex = oldMat.mTextures.get(oldMat.mTextures.size()-1);
	            if (otex.mName.length()!=0)
	            {
	                // if there is only one texture assume it contains the diffuse color
	                aiString tex = new aiString( otex.mName);
	                if( otex.mIsNormalMap)
	                    mat.AddProperty(tex, material.AI_MATKEY_TEXTURE_NORMALS(0));
	                else
	                    mat.AddProperty(tex, material.AI_MATKEY_TEXTURE_DIFFUSE(0));
	            }
	        }
	        else
	        {
	            // Otherwise ... try to search for typical strings in the
	            // texture's file name like 'bump' or 'diffuse'
	            int iHM = 0,iNM = 0,iDM = 0,iSM = 0,iAM = 0,iEM = 0;
	            for( int b = 0; b < oldMat.mTextures.size(); b++)
	            {
	                TexEntry otex = oldMat.mTextures.get(b);
	                String sz = otex.mName;
	                if (sz.length()==0)continue;


	                // find the file name
	                //size_t iLen = sz.length();
	                int s = sz.lastIndexOf("\\/");
	                if (std.string.npos == s)
	                    s = 0;

	                // cut off the file extension
	                int sExt = sz.lastIndexOf('.');
	                if (std.string.npos != sExt){
	                    sz = sz.substring(0, sExt);
	                }

	                // convert to lower case for easier comparison
	                for( int c = 0; c < sz.length(); c++)
	                    if( ctype.isalpha(sz.charAt(c))) continue; //useless code
	                        //sz[c] = tolower( sz[c]);
	                sz=sz.toLowerCase();

	                // Place texture filename property under the corresponding name
	                aiString tex = new aiString( oldMat.mTextures.get(b).mName);

	                // bump map
	                if (std.string.npos != sz.indexOf("bump", s) || std.string.npos != sz.indexOf("height", s))
	                {
	                    mat.AddProperty(tex, material.AI_MATKEY_TEXTURE_HEIGHT(iHM++));
	                } else
	                if (otex.mIsNormalMap || std.string.npos != sz.indexOf( "normal", s) || std.string.npos != sz.indexOf("nm", s))
	                {
	                    mat.AddProperty(tex, material.AI_MATKEY_TEXTURE_NORMALS(iNM++));
	                } else
	                if (std.string.npos != sz.indexOf( "spec", s) || std.string.npos != sz.indexOf( "glanz", s))
	                {
	                    mat.AddProperty(tex, material.AI_MATKEY_TEXTURE_SPECULAR(iSM++));
	                } else
	                if (std.string.npos != sz.indexOf( "ambi", s) || std.string.npos != sz.indexOf( "env", s))
	                {
	                    mat.AddProperty(tex, material.AI_MATKEY_TEXTURE_AMBIENT(iAM++));
	                } else
	                if (std.string.npos != sz.indexOf( "emissive", s) || std.string.npos != sz.indexOf( "self", s))
	                {
	                    mat.AddProperty(tex, material.AI_MATKEY_TEXTURE_EMISSIVE(iEM++));
	                } else
	                {
	                    // Assume it is a diffuse texture
	                    mat.AddProperty(tex, material.AI_MATKEY_TEXTURE_DIFFUSE(iDM++));
	                }
	            }
	        }

	        pScene.mMaterials[pScene.mNumMaterials] = mat;
	        oldMat.sceneIndex = pScene.mNumMaterials;
	        pScene.mNumMaterials++;
	    }
	}

	private int get_AI_MAX_NUMBER_OF_COLOR_SETS()
	{
		return mesh.AI_MAX_NUMBER_OF_COLOR_SETS;
	}

	private int get_AI_MAX_NUMBER_OF_TEXTURECOORDS()
	{
		return mesh.AI_MAX_NUMBER_OF_TEXTURECOORDS;
	}

}
