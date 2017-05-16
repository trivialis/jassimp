package com.trivialis.java.jassimp.port.code;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.trivialis.java.jassimp.port.include.assimp.color4.aiColor4D;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.material;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterialProperty;
import com.trivialis.java.jassimp.port.include.assimp.types.aiReturn;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.StringUtil;
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
		        aiMaterialProperty prop = pMat.mProperties[i];

		        if (prop!=null
		            && 0 == string.strcmp( Pointer.valueOf(StringUtil.toCharacterArray(prop.mKey.data)), Pointer.valueOf(StringUtil.toCharacterArray(pKey.toCharArray())) )
		            && (Integer.MAX_VALUE == type  || prop.mSemantic == type)
		            && (Integer.MAX_VALUE == index || prop.mIndex == index))
		        {
		            pPropOut.set(pMat.mProperties[i]);
		            return aiReturn.SUCCESS;
		        }
		    }
		    pPropOut = null;
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
		        assert(prop.get().mDataLength>=5);

		        // The string is stored as 32 but length prefix followed by zero-terminated UTF8 data
		        pOut.length = (int)((int)(prop.get().mData[0]));

		        assert( pOut.length+1+4==prop.get().mDataLength );
		        assert( prop.get().mData[ prop.get().mDataLength - 1 ] !=0 );
		        string.memcpy(pOut.data,Arrays.copyOfRange(prop.get().mData, 4, pOut.length+1),pOut.length+1);
		    }
		    else {
		        // TODO - implement lexical cast as well
		        Logger.getLogger("default").log(Level.SEVERE, "Material property" + new String(pKey) +
		            " was found, but is no string" );
		        return aiReturn.FAILURE;
		    }
		    return aiReturn.SUCCESS;
		}


	public static aiReturn aiGetMaterialColor(aiMaterial pMat, String pKey, int type, int index, aiColor4D pOut)
	{

		int iMax = 4;
		IPointer<aiMaterialProperty> pPropOut = Pointer.valueOf(null);
		aiGetMaterialProperty(pMat, pKey, type, index, pPropOut);
		aiMaterialProperty prop = pPropOut.get();
		
		aiReturn eRet = aiReturn.SUCCESS;
		if(prop==null) eRet = aiReturn.FAILURE;
		
		int iWrite = Math.min(iMax, prop.mDataLength/4);
		iMax=iWrite;
		
		ByteBuffer bb = ByteBuffer.wrap(prop.mData);
		
		for(int a = 0; a<iWrite;a++) {
			if(a==0) pOut.r=new ai_real(bb.getFloat());
			if(a==1) pOut.g=new ai_real(bb.getFloat());
			if(a==2) pOut.b=new ai_real(bb.getFloat());
			if(a==3) pOut.a=new ai_real(bb.getFloat());
		}
		
		

		// if no alpha channel is defined: set it to 1.0
		if (3 == iMax) {
			pOut.a = new ai_real(1.0F);
		}

		return eRet;
			
	}


	public static aiReturn aiGetMaterialFloat(aiMaterial pMat, String pKey, Integer type, Integer index, ai_real pOut)
	{
		IPointer<aiMaterialProperty> pPropOut = Pointer.valueOf(null);
		aiGetMaterialProperty(pMat, pKey, type, index, pPropOut);
		aiMaterialProperty prop = pPropOut.get();
		
		aiReturn eRet = aiReturn.SUCCESS;
		if(prop==null) eRet = aiReturn.FAILURE;
		
		pOut.setValue(new ai_real(ByteBuffer.wrap(prop.mData).getFloat()));
		
		return eRet;
	}

}
