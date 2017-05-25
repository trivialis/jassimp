package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.material;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.matrix3x3;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiFace;
import com.trivialis.java.jassimp.port.include.assimp.mesh.aiMesh;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiNode;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.port.include.assimp.types;
import com.trivialis.java.jassimp.port.include.assimp.types.aiReturn;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3D;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3t;
import com.trivialis.java.jassimp.util.ArrayUtil;
import com.trivialis.java.jassimp.util.IPointer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ObjExporter {

    public static final String MaterialExt = ".mtl";

    public ObjExporter(IPointer<StringBuilder> _filename, aiScene pScene) {
        this.filename = _filename.get().toString();
        this.pScene = pScene;

        WriteGeometryFile();
        WriteMaterialFile();
    }

    public String GetMaterialLibName() {
        String s = GetMaterialLibFileName();
        int il = s.lastIndexOf("/\\");
        if (il != -1) {
            return s.substring(il + 1);
        }
        return s;
    }

    public String GetMaterialLibFileName() {
        return filename + MaterialExt;
    }

    public StringBuilder mOutput = new StringBuilder();
    public StringBuilder mOutputMat = new StringBuilder();

    public static class FaceVertex {

        public int vp, vn, vt, vc;
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

        for (int i = 0; i < pScene.mNumMaterials; ++i) {
            aiMaterial mat = pScene.mMaterials[i];

            int illum = 1;
            mOutputMat.append("newmtl ").append(GetMaterialName(i)).append(endl);

            aiColor4D c = new aiColor4D();
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_DIFFUSE, c)) {
                mOutputMat.append("Kd ").append(c.r).append(" ").append(c.g).append(" ").append(c.b).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_AMBIENT, c)) {
                mOutputMat.append("Ka ").append(c.r).append(" ").append(c.g).append(" ").append(c.b).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_SPECULAR, c)) {
                mOutputMat.append("Ks ").append(c.r).append(" ").append(c.g).append(" ").append(c.b).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_EMISSIVE, c)) {
                mOutputMat.append("Ke ").append(c.r).append(" ").append(c.g).append(" ").append(c.b).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_COLOR_TRANSPARENT, c)) {
                mOutputMat.append("Tf ").append(c.r).append(" ").append(c.g).append(" ").append(c.b).append(endl);
            }

            ai_real o = new ai_real(0F); //Implicit float creation!
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_OPACITY, o)) {
                mOutputMat.append("d ").append(o).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_REFRACTI, o)) {
                mOutputMat.append("Ni ").append(o).append(endl);
            }

            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_SHININESS, o) && o.opBigger(new ai_real(0.0f))) {
                mOutputMat.append("Ns ").append(o).append(endl);
                illum = 2;
            }

            mOutputMat.append("illum ").append(illum).append(endl);

            aiString s = new aiString();
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_DIFFUSE(0), s)) {
                mOutputMat.append("map_Kd ").append(new String(s.data)).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_AMBIENT(0), s)) {
                mOutputMat.append("map_Ka ").append(new String(s.data)).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_SPECULAR(0), s)) {
                mOutputMat.append("map_Ks ").append(new String(s.data)).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_SHININESS(0), s)) {
                mOutputMat.append("map_Ns ").append(new String(s.data)).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_OPACITY(0), s)) {
                mOutputMat.append("map_d ").append(new String(s.data)).append(endl);
            }
            if (aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_HEIGHT(0), s)
                    || aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_TEXTURE_NORMALS(0), s)) {
                // implementations seem to vary here, so write both variants
                mOutputMat.append("bump ").append(new String(s.data)).append(endl);
                mOutputMat.append("map_bump ").append(new String(s.data)).append(endl);
            }

            mOutputMat.append(endl);
        }
    }

    public void WriteGeometryFile() {
        mOutput.append("mtllib ").append(GetMaterialLibName()).append(endl).append(endl);

        // collect mesh geometry
        aiMatrix4x4 mBase = new aiMatrix4x4();
        AddNode(pScene.mRootNode, mBase);

        // write vertex positions with colors, if any
        vpMap.getVectors(vp);// System.out.println(vp.size());
        vcMap.getColors(vc);
        if (vc.isEmpty()) {
            mOutput.append("# ").append(vp.size()).append(" vertex positions").append(endl);
            for (aiVector3D v : vp) {
                mOutput.append("v  ").append(v.x).append(" ").append(v.y).append(" ").append(v.z).append(endl);
            }
        } else {
            mOutput.append("# ").append(vp.size()).append(" vertex positions and colors").append(endl);
            int colIdx = 0;
            for (aiVector3D v : vp) {
                mOutput.append("v  ").append(v.x).append(" ").append(v.y).append(" ").append(v.z).append(" ").append(vc.get(colIdx).r).append(" ").append(vc.get(colIdx).g).append(" ").append(vc.get(colIdx).b).append(endl);
                colIdx++;
            }
        }
        mOutput.append(endl);

        // write uv coordinates
        vtMap.getVectors(vt); //System.out.println(vt.size());
        mOutput.append("# ").append(vt.size()).append(" UV coordinates").append(endl);
        for (aiVector3D v : vt) {
            mOutput.append("vt ").append(v.x).append(" ").append(v.y).append(" ").append(v.z).append(endl);
        }
        mOutput.append(endl);

        // write vertex normals
        vnMap.getVectors(vn);
        mOutput.append("# ").append(vn.size()).append(" vertex normals").append(endl);
        for (aiVector3D v : vn) {
            mOutput.append("vn ").append(v.x).append(" ").append(v.y).append(" ").append(v.z).append(endl);
        }
        mOutput.append(endl);

        // now write all mesh instances
        for (MeshInstance m : meshes) {
            mOutput.append("# Mesh \'").append(m.name).append("\' with ").append(m.faces.size()).append(" faces").append(endl);
            if (!m.name.isEmpty()) {
                mOutput.append("g ").append(m.name).append(endl);
            }
            mOutput.append("usemtl ").append(m.matname).append(endl);

            for (Face f : m.faces) {
                mOutput.append(f.kind).append(' ');
                for (FaceVertex fv : f.indices) {
                    mOutput.append(' ').append(fv.vp);

                    if (f.kind != 'p') {
                        if ((fv.vt != 0) || (f.kind == 'f')) {
                            mOutput.append('/');
                        }
                        if (fv.vt != 0) {
                            mOutput.append(fv.vt);
                        }
                        if ((f.kind == 'f') && fv.vn != 0) {
                            mOutput.append('/').append(fv.vn);
                        }
                    }
                }

                mOutput.append(endl);
            }
            mOutput.append(endl);
        }
    }

    public String GetMaterialName(int index) {
        aiMaterial mat = pScene.mMaterials[index];
        aiString s = new aiString();
        if (types.aiReturn.SUCCESS == mat.Get(material.AI_MATKEY_NAME, s)) {
            return new String(s.data);
        }

        return "$Material_" + index;

    }

    public void AddMesh(aiString name, aiMesh m, aiMatrix4x4 mat) {
        meshes.add(new MeshInstance());
        MeshInstance mesh = meshes.get(meshes.size() - 1);

        mesh.name = new String(name.data) + (m.mName.length != 0 ? "_" + new String(m.mName.data) : "");
        mesh.matname = GetMaterialName(m.mMaterialIndex);

        while (mesh.faces.size() < m.mNumFaces) {
            mesh.faces.add(new Face());
        }

        for (int i = 0; i < m.mNumFaces; ++i) {
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
            while (face.indices.size() < f.mNumIndices) {
                face.indices.add(new FaceVertex());//System.out.println(f.mNumIndices);
            }
            for (int a = 0; a < f.mNumIndices; ++a) {
                int idx = f.mIndices[a];//System.out.println(idx);

                aiVector3D vert = (aiVector3D) aiVector3t.multiply(mat, m.mVertices[idx]);//System.out.println(mat.a1);//System.out.println(m.mVertices[idx].x);//System.out.println(vert.x);
                face.indices.get(a).vp = vpMap.getIndex(vert);//System.out.println(face.indices.get(a).vp);

                if (m.mNormals.length > 0) {
                    aiVector3D norm = (aiVector3D) aiVector3t.multiply(new matrix3x3.aiMatrix3x3(mat), m.mNormals[idx]);
                    face.indices.get(a).vn = vnMap.getIndex(norm);
                } else {
                    face.indices.get(a).vn = 0;
                }

                if (null != m.mColors[0]) {
                    aiColor4D col4 = m.mColors[0][idx];
                    face.indices.get(a).vc = vcMap.getIndex(col4);
                } else {
                    face.indices.get(a).vc = 0;
                }
                //   System.out.println(m.mTextureCoords[ 0 ].length);// for(aiVector3t aa : m.mTextureCoords[0]) System.out.println(aa.x.getValue()+" "+aa.y.getValue()+" "+aa.z.getValue());
                if (m.mTextureCoords[0].length > 0) {
                    face.indices.get(a).vt = vtMap.getIndex(m.mTextureCoords[0][idx]); //System.out.println(face.indices.get(a).vt); System.out.println(m.mTextureCoords[0][idx].x.getValue());
                } else {
                    face.indices.get(a).vt = 0;
                }
            }
        }
    }

    public void AddNode(aiNode nd, aiMatrix4x4 mParent) {
        aiMatrix4x4 mAbs = (aiMatrix4x4) mParent.opMultiply(nd.mTransformation);
        //System.out.println("Addnode: " + mAbs.a1);//System.out.println("Mutipl: " + nd.mTransformation.a1);
        for (int i = 0; i < nd.mNumMeshes; ++i) {
            AddMesh(nd.mName, pScene.mMeshes[nd.mMeshes[i]], mAbs);
        }
        //System.out.println(nd.mNumChildren);
        for (int i = 0; i < nd.mNumChildren; ++i) {
            AddNode(nd.mChildren[i], mAbs);
        }
    }

    String filename;
    aiScene pScene;

    ArrayList<aiVector3D> vp = new ArrayList<>();
    ArrayList<aiVector3D> vn = new ArrayList<>();
    ArrayList<aiVector3D> vt = new ArrayList<>();

    ArrayList<aiColor4D> vc = new ArrayList<>();

    public static class aiVectorCompare implements Comparator<aiVector3D> {
        //isLess

        public static boolean isLess(aiVector3D a, aiVector3D b) {
            if (a.x.opSmaller(b.x)) {
                return true;
            }
            if (a.x.opBigger(b.x)) {
                return false;
            }
            if (a.y.opSmaller(b.y)) {
                return true;
            }
            if (a.y.opBigger(b.y)) {
                return false;
            }
            if (a.z.opSmaller(b.z)) {
                return true;
            }
            if (a.z.opBigger(b.z)) {
                return false;
            }
            return false;
        }

        @Override
        public int compare(aiVector3D a, aiVector3D b) {
            if (isLess(a, b)) {
                return -1;
            } else if (isLess(b, a)) {
                return 1;
            }
            return 0;
        }
    }

    public static class aiColor4Compare implements Comparator<aiColor4D> {

        public static boolean isLess(aiColor4D a, aiColor4D b) {
            if (a.r.opSmaller(b.r)) {
                return true;
            }
            if (a.r.opBigger(b.r)) {
                return false;
            }
            if (a.g.opSmaller(b.g)) {
                return true;
            }
            if (a.g.opBigger(b.g)) {
                return false;
            }
            if (a.b.opSmaller(b.b)) {
                return true;
            }
            if (a.b.opBigger(b.b)) {
                return false;
            }
            if (a.a.opSmaller(b.a)) {
                return true;
            }
            if (a.a.opBigger(b.a)) {
                return false;
            }
            return false;
        }

        @Override
        public int compare(aiColor4D a, aiColor4D b) {
            if (isLess(a, b)) {
                return -1;
            } else if (isLess(b, a)) {
                return 1;
            }
            return 0;
        }
    };

    //Jassimp
    public static class vecIndexMap {

        TreeMap<aiVector3D, Integer> _vecMap = new TreeMap<>(
                new aiVectorCompare()
        );

        int mNextIndex = 1;

        public int getIndex(aiVector3D vec) {
            if (_vecMap.containsKey(vec)) {
                return _vecMap.get(vec);
            }
            _vecMap.put(vec, mNextIndex);
            int ret = mNextIndex;
            mNextIndex++;
            return ret;
        }

        public void getVectors(ArrayList<aiVector3D> vecs) {
            ArrayUtil.Generator.ensureSize(vecs, _vecMap.size()); //System.out.println(_vecMap.keySet().size());
            for (Map.Entry<aiVector3D, Integer> s : _vecMap.entrySet()) {
                vecs.set(s.getValue() - 1, s.getKey());
            }
        }
    }

    public static class colIndexMap {

        TreeMap<aiColor4D, Integer> _colMap = new TreeMap<>(
                new aiColor4Compare()
        );

        int mNextIndex = 1;

        public int getIndex(aiColor4D col) {
            if (_colMap.containsKey(col)) {
                return _colMap.get(col);
            }
            _colMap.put(col, mNextIndex);
            int ret = mNextIndex;
            mNextIndex++;
            return ret;
        }

        public void getColors(ArrayList<aiColor4D> colors) {
            ArrayUtil.Generator.ensureSize(colors, _colMap.size());
            for (Map.Entry<aiColor4D, Integer> s : _colMap.entrySet()) {
                colors.set(s.getValue() - 1, s.getKey());
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
