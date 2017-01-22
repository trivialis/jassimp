package com.trivialis.java.jassimp.port.include.assimp;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import com.trivialis.java.jassimp.port.code.MaterialSystem;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterialProperty;
import com.trivialis.java.jassimp.port.include.assimp.types.aiColor3D;
import com.trivialis.java.jassimp.port.include.assimp.types.aiReturn;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Tuples.Tuple;
import com.trivialis.java.jassimp.util.std;
import com.trivialis.java.jassimp.util.string;
import com.trivialis.java.jassimp.util.serialization.Bytes;

public class material {

	public enum aiShadingMode {
		aiShadingMode_Gouraud(0x2);

		public int value;

		private aiShadingMode(int val) {
			value = val;
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
		public aiMaterialProperty[] mProperties;

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
			        aiMaterialProperty prop = mProperties[i];

			        if (prop !=null/* just for safety */ && string.strcmp( prop.mKey.data, pKey.getBytes(StandardCharsets.UTF_8) )==0 &&
			            prop.mSemantic == type && prop.mIndex == index){

			            mProperties[i]=null;
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

			    pcNew.mKey.length = string.strlen(pKey.toCharArray());
			    assert (types.MAXLEN > pcNew.mKey.length);
			    string.strcpy( pcNew.mKey.data, pKey.getBytes(StandardCharsets.UTF_8) );

			    if (Integer.MAX_VALUE != iOutIndex)  {
			        mProperties[iOutIndex] = pcNew;
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
			        string.memcpy (ppTemp,mProperties,iOld);

			        mProperties = null;
			        mProperties = ppTemp;
			    }
			    // push back ...
			    mProperties[mNumProperties++] = pcNew;
			    return aiReturn.SUCCESS;
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
			    return AddBinaryProperty(pInput.data,
			    		pInput.length+1+4,
			        pKey,
			        type,
			        index,
			        aiPropertyTypeInfo.aiPTI_String);
			}



	}

	public static class aiMaterialProperty
	{
	    /** Specifies the name of the property (key)
	     *  Keys are generally case insensitive.
	     */
	    public aiString mKey;

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

	};
}