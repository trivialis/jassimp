package com.trivialis.java.jassimp.port.code;

import java.util.ArrayList;

import com.trivialis.java.jassimp.port.code.ConvertToLHProcess.FlipWindingOrderProcess;
import com.trivialis.java.jassimp.port.code.ConvertToLHProcess.MakeLeftHandedProcess;
import com.trivialis.java.jassimp.port.code.XFileHelper.Bone;
import com.trivialis.java.jassimp.port.code.XFileHelper.Face;
import com.trivialis.java.jassimp.port.code.XFileHelper.Material;
import com.trivialis.java.jassimp.port.code.XFileHelper.Mesh;
import com.trivialis.java.jassimp.port.code.XFileHelper.Node;
import com.trivialis.java.jassimp.port.code.XFileHelper.Scene;
import com.trivialis.java.jassimp.port.include.assimp.IOStream;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.material;
import com.trivialis.java.jassimp.port.include.assimp.scene;
import com.trivialis.java.jassimp.port.include.assimp.mesh;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiVertexWeight;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiBone;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiFace;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiMesh;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiNode;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
import com.trivialis.java.jassimp.port.include.assimp.vector2.aiVector2D;
import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.std;
import com.trivialis.java.jassimp.util.string;

public class XFileImporter extends BaseImporter {

	public ArrayList<Character> mBuffer
	= new ArrayList<Character>()
	;

	public XFileImporter() {

	}

	public void destroy() {

	}

	@Override
	public void InternReadFile(String pFile, ScopeGuard<aiScene> pScene, IPointer<FileSystemFilter> pIOHandler)
	{
		IPointer<IOStream> file = Pointer.valueOf(pIOHandler.get().open(pFile));
		if(file.get() == null)
			throw new Exceptional.DeadlyImportError("Failed to open file " + pFile + ".");

		int fileSize = file.get().FileSize();
		if(fileSize < 16)
			throw new Exceptional.DeadlyImportError("XFile is too small.");

		mBuffer.ensureCapacity(fileSize + 1);
		file.get().Read(mBuffer, 1, fileSize);
		ConvertToUTF8(mBuffer);

		XFileParser parser = new XFileParser(mBuffer.toArray(new Character[0]));

		CreateDataRepresentationFromImport(pScene.get(), parser.GetImportedData()); //I ignore scopeguard for now.

		if(pScene.get().mRootNode==null)
			throw new Exceptional.DeadlyImportError("XFile is ill-formatted - no content imported.");



	}

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

			aiMaterial mat = new scene.aiMaterial();
			int shadeMode = (int) material.aiShadingMode.aiShadingMode_Gouraud.value;
			mat.AddProperty(Pointer.valueOf(shadeMode), 1, material.AI_MATKEY_SHADING_MODEL);

			int specExp = 1;

			aiColor3D clr = new aiColor3D(new ai_real(0),new ai_real(0), new ai_real(0));
	        mat.AddProperty(Pointer.valueOf(clr), 1, material.AI_MATKEY_COLOR_EMISSIVE);
	        mat.AddProperty(Pointer.valueOf(clr), 1, material.AI_MATKEY_COLOR_SPECULAR);

	        clr = new aiColor3D(new ai_real(0.5F), new ai_real(0.5F), new ai_real(0.5F));
	        mat.AddProperty(Pointer.valueOf(clr), 1, material.AI_MATKEY_COLOR_DIFFUSE);
	        mat.AddProperty(Pointer.valueOf(specExp), 1, material.AI_MATKEY_SHININESS);

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
	    node.mName.length = pNode.mName.length();
	    node.mParent = pParent;
	    string.memcpy( node.mName.data, pNode.mName.toCharArray(), pNode.mName.length());
	    node.mName.data[node.mName.length] = 0;
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
	            mesh.mFaces = new scene.aiFace[mesh.mNumFaces];

	            // name
	            mesh.mName.Set(sourceMesh.mName);

