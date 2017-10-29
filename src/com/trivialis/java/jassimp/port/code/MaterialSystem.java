package com.trivialis.java.jassimp.port.code;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.material;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterialProperty;
import com.trivialis.java.jassimp.port.include.assimp.material.aiPropertyTypeInfo;
import com.trivialis.java.jassimp.port.include.assimp.types.aiReturn;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.StringUtil;
import com.trivialis.java.jassimp.util.ctype;
import com.trivialis.java.jassimp.util.string;

public class MaterialSystem {


	public static aiReturn aiGetMaterialProperty( aiMaterial pMat,
		     String pKey,
		    int type,
		    int index,
		     IPointer<aiMaterialProperty> pPropOut)
		{
		    assert (pMat != null);
		    assert (pKey != null);
		    assert (pPropOut != null);

		    for ( int i = 0; i < pMat.mNumProperties; ++i ) {
		        aiMaterialProperty prop = pMat.mProperties.get(i);
                        
		        if (prop!=null
		            && 0 == string.strcmp( Pointer.valueOf(StringUtil.toCharacterArray(prop.mKey.data)), Pointer.valueOf(StringUtil.toCharacterArray(pKey.toCharArray())) )
		            && (Integer.MAX_VALUE == type  || prop.mSemantic == type)
		            && (Integer.MAX_VALUE == index || prop.mIndex == index))
		        {
		            pPropOut.set(pMat.mProperties.get(i));
		            return aiReturn.SUCCESS;
		        }
		    }
		    pPropOut.set(null);
		    return aiReturn.FAILURE;
		}


	public static aiReturn aiGetMaterialString( aiMaterial pMat,
		     String pKey,
		    int type,
		    int index,
		    aiString pOut)
		{
		    assert (pOut != null);

		    IPointer<aiMaterialProperty> prop = Pointer.valueOf(new aiMaterialProperty());
		    aiGetMaterialProperty(pMat,pKey,type,index,prop);
		    if (prop.get()==null) {
		        return aiReturn.FAILURE;
		    }

		    if( material.aiPropertyTypeInfo.aiPTI_String == prop.get().mType) {
		        assert(prop.get().mDataLength>=5); if(prop.get().mDataLength<5) throw new RuntimeException("Invalid length: " + prop.get().mDataLength);

		        // The string is stored as 32 but length prefix followed by zero-terminated UTF8 data
		        pOut.length = ByteBuffer.wrap(prop.get().mData).getInt();//pOut.length = (int)((int)(prop.get().mData[0]));
		        assert( pOut.length+4==prop.get().mDataLength ); if(pOut.length+4!=prop.get().mDataLength) throw new RuntimeException("pOut length: " + (pOut.length+4) + " does not equal mDataLength: " + prop.get().mDataLength);
		        assert( prop.get().mData[ prop.get().mDataLength - 1 ] !=0 );
		        pOut.data=Arrays.copyOfRange(prop.get().mData, 4, pOut.length+4);
		    }
		    else {
		        // TODO - implement lexical cast as well
		        Logger.getLogger("default").log(Level.SEVERE, "Material property{0} was found, but is no string", pKey);
		        return aiReturn.FAILURE;
		    }
		    return aiReturn.SUCCESS;
		}


