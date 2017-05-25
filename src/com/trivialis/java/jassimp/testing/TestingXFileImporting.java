package com.trivialis.java.jassimp.testing;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.trivialis.java.jassimp.port.code.BaseImporter.ScopeGuard;
import com.trivialis.java.jassimp.port.code.DefaultIOStream;
import com.trivialis.java.jassimp.port.code.Importer;
import com.trivialis.java.jassimp.port.code.ObjExporter;
import com.trivialis.java.jassimp.port.code.XFileImporter;
import com.trivialis.java.jassimp.port.include.assimp.IOStream;
import com.trivialis.java.jassimp.port.include.assimp.IOSystem;
import com.trivialis.java.jassimp.port.include.assimp.material;
import com.trivialis.java.jassimp.port.include.assimp.material.aiMaterial;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.port.include.assimp.types.aiString;
import com.trivialis.java.jassimp.port.include.assimp.vector3.aiVector3t;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.ctype;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

public class TestingXFileImporting {

	public static void main(String[] args) throws IOException {
                
		String path = (!System.getProperty("os.name").contains("Windows"))?
				System.getProperty("user.home")+"/Projects/RTR/RTR/{app}/Scenes/xfiles/mozd02.X"
                        :System.getProperty("user.name").toLowerCase().contains("s23")?
                        		"X:/My Documents/Projects/RTR/{app}/Scenes/xfiles/mozd02.X":
                        			"C:/Users/MWPuser/AppData/Local/BrainBombers/Rule the Rail!/Scenes/xfiles/mozd02.X";
                
		XFileImporter xfi = new XFileImporter();
		ScopeGuard<aiScene> result = xfi.ReadFile(new Importer(), path, new IOSystem() {
			
			@Override
			public char getOsSeparator()
			{
				return File.separatorChar;
			}
			
			@Override
			public IOStream Open(IPointer<StringBuilder> pFile, String pMode)
			{
				return new DefaultIOStream(new File(pFile.get().toString()), new File(pFile.get().toString()).getName());
			}
			
			@Override
			public boolean Exists(IPointer<StringBuilder> in)
			{
				return new File(in.get().toString()).exists();
			}
		});
                System.out.println(xfi.parser.mScene.mRootNode.mChildren.get(0).mMeshes.get(0).mPositions.get(0));
                System.out.println(xfi.parser.mScene.mRootNode.mChildren.get(0).mMeshes.get(0).mPositions.get(1));
                System.out.println(xfi.parser.mScene.mRootNode.mChildren.get(0).mMeshes.get(0).mPositions.get(2));
                
		
		//TODO: Change paths of texture pictures. Make it follow jme3 rules.

		for(aiMaterial m : result.get().mMaterials) {
			aiString pOut = new aiString();
			m.Get(material.AI_MATKEY_TEXTURE_DIFFUSE(0),pOut);
			//System.out.println(new File(new String(pOut.data)).getName());

			//TODO: Why does this give an error? m.AddProperty(temp, material.AI_MATKEY_TEXTURE_DIFFUSE(0));
		}

		
		ObjExporter obj = new ObjExporter(Pointer.valueOf(new StringBuilder("test.obj")), result.get());
		File f = new File("../openRail/assets/Models/test.obj");
		File f2 = new File("../openRail/assets/Models/test.obj.mtl");
		Files.write(f.toPath(), obj.mOutput.toString().getBytes());
		Files.write(f2.toPath(), obj.mOutputMat.toString().replaceAll("[A-Z][:][\\\\]([A-Za-z0-9 ]+\\\\)*", "").replaceAll("[.]bmp", ".dds").getBytes());
	}
	
}
