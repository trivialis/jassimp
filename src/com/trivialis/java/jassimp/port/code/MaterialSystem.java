package com.trivialis.java.jassimp.port.code;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.trivialis.java.jassimp.port.include.assimp.material;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterialProperty;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.types.aiReturn;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.StringUtil;
import com.trivialis.java.jassimp.util.std;
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

}