	public static aiReturn aiGetMaterialFloatArray(aiMaterial pMat,
		    String pKey,
		    int type,
		    int index,
		    float[] pOut,
		    IPointer<Integer> pMax)
		{
		    assert (pOut != null);
		    assert (pMat != null);

		    IPointer<aiMaterialProperty> pPropOut = Pointer.valueOf(new aiMaterialProperty());
		    aiGetMaterialProperty(pMat,pKey,type,index, pPropOut);
		    aiMaterialProperty prop = pPropOut.get();
		    if (prop==null) {
		        return aiReturn.FAILURE;
		    }
		    
		    ByteBuffer bb = ByteBuffer.wrap(prop.mData);

		    // data is given in floats, convert to ai_real
		    int iWrite = 0;
		    if( aiPropertyTypeInfo.aiPTI_Float == prop.mType || aiPropertyTypeInfo.aiPTI_Buffer == prop.mType)  {
		        iWrite = prop.mDataLength / 4;
		        if (pMax.get()!=null) {
		            iWrite = Math.min(pMax.get(),iWrite); ;
		        }
		        for (int a = 0; a < iWrite;++a)    {
		            pOut[a] = bb.getFloat();
		        }
		        if (pMax.get()!=null) {
		            pMax.set(iWrite);
		        }
		    }
		    // data is given in doubles, convert to float
		    else if( aiPropertyTypeInfo.aiPTI_Double == prop.mType)  {
		        iWrite = prop.mDataLength / 8;
		        if (pMax.get()!=null) {
		        	iWrite = Math.min(pMax.get(),iWrite); ;
		        }
		        for (int a = 0; a < iWrite;++a)    {
		        	 pOut[a] = (float) bb.getDouble();
		        }
		        if (pMax.get()!=null) {
		        	pMax.set(iWrite);
		        }
		    }
		    // data is given in ints, convert to float
		    else if( aiPropertyTypeInfo.aiPTI_Integer == prop.mType)  {
		        iWrite = prop.mDataLength / 4;
		        if (pMax.get()!=null) {
		        	iWrite = Math.min(pMax.get(),iWrite); ;
		        }
		        for (int a = 0; a < iWrite;++a)    {
		        	 pOut[a] = bb.getInt()+0.0F;
		        }
		        if (pMax.get()!=null) {
		        	pMax.set(iWrite);
		        }
		    }
		    // a string ... read floats separated by spaces
		    else {
		    	if (pMax.get()!=null) {
		            iWrite = pMax.get();
		        }
		        // strings are zero-terminated with a 32 bit length prefix, so this is safe
//		        char cur = prop.mData + 4;
		        IPointer<Character> pointer = Pointer.valueOf(StringUtil.toCharacterArray(prop.mData)); //System.out.println(new String(prop.mData));
		        pointer.pointerOffset(4);
		        if( prop.mDataLength < 5 ) throw new RuntimeException("mDataLength too small: " + prop.mDataLength);
		        if( prop.mData[ prop.mDataLength - 1 ] == 0) throw new RuntimeException("Not zero terminated: " + prop.mData[ prop.mDataLength - 1 ]);
		        for ( int a = 0; ;++a) {
		        	IPointer<Float> temp = Pointer.valueOf(0.0F); //System.out.println(StringUtil.getCharactersAsString(pointer, 17));
		            pointer = fast_atof.fast_atoreal_move(pointer,temp); 
		            pOut[a]=temp.get();
		            if ( a==iWrite-1 ) {
		                break;
		            }
		            if ( !ctype.isspace(pointer.get()) ) {
		                Logger.getLogger("").log(Level.WARNING, "Material property" + new String(pKey) +
		                    " is a string; failed to parse a float array out of it.");
		                return aiReturn.FAILURE;
		            }
		        }

		        if (pMax.get()!=null) {
		        	pMax.set(iWrite);
		        }
		    }
		    return aiReturn.SUCCESS;
		}
	
	public static aiReturn aiGetMaterialColor(aiMaterial pMat, String pKey, int type, int index, aiColor4D pOut)
	{

		IPointer<Integer> iMax = Pointer.valueOf(4);
		float[] c = new float[4];
		aiReturn eRet = aiGetMaterialFloatArray(pMat, pKey, type, index, c, iMax);

		pOut.r=c[0];
		pOut.g=c[1];
		pOut.b=c[2];
		pOut.a=c[3];
		// if no alpha channel is defined: set it to 1.0
		if (3 == iMax.get()) {
			pOut.a = 1.0F;
		}

		return eRet;
			
	}


	public static aiReturn aiGetMaterialFloat(aiMaterial pMat, String pKey, Integer type, Integer index, float... pOut)
	{
		return aiGetMaterialFloatArray(pMat, pKey, type, index, pOut, Pointer.valueOf(0));
	}

}
