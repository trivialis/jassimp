package com.trivialis.java.jassimp.port.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.material;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiFace;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiMesh;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiNode;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.port.include.assimp.types;
import com.trivialis.java.jassimp.port.include.assimp.types.aiReturn;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3t;
import com.trivialis.java.jassimp.util.IPointer;

public class ObjExporter {
	
	public static final String MaterialExt = ".mtl";

	public ObjExporter(IPointer<StringBuilder> _filename, aiScene pScene)
	{
		this.filename=_filename.get().toString();
		this.pScene=pScene;
		
		WriteGeometryFile();
		WriteMaterialFile();
	}
	
	public String GetMaterialLibName() {
		String s = GetMaterialLibFileName();
		int il = s.lastIndexOf("/\\");
		if(il != -1) {
			return s.substring(il+1);
		}
		return s;
	}
	
	public String GetMaterialLibFileName() {
		return filename + MaterialExt;
	}
	
	public StringBuilder mOutput = new StringBuilder();
	public StringBuilder  mOutputMat = new StringBuilder();
	
	public static class FaceVertex {
		public int vp,vn,vt,vc;
	}
	
	public static class Face {
		public char kind;
		public ArrayList<FaceVertex> indices = new ArrayList<>();
	}
	
	public static class MeshInstance {
		public String name, matname;
		public ArrayList<Face> faces = new ArrayList<>();
	}
	
	public void WriteHeader(StringBuilder out) {
		//Skip assimp headers
	}
	
	public void WriteMaterialFile() {
		WriteHeader(mOutputMat);
		
		for(int i = 0; i < pScene.mNumMaterials; ++i) {
	        aiMaterial mat = pScene.mMaterials[i];

	        int illum = 1;
	        mOutputMat.append("newmtl " + GetMaterialName(i)  + endl);

	        aiColor4D c = new aiColor4D();
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_DIFFUSE.x,material.AI_MATKEY_COLOR_DIFFUSE.y,material.AI_MATKEY_COLOR_DIFFUSE.z,c)) {
	            mOutputMat .append("Kd " ).append(c.r ).append(" " ).append(c.g ).append(" " ).append(c.b ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_AMBIENT.x,material.AI_MATKEY_COLOR_AMBIENT.y,material.AI_MATKEY_COLOR_AMBIENT.z,c)) {
	            mOutputMat .append("Ka " ).append(c.r ).append(" " ).append(c.g ).append(" " ).append(c.b ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_SPECULAR.x,material.AI_MATKEY_COLOR_SPECULAR.y,material.AI_MATKEY_COLOR_SPECULAR.z,c)) {
	            mOutputMat .append("Ks " ).append(c.r ).append(" " ).append(c.g ).append(" " ).append(c.b ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_EMISSIVE.x,material.AI_MATKEY_COLOR_EMISSIVE.y,material.AI_MATKEY_COLOR_EMISSIVE.z,c)) {
	            mOutputMat .append("Ke " ).append(c.r ).append(" " ).append(c.g ).append(" " ).append(c.b ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_TRANSPARENT.x,material.AI_MATKEY_COLOR_TRANSPARENT.y,material.AI_MATKEY_COLOR_TRANSPARENT.z,c)) {
	            mOutputMat .append("Tf " ).append(c.r ).append(" " ).append(c.g ).append(" " ).append(c.b ).append(endl);
	        }
	        