	            // normals?
	            if( sourceMesh.mNormals.size() > 0)
	                mesh.mNormals = new aiVector3D[numVertices];
	            // texture coords
	            for( int c = 0; c < get_AI_MAX_NUMBER_OF_TEXTURECOORDS(); c++)
	            {
	                if( sourceMesh.mTexCoords[c].size() > 0)
	                    mesh.mTextureCoords[c] = new aiVector3D[numVertices];
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
	            orgPoints.ensureCapacity(numVertices);

	            for(int c = 0; c < faces.size(); c++)
	            {
	                int f = faces.get(c); // index of the source face
	                Face pf = sourceMesh.mPosFaces.get(f); // position source face

	                // create face. either triangle or triangle fan depending on the index count
	                aiFace df = mesh.mFaces[c]; // destination face
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
	                            mesh.mTextureCoords[e][newIndex] = new aiVector3D( tex.x, new ai_real(1.0f).opSubtract(tex.y), new ai_real(0.0f));
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
	                ArrayList<ai_real> oldWeights = new ArrayList<ai_real>(sourceMesh.mPositions.size());
	                for(int d = 0; d < obone.mWeights.size(); d++)
	                    oldWeights.set(obone.mWeights.get(d).mVertex, obone.mWeights.get(d).mWeight);

	                // collect all vertex weights that influence a vertex in the new mesh
	                ArrayList<aiVertexWeight> newWeights = new ArrayList<aiVertexWeight>();
	                newWeights.ensureCapacity( numVertices);
	                for(int d = 0; d < orgPoints.size(); d++)
	                {
	                    // does the new vertex stem from an old vertex which was influenced by this bone?
	                    ai_real w = oldWeights.get(orgPoints.get(d));
	                    if( w.opBigger(new ai_real(0.0)))
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
	    std.vector<aiAnimation*> newAnims;

	    for( unsigned int a = 0; a < pData.mAnims.size(); a++)
	    {
	        XFile.Animation* anim = pData.mAnims[a];
	        // some exporters mock me with empty animation tags.
	        if( anim.mAnims.size() == 0)
	            continue;

	        // create a new animation to hold the data
	        aiAnimation* nanim = new aiAnimation;
	        newAnims.push_back( nanim);
	        nanim.mName.Set( anim.mName);
	        // duration will be determined by the maximum length
	        nanim.mDuration = 0;
	        nanim.mTicksPerSecond = pData.mAnimTicksPerSecond;
	        nanim.mNumChannels = (unsigned int)anim.mAnims.size();
	        nanim.mChannels = new aiNodeAnim*[nanim.mNumChannels];

	        for( unsigned int b = 0; b < anim.mAnims.size(); b++)
	        {
	            XFile.AnimBone* bone = anim.mAnims[b];
	            aiNodeAnim* nbone = new aiNodeAnim;
	            nbone.mNodeName.Set( bone.mBoneName);
	            nanim.mChannels[b] = nbone;

	            // keyframes are given as combined transformation matrix keys
	            if( bone.mTrafoKeys.size() > 0)
	            {
	                nbone.mNumPositionKeys = (unsigned int)bone.mTrafoKeys.size();
	                nbone.mPositionKeys = new aiVectorKey[nbone.mNumPositionKeys];
	                nbone.mNumRotationKeys = (unsigned int)bone.mTrafoKeys.size();
	                nbone.mRotationKeys = new aiQuatKey[nbone.mNumRotationKeys];
	                nbone.mNumScalingKeys = (unsigned int)bone.mTrafoKeys.size();
	                nbone.mScalingKeys = new aiVectorKey[nbone.mNumScalingKeys];

	                for( unsigned int c = 0; c < bone.mTrafoKeys.size(); c++)
	                {
	                    // deconstruct each matrix into separate position, rotation and scaling
	                    double time = bone.mTrafoKeys[c].mTime;
	                    aiMatrix4x4 trafo = bone.mTrafoKeys[c].mMatrix;

	                    // extract position
	                    aiVector3D pos( trafo.a4, trafo.b4, trafo.c4);

	                    nbone.mPositionKeys[c].mTime = time;
	                    nbone.mPositionKeys[c].mValue = pos;

	                    // extract scaling
	                    aiVector3D scale;
	                    scale.x = aiVector3D( trafo.a1, trafo.b1, trafo.c1).Length();
	                    scale.y = aiVector3D( trafo.a2, trafo.b2, trafo.c2).Length();
	                    scale.z = aiVector3D( trafo.a3, trafo.b3, trafo.c3).Length();
	                    nbone.mScalingKeys[c].mTime = time;
	                    nbone.mScalingKeys[c].mValue = scale;

	                    // reconstruct rotation matrix without scaling
	                    aiMatrix3x3 rotmat(
	                        trafo.a1 / scale.x, trafo.a2 / scale.y, trafo.a3 / scale.z,
	                        trafo.b1 / scale.x, trafo.b2 / scale.y, trafo.b3 / scale.z,
	                        trafo.c1 / scale.x, trafo.c2 / scale.y, trafo.c3 / scale.z);

	                    // and convert it into a quaternion
	                    nbone.mRotationKeys[c].mTime = time;
	                    nbone.mRotationKeys[c].mValue = aiQuaternion( rotmat);
	                }

	                // longest lasting key sequence determines duration
	                nanim.mDuration = std.max( nanim.mDuration, bone.mTrafoKeys.back().mTime);
	            } else
	            {
	                // separate key sequences for position, rotation, scaling
	                nbone.mNumPositionKeys = (unsigned int)bone.mPosKeys.size();
	                nbone.mPositionKeys = new aiVectorKey[nbone.mNumPositionKeys];
	                for( unsigned int c = 0; c < nbone.mNumPositionKeys; c++)
	                {
	                    aiVector3D pos = bone.mPosKeys[c].mValue;

	                    nbone.mPositionKeys[c].mTime = bone.mPosKeys[c].mTime;
	                    nbone.mPositionKeys[c].mValue = pos;
	                }

	                // rotation
	                nbone.mNumRotationKeys = (unsigned int)bone.mRotKeys.size();
	                nbone.mRotationKeys = new aiQuatKey[nbone.mNumRotationKeys];
	                for( unsigned int c = 0; c < nbone.mNumRotationKeys; c++)
	                {
	                    aiMatrix3x3 rotmat = bone.mRotKeys[c].mValue.GetMatrix();

	                    nbone.mRotationKeys[c].mTime = bone.mRotKeys[c].mTime;
	                    nbone.mRotationKeys[c].mValue = aiQuaternion( rotmat);
	                    nbone.mRotationKeys[c].mValue.w *= -1.0f; // needs quat inversion
	                }

	                // scaling
	                nbone.mNumScalingKeys = (unsigned int)bone.mScaleKeys.size();
	                nbone.mScalingKeys = new aiVectorKey[nbone.mNumScalingKeys];
	                for( unsigned int c = 0; c < nbone.mNumScalingKeys; c++)
	                    nbone.mScalingKeys[c] = bone.mScaleKeys[c];

	                // longest lasting key sequence determines duration
	                if( bone.mPosKeys.size() > 0)
	                    nanim.mDuration = std.max( nanim.mDuration, bone.mPosKeys.back().mTime);
	                if( bone.mRotKeys.size() > 0)
	                    nanim.mDuration = std.max( nanim.mDuration, bone.mRotKeys.back().mTime);
	                if( bone.mScaleKeys.size() > 0)
	                    nanim.mDuration = std.max( nanim.mDuration, bone.mScaleKeys.back().mTime);
	            }
	        }
	    }

	    // store all converted animations in the scene
	    if( newAnims.size() > 0)
	    {
	        pScene.mNumAnimations = (unsigned int)newAnims.size();
	        pScene.mAnimations = new aiAnimation* [pScene.mNumAnimations];
	        for( unsigned int a = 0; a < newAnims.size(); a++)
	            pScene.mAnimations[a] = newAnims[a];
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

	private void CreateAnimations(aiScene pScene, Scene pData)
	{
		// TODO Auto-generated method stub

	}

	private void ConvertMaterials(aiScene pScene, ArrayList<Material> mGlobalMaterials)
	{
		// TODO Auto-generated method stub

	}



}
