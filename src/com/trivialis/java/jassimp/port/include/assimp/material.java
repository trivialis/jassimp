package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.code.MaterialSystem;
import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
import com.trivialis.java.jassimp.port.include.assimp.types.aiReturn;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.util.AiRealUtil;
import com.trivialis.java.jassimp.util.ArrayUtil;
import com.trivialis.java.jassimp.util.Tuples.Tuple;
import com.trivialis.java.jassimp.util.serialization.Bytes;
import com.trivialis.java.jassimp.util.string;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class material {

	public enum aiTextureOp
	{
	aiTextureOp_Multiply(0x0),
	aiTextureOp_Add(0x1),
	aiTextureOp_Subtract(0x2),
	aiTextureOp_Divide(0x3),
	aiTextureOp_SmoothAdd(0x4),
	aiTextureOp_SignedAdd(0x5);public int value;

	 private aiTextureOp(int val)
	{ value=val;
	}
	}

	public enum aiTextureMapMode
	{
	aiTextureMapMode_Wrap(0x0),
	aiTextureMapMode_Clamp(0x1),
	aiTextureMapMode_Decal(0x3),
	aiTextureMapMode_Mirror(0x2);public int value;

	 private aiTextureMapMode(int val)
	{ value=val;
	}
	}

	public enum aiTextureMapping
	{
	aiTextureMapping_UV(0x0),
	aiTextureMapping_SPHERE(0x1),
	aiTextureMapping_CYLINDER(0x2),
	aiTextureMapping_BOX(0x3),
	aiTextureMapping_PLANE(0x4),
	aiTextureMapping_OTHER(0x5);public int value;

	 private aiTextureMapping(int val)
	{ value=val;
	}
	}

	public enum aiTextureType
	{
	aiTextureType_NONE(0x0),
	aiTextureType_DIFFUSE(0x1),
	aiTextureType_SPECULAR(0x2),
	aiTextureType_AMBIENT(0x3),
	aiTextureType_EMISSIVE(0x4),
	aiTextureType_HEIGHT(0x5),
	aiTextureType_NORMALS(0x6),
	aiTextureType_SHININESS(0x7),
	aiTextureType_OPACITY(0x8),
	aiTextureType_DISPLACEMENT(0x9),
	aiTextureType_LIGHTMAP(0),
	aiTextureType_REFLECTION(0),
	aiTextureType_UNKNOWN(0);public int value;

	 private aiTextureType(int val)
	{ value=val;
	}
	}

	public enum aiShadingMode
	{
	aiShadingMode_Flat(0x1),
	aiShadingMode_Gouraud(0x2),
	aiShadingMode_Phong(0x3),
	aiShadingMode_Blinn(0x4),
	aiShadingMode_Toon(0x5),
	aiShadingMode_OrenNayar(0x6),
	aiShadingMode_Minnaert(0x7),
	aiShadingMode_CookTorrance(0x8),
	aiShadingMode_NoShading(0x9),
	aiShadingMode_Fresnel(0);

	public int value;

	 private aiShadingMode(int val)
	{ value=val;
	}
	}

	public enum aiTextureFlags
	{
	aiTextureFlags_Invert(0x1),
	aiTextureFlags_UseAlpha(0x2),
	aiTextureFlags_IgnoreAlpha(0x4);public int value;

	 private aiTextureFlags(int val)
	{ value=val;
	}
	}

	public enum aiBlendMode
	{
	aiBlendMode_Default(0x0),
	aiBlendMode_Additive(0x1);public int value;

	 private aiBlendMode(int val)
	{ value=val;
	}
	}

	public static class aiMaterialProperty
	{
	    /** Specifies the name of the property (key)
	     *  Keys are generally case insensitive.
	     */
	    public aiString mKey = new aiString();

	    /** Textures: Specifies their exact usage semantic.
	     * For non-texture properties, this member is always 0
	     * (or, better-said, #aiTextureType_NONE).
	     */
	    public int mSemantic;

	    /** Textures: Specifies the index of the texture.
	     *  For non-texture properties, this member is always 0.
	     */
	    public int mIndex;

	    /** Size of the buffer mData is pointing to, in bytes.
	     *  This value may not be 0.
	     */
	    public int mDataLength;

	    /** Type information for the property.
	     *
	     * Defines the data layout inside the data buffer. This is used
	     * by the library internally to perform debug checks and to
	     * utilize proper type conversions.
	     * (It's probably a hacky solution, but it works.)
	     */
	    public aiPropertyTypeInfo mType;

	    /** Binary buffer to hold the property's value.
	     * The size of the buffer is always mDataLength.
	     */
	    public byte[] mData;



	    public aiMaterialProperty() {
	        mSemantic = 0;
	        mIndex=0;
	        mDataLength= 0 ;
	        mType=aiPropertyTypeInfo.aiPTI_Float;
	        mData=null;

	    }

	    public void destroy()   {
	        mData=null;
	    }

	}

	public enum aiPropertyTypeInfo
	{
	    /** Array of single-precision (32 Bit) floats
	     *
	     *  It is possible to use aiGetMaterialInteger[Array]() (or the C++-API
	     *  aiMaterial::Get()) to query properties stored in floating-point format.
	     *  The material system performs the type conversion automatically.
	    */
	    aiPTI_Float(0x1),

	    /** Array of double-precision (64 Bit) floats
	     *
	     *  It is possible to use aiGetMaterialInteger[Array]() (or the C++-API
	     *  aiMaterial::Get()) to query properties stored in floating-point format.
	     *  The material system performs the type conversion automatically.
	    */
	    aiPTI_Double(0x2),

	    /** The material property is an aiString.
	     *
	     *  Arrays of strings aren't possible, aiGetMaterialString() (or the
	     *  C++-API aiMaterial::Get()) *must* be used to query a string property.
	    */
	    aiPTI_String(0x3),

	    /** Array of (32 Bit) integers
	     *
	     *  It is possible to use aiGetMaterialFloat[Array]() (or the C++-API
	     *  aiMaterial::Get()) to query properties stored in integer format.
	     *  The material system performs the type conversion automatically.
	    */
	    aiPTI_Integer(0x4),


	    /** Simple binary buffer, content undefined. Not convertible to anything.
	    */
	    aiPTI_Buffer(0x5);

	    public int value;

		private aiPropertyTypeInfo(int val) {
	    	value =val;
	    }

	}



	public static Tuple<String,Integer,Integer> AI_MATKEY_NAME = new Tuple<String,Integer,Integer>("?mat.name",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_TWOSIDED = new Tuple<String,Integer,Integer>("$mat.twosided",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_SHADING_MODEL = new Tuple<String,Integer,Integer>("$mat.shadingm",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_ENABLE_WIREFRAME = new Tuple<String,Integer,Integer>("$mat.wireframe",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_BLEND_FUNC = new Tuple<String,Integer,Integer>("$mat.blend",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_OPACITY = new Tuple<String,Integer,Integer>("$mat.opacity",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_BUMPSCALING = new Tuple<String,Integer,Integer>("$mat.bumpscaling",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_SHININESS = new Tuple<String,Integer,Integer>("$mat.shininess",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_REFLECTIVITY = new Tuple<String,Integer,Integer>("$mat.reflectivity",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_SHININESS_STRENGTH = new Tuple<String,Integer,Integer>("$mat.shinpercent",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_REFRACTI = new Tuple<String,Integer,Integer>("$mat.refracti",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_COLOR_DIFFUSE = new Tuple<String,Integer,Integer>("$clr.diffuse",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_COLOR_AMBIENT = new Tuple<String,Integer,Integer>("$clr.ambient",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_COLOR_SPECULAR = new Tuple<String,Integer,Integer>("$clr.specular",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_COLOR_EMISSIVE = new Tuple<String,Integer,Integer>("$clr.emissive",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_COLOR_TRANSPARENT = new Tuple<String,Integer,Integer>("$clr.transparent",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_COLOR_REFLECTIVE = new Tuple<String,Integer,Integer>("$clr.reflective",0,0);
	public static Tuple<String,Integer,Integer> AI_MATKEY_GLOBAL_BACKGROUND_IMAGE = new Tuple<String,Integer,Integer>("?bg.global",0,0);


	public static class aiMaterial {

		public int mNumAllocated;
		public int mNumProperties;
		public List<aiMaterialProperty> mProperties = new ArrayList<>();

		public aiReturn AddBinaryProperty (byte[] pInput,
			    int pSizeInBytes,
			    String pKey,
			    int type,
			    int index,
			    aiPropertyTypeInfo pType
			    )
			{
			    assert (pInput != null);
			    assert (pKey != null);
			    assert (0 != pSizeInBytes);

			    // first search the list whether there is already an entry with this key
			    int iOutIndex = Integer.MAX_VALUE;
			    for (int i = 0; i < mNumProperties;++i)    {
			        aiMaterialProperty prop = mProperties.get(i);

			        if (prop !=null/* just for safety */ && 
			        		string.strcmp( prop.mKey.data, 
			        		pKey.getBytes(StandardCharsets.UTF_8) )==0 &&
			            prop.mSemantic == type && prop.mIndex == index){

			            mProperties.set(i, null);
			            iOutIndex = i;
			        }
			    }

			    // Allocate a new material property
			    aiMaterialProperty pcNew = new aiMaterialProperty();

			    // .. and fill it
			    pcNew.mType = pType;
			    pcNew.mSemantic = type;
			    pcNew.mIndex = index;

			    pcNew.mDataLength = pSizeInBytes;
			    pcNew.mData = new byte[pSizeInBytes];
			    string.memcpy (pcNew.mData,pInput,pSizeInBytes);

			    //pcNew.mKey.length = string.strlen(pKey.toCharArray());
			    assert (types.MAXLEN > pcNew.mKey.length);
			    pcNew.mKey.Set(pKey);

			    if (Integer.MAX_VALUE != iOutIndex)  {
			        mProperties.set(iOutIndex, pcNew);
			        return aiReturn.SUCCESS;
			    }

			    // resize the array ... double the storage allocated
			    if (mNumProperties == mNumAllocated)    {
			        int iOld = mNumAllocated;
			        mNumAllocated *= 2;

			        aiMaterialProperty[] ppTemp;
			        try {
			            ppTemp = new aiMaterialProperty[mNumAllocated];
			        } catch (Exception e) {
			            pcNew=null;
			            return aiReturn.OUTOFMEMORY;
			        }

			        // just copy all items over; then replace the old array
			        string.memcpy (ppTemp,mProperties.toArray(new aiMaterialProperty[0]),iOld);

			        mProperties = null;
			        mProperties = ArrayUtil.toList(ppTemp);
			    }
			    // push back ...
			    mProperties.add(pcNew);mNumProperties++;
			    return aiReturn.SUCCESS;
			}

		public aiReturn AddProperty(ai_real[] ai_reals, int pNumValues, String pKey, Integer type, Integer index)
		{
			if(ai_reals[0].getValue() instanceof Double) {
				return AddProperty(AiRealUtil.convertToDoubles(ai_reals), pNumValues, pKey, type, index);
			} else if(ai_reals[0].getValue() instanceof Float) {
				return AddProperty(AiRealUtil.convertToFloats(ai_reals), pNumValues, pKey, type, index);
			} else {
				throw new RuntimeException();
			}
		}

		public aiReturn AddProperty(double[] doubles, int pNumValues, String pKey, Integer type, Integer index)
		{
			return AddBinaryProperty(Bytes.serialize(doubles), pNumValues*8, pKey, type, index, aiPropertyTypeInfo.aiPTI_Double);
		}

		public aiReturn AddProperty(float[] floats, int pNumValues, String pKey, Integer type, Integer index)
		{
			return AddBinaryProperty(Bytes.serialize(floats), pNumValues*4, pKey, type, index, aiPropertyTypeInfo.aiPTI_Float);
		}

		public aiReturn AddProperty(aiColor4D[] aiColor4Ds, int pNumValues, String pKey, Integer type, Integer index)
		{
			return AddBinaryProperty(Bytes.serialize(aiColor4Ds), pNumValues*4*4, pKey, type, index, aiPropertyTypeInfo.aiPTI_Float);
		}

		public aiReturn AddProperty(aiColor3D[] clr, int pNumValues, String pKey, Integer type, Integer index)
		{
			return AddBinaryProperty(Bytes.serialize(clr), pNumValues*4*3, pKey, type, index, aiPropertyTypeInfo.aiPTI_Float); //Could be Double or Float we do not know. lest assume Float for now. as Assimp does this as well.
		}

		public aiReturn AddProperty(int[] pInput, int pNumValues, String pKey, int type, int index)
		{
			return AddBinaryProperty(Bytes.serialize(pInput), pNumValues*4, pKey, type, index, aiPropertyTypeInfo.aiPTI_Integer);
		}

		public aiReturn Get(String pKey, int type, int idx, aiString pOut)
		{
			return MaterialSystem.aiGetMaterialString(this, pKey, type, idx, pOut);
		}
                
                public aiReturn Get(Tuple<String, Integer, Integer> a, aiString pOut)
		{
			return MaterialSystem.aiGetMaterialString(this, a.x, a.y, a.z, pOut);
		}

		public aiReturn AddProperty(aiString tex, Tuple<String, Integer, Integer> t)
		{
			return AddProperty(tex, t.x, t.y, t.z);
		}
                
                public aiReturn AddProperty(int[] ints, int i, Tuple<String, Integer, Integer> t) {
                    return AddProperty(ints, i, t.x, t.y, t.z);
                }
                
                public aiReturn AddProperty(aiColor3D[] clr, int pNumValues, Tuple<String, Integer, Integer> t) {
                    return AddProperty(clr, pNumValues, t.x, t.y, t.z);
                }
                
                     public aiReturn AddProperty(aiColor4D[] aiColor4D, int i, Tuple<String, Integer, Integer> t) {
                         return AddProperty(aiColor4D, i, t.x, t.y, t.z);
        }

        public  aiReturn AddProperty(ai_real[] ai_real, int i, Tuple<String, Integer, Integer> t) {
            return AddProperty(ai_real, i, t.x, t.y, t.z);
        }
                        

		public aiReturn AddProperty ( aiString pInput,
			     String pKey,
			    int type,
			    int index)
			{
			    // We don't want to add the whole buffer .. write a 32 bit length
			    // prefix followed by the zero-terminated UTF8 string.
			    // (HACK) I don't want to break the ABI now, but we definitely
			    // ought to change aiString.mLength to uint32_t one day.
			    if (Integer.BYTES == 8) { //How to do this?
			        aiString copy = pInput;
			        int[] s = new int[]{copy.length, pInput.length};
			        //s[1] = static_cast<uint32_t>(pInput.length);

			        return AddBinaryProperty(Bytes.serialize(s[1]),
			            pInput.length+1+4,
			            pKey,
			            type,
			            index,
			            aiPropertyTypeInfo.aiPTI_String);
			    }
			    assert(Integer.BYTES==4);
			    return AddBinaryProperty(ByteBuffer.allocate(pInput.length+4+1).putInt(pInput.length).put(pInput.data).array(),
			    		pInput.length+4, //+1 already done in aiString?
			        pKey,
			        type,
			        index,
			        aiPropertyTypeInfo.aiPTI_String);
			}

		public aiReturn Get(String pKey, int type, int idx, aiColor4D c)
		{
			return MaterialSystem.aiGetMaterialColor(this, pKey, type, idx, c);
		}

		public aiReturn Get(String x, Integer y, Integer z, ai_real o)
		{
			return MaterialSystem.aiGetMaterialFloat(this, x, y, z, o);
		}

        public aiReturn Get(Tuple<String, Integer, Integer> a, aiColor4D c) {
            return MaterialSystem.aiGetMaterialColor(this, a.x,a.y,a.z, c);
        }
        
          public aiReturn Get(Tuple<String, Integer, Integer> a, ai_real o) {
            return MaterialSystem.aiGetMaterialFloat(this, a.x,a.y,a.z, o);
        }

   

     




	}

	public static String _AI_MATKEY_TEXTURE_BASE = "$tex.file";

	public static Tuple<String, Integer, Integer> AI_MATKEY_TEXTURE(int type, int N) {
		return new Tuple<String, Integer, Integer>(_AI_MATKEY_TEXTURE_BASE, type, N);
	}

	public static Tuple<String, Integer, Integer> AI_MATKEY_TEXTURE_NORMALS(int N) {
		return AI_MATKEY_TEXTURE(aiTextureType.aiTextureType_NORMALS.value, N);
	}

	public static Tuple<String, Integer, Integer> AI_MATKEY_TEXTURE_DIFFUSE(int N)
	{
		return AI_MATKEY_TEXTURE(aiTextureType.aiTextureType_DIFFUSE.value, N);
	}

	public static Tuple<String, Integer, Integer> AI_MATKEY_TEXTURE_HEIGHT(int N) {
		return AI_MATKEY_TEXTURE(aiTextureType.aiTextureType_HEIGHT.value, N);
	}

	public static Tuple<String, Integer, Integer> AI_MATKEY_TEXTURE_SPECULAR(int N) {
		return AI_MATKEY_TEXTURE(aiTextureType.aiTextureType_SPECULAR.value, N);
	}

	public static Tuple<String, Integer, Integer> AI_MATKEY_TEXTURE_AMBIENT(int N) {
		return AI_MATKEY_TEXTURE(aiTextureType.aiTextureType_AMBIENT.value, N);
	}

	public static Tuple<String, Integer, Integer> AI_MATKEY_TEXTURE_EMISSIVE(int N) {
		return AI_MATKEY_TEXTURE(aiTextureType.aiTextureType_EMISSIVE.value, N);
	}
	
	public static Tuple<String, Integer, Integer> AI_MATKEY_TEXTURE_SHININESS(int N) {
		return AI_MATKEY_TEXTURE(aiTextureType.aiTextureType_SHININESS.value, N);
	}
	
	public static Tuple<String, Integer, Integer> AI_MATKEY_TEXTURE_OPACITY(int N) {
		return AI_MATKEY_TEXTURE(aiTextureType.aiTextureType_OPACITY.value, N);
	}



}