	        ai_real o = new ai_real(0F); //Implicit float creation!
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_OPACITY.x,material.AI_MATKEY_OPACITY.y,material.AI_MATKEY_OPACITY.z,o)) {
	            mOutputMat .append("d " ).append(o ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_REFRACTI.x,material.AI_MATKEY_REFRACTI.y,material.AI_MATKEY_REFRACTI.z,o)) {
	            mOutputMat .append("Ni " ).append(o ).append(endl);
	        }

	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_SHININESS.x,material.AI_MATKEY_SHININESS.y,material.AI_MATKEY_SHININESS.z,o) && o.opBigger(new ai_real(0))) {
	            mOutputMat .append("Ns " ).append(o ).append(endl);
	            illum = 2;
	        }

	        mOutputMat .append("illum " ).append(illum ).append(endl);

	        aiString s = new aiString();
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_DIFFUSE(0).x,material.AI_MATKEY_TEXTURE_DIFFUSE(0).y,material.AI_MATKEY_TEXTURE_DIFFUSE(0).z,s)) {
	            mOutputMat .append("map_Kd " ).append(s.data ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_AMBIENT(0).x,material.AI_MATKEY_TEXTURE_AMBIENT(0).y,material.AI_MATKEY_TEXTURE_AMBIENT(0).z,s)) {
	            mOutputMat .append("map_Ka " ).append(s.data ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_SPECULAR(0).x,material.AI_MATKEY_TEXTURE_SPECULAR(0).y,material.AI_MATKEY_TEXTURE_SPECULAR(0).z,s)) {
	            mOutputMat .append("map_Ks " ).append(s.data ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_SHININESS(0).x,material.AI_MATKEY_TEXTURE_SHININESS(0).y,material.AI_MATKEY_TEXTURE_SHININESS(0).z,s)) {
	            mOutputMat .append("map_Ns " ).append(s.data ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_OPACITY(0).x,material.AI_MATKEY_TEXTURE_OPACITY(0).y,material.AI_MATKEY_TEXTURE_OPACITY(0).z,s)) {
	            mOutputMat .append("map_d " ).append(s.data ).append(endl);
	        }
	        if(aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_HEIGHT(0).x,material.AI_MATKEY_TEXTURE_HEIGHT(0).y,material.AI_MATKEY_TEXTURE_HEIGHT(0).z,s) || aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_NORMALS(0).x,material.AI_MATKEY_TEXTURE_NORMALS(0).y,material.AI_MATKEY_TEXTURE_NORMALS(0).z,s)) {
	            // implementations seem to vary here, so write both variants
	            mOutputMat .append("bump " ).append(s.data ).append(endl);
	            mOutputMat .append("map_bump " ).append(s.data ).append(endl);
	        }

	        mOutputMat .append(endl);
	    }
	}
	
	public void WriteGeometryFile() {
	    mOutput .append("mtllib "  ).append(GetMaterialLibName() ).append(endl ).append(endl);

	    // collect mesh geometry
	    aiMatrix4x4 mBase = new aiMatrix4x4();
	    AddNode(pScene.mRootNode, mBase);

	    // write vertex positions with colors, if any
	    vpMap.getVectors( vp );
	    vcMap.getColors( vc );
	    if ( vc.isEmpty() ) {
	        mOutput .append("# " ).append(vp.size() ).append(" vertex positions" ).append(endl);
	        for (aiVector3D v : vp ) {
	            mOutput .append("v  " ).append(v.x ).append(" " ).append(v.y ).append(" " ).append(v.z ).append(endl);
	        }
	    } else {
	        mOutput .append("# " ).append(vp.size() ).append(" vertex positions and colors" ).append(endl);
	        int colIdx = 0;
	        for ( aiVector3D v : vp ) {
	            mOutput .append("v  " ).append(v.x ).append(" " ).append(v.y ).append(" " ).append(v.z ).append(" " ).append(vc.get(colIdx).r ).append(" " ).append(vc.get(colIdx).g ).append(" " ).append(vc.get(colIdx).b ).append(endl);
	            colIdx++;
	        }
	    }
	    mOutput .append(endl);

	    // write uv coordinates
	    vtMap.getVectors(vt);
	    mOutput .append("# " ).append(vt.size() ).append(" UV coordinates" ).append(endl);
	    for(aiVector3D v : vt) {
	        mOutput .append("vt " ).append(v.x ).append(" " ).append(v.y ).append(" " ).append(v.z ).append(endl);
	    }
	    mOutput .append(endl);

	    // write vertex normals
	    vnMap.getVectors(vn);
	    mOutput .append("# " ).append(vn.size() ).append(" vertex normals" ).append(endl);
	    for(aiVector3D v : vn) {
	        mOutput .append("vn " ).append(v.x ).append(" " ).append(v.y ).append(" " ).append(v.z ).append(endl);
	    }
	    mOutput .append(endl);

	    // now write all mesh instances
	    for(MeshInstance m : meshes) {
	        mOutput .append("# Mesh \'" ).append(m.name ).append("\' with " ).append(m.faces.size() ).append(" faces" ).append(endl);
	        if (!m.name.isEmpty()) {
	            mOutput .append("g " ).append(m.name ).append(endl);
	        }
	        mOutput .append("usemtl " ).append(m.matname ).append(endl);

	        for(Face f : m.faces) {
	            mOutput .append(f.kind ).append(' ');
	            for(FaceVertex fv : f.indices) {
	                mOutput .append(' ' ).append(fv.vp);

	                if (f.kind != 'p') {
	                    if ((fv.vt != 0) || (f.kind == 'f')) {
	                        mOutput .append('/');
	                    }
	                    if (fv.vt!=0) {
	                        mOutput .append(fv.vt);
	                    }
	                    if ((f.kind == 'f') && fv.vn!=0) {
	                        mOutput .append('/' ).append(fv.vn);
	                    }
	                }
	            }

	            mOutput .append(endl);
	        }
	        mOutput .append(endl);
	    }
	}
	
	public String GetMaterialName(int index) {
		aiMaterial mat = pScene.mMaterials[index];
		aiString s = new aiString();
		if(types.aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_NAME.x,material.AI_MATKEY_NAME.y,material.AI_MATKEY_NAME.z, s)) {
			return new String(s.data);
		}
		
		return "$Material_"+index;
		
	}
	
	public void AddMesh(aiString name, aiMesh m, aiMatrix4x4 mat) {
		  meshes.add(new MeshInstance());
		    MeshInstance mesh = meshes.get(meshes.size()-1);

		    mesh.name = new String(name.data) + (m.mName.length!=0 ? "_" + new String(m.mName.data) : "");
		    mesh.matname = GetMaterialName(m.mMaterialIndex);

		    while(mesh.faces.size()<m.mNumFaces) mesh.faces.add(new Face());

		    for(int i = 0; i < m.mNumFaces; ++i) {
		        aiFace f = m.mFaces[i];

		        Face face = mesh.faces.get(i);
		        switch (f.mNumIndices) {
		            case 1:
		                face.kind = 'p';
		                break;
		            case 2:
		                face.kind = 'l';
		                break;
		            default:
		                face.kind = 'f';
		        }
		        while(face.indices.size()<f.mNumIndices) face.indices.add(new FaceVertex());

		        for(int a = 0; a < f.mNumIndices; ++a) {
		            int idx = f.mIndices[a];

		            aiVector3D vert = (aiVector3D) aiVector3t.multiply(mat, m.mVertices[idx]);
		            face.indices.get(a).vp = vpMap.getIndex(vert);

		            if (m.mNormals.length>0) {
		                aiVector3D norm = (aiVector3D) aiVector3t.multiply(new matrix3x3.aiMatrix3x3(mat), m.mNormals[idx]);
		                face.indices.get(a).vn = vnMap.getIndex(norm);
		            } else {
		                face.indices.get(a).vn = 0;
		            }

		            if ( null != m.mColors[ 0 ] ) {
		                aiColor4D col4 = m.mColors[ 0 ][ idx ];
		                face.indices.get(a).vc = vcMap.getIndex( col4 );
		            } else {
		                face.indices.get(a).vc = 0;
		            }

		            if ( m.mTextureCoords[ 0 ].length>0 ) {
		                face.indices.get(a).vt = vtMap.getIndex(m.mTextureCoords[0][idx]);
		            } else {
		                face.indices.get(a).vt = 0;
		            }
		        }
		    }
	}
	
	public void AddNode(aiNode nd, aiMatrix4x4 mParent) {
		   aiMatrix4x4 mAbs = (aiMatrix4x4) mParent.opMultiply(nd.mTransformation);

		    for(int i = 0; i < nd.mNumMeshes; ++i) {
		        AddMesh(nd.mName, pScene.mMeshes[nd.mMeshes[i]], mAbs);
		    }

		    for(int i = 0; i < nd.mNumChildren; ++i) {
		        AddNode(nd.mChildren[i], mAbs);
		    }
	}
	
	String filename;
	aiScene pScene;
	
	ArrayList<aiVector3D> vp = new ArrayList<>();
	ArrayList<aiVector3D> vn = new ArrayList<>();
	ArrayList<aiVector3D> vt = new ArrayList<>();
	
	ArrayList<aiColor4D> vc = new ArrayList<>();
	
	public static class aiVectorCompare implements Comparator<aiVector3D>{
		public static boolean isEqual(aiVector3D a, aiVector3D b) {
	          if(a.x.opSmaller(b.x)) return true;
	            if(a.x.opBigger(b.x)) return false;
	            if(a.y.opSmaller(b.y)) return true;
	            if(a.y.opBigger(b.y)) return false;
	            if(a.z.opSmaller(b.z)) return true;
	            return false;
		}

		@Override
		public int compare(aiVector3D a, aiVector3D b)
		{
	          if(a.x.opSmaller(b.x)) return 1;
	            if(a.x.opBigger(b.x)) return -1;
	            if(a.y.opSmaller(b.y)) return 1;
	            if(a.y.opBigger(b.y)) return -1;
	            if(a.z.opSmaller(b.z)) return 1;
	            return -1;
		}
	}
	
    public static class aiColor4Compare implements Comparator<aiColor4D> {
    	public static boolean isEqual( aiColor4D a, aiColor4D b ) {
            if ( a.r.opSmaller(b.r) ) return true;
            if ( a.r.opBigger(b.r) ) return false;
            if ( a.g.opSmaller(b.g) ) return true;
            if ( a.g.opBigger(b.g) ) return false;
            if ( a.b.opSmaller(b.b) ) return true;
            if ( a.b.opBigger(b.b) ) return false;
            if ( a.a.opSmaller(b.a) ) return true;
            if ( a.a.opBigger(b.a) ) return false;
            return false;
        }

		@Override
		public int compare(aiColor4D a, aiColor4D b)
		{
            if ( a.r.opSmaller(b.r) ) return 1;
            if ( a.r.opBigger(b.r) ) return -1;
            if ( a.g.opSmaller(b.g) ) return 1;
            if ( a.g.opBigger(b.g) ) return -1;
            if ( a.b.opSmaller(b.b) ) return 1;
            if ( a.b.opBigger(b.b) ) return -1;
            if ( a.a.opSmaller(b.a) ) return 1;
            if ( a.a.opBigger(b.a) ) return -1;
            return -1;
		}
    };
    
    //Jassimp

    
    public static class vecIndexMap {
        public static class DataType {
        	public aiVector3D a;
        	public int b;
        	public aiVectorCompare c;
        }
    	
        TreeMap<aiVector3D, Integer> _vecMap = new TreeMap<>(new aiVectorCompare());
        
    	int mNextIndex = 1;
    	ArrayList<DataType> vecMap = new ArrayList<>();
    	
    	public int getIndex(aiVector3D vec) {
    		if(_vecMap.containsKey(vec)) return _vecMap.get(vec);
    		_vecMap.put(vec, mNextIndex);
    		int ret = mNextIndex;
    		mNextIndex++;
    		return ret; 
    	}
    	
    	public void getVectors(ArrayList<aiVector3D> vecs) {
    		vecs.clear();
    		for(aiVector3D a : _vecMap.keySet()) {
    			vecs.add(a);
    		}
    	}
    }
    
    public static class colIndexMap {
        public static class DataType {
        	public aiColor4D a;
        	public int b;
        	public aiColor4Compare c;
        }
        
        TreeMap<aiColor4D, Integer> _colMap = new TreeMap<>( new aiColor4Compare());
    	
    	int mNextIndex = 1;
    	ArrayList<DataType> colMap = new ArrayList<>();
    	
    	public int getIndex(aiColor4D col) {
    		if(_colMap.containsKey(col)) return _colMap.get(col);
    		_colMap.put(col, mNextIndex);
    		int ret = mNextIndex;
    		mNextIndex++;
    		return ret; 
    	}
    	
    	public void getColors(ArrayList<aiColor4D> colors) {
    		colors.clear();
    		for(aiColor4D a : _colMap.keySet()) {
    			colors.add(a);
    		}
    	}
    }
    
    vecIndexMap vpMap = new vecIndexMap();
    vecIndexMap vnMap = new vecIndexMap();
    vecIndexMap vtMap = new vecIndexMap();
    colIndexMap vcMap = new colIndexMap();
    ArrayList<MeshInstance> meshes = new ArrayList<>();
	
    String endl = "\n";
}