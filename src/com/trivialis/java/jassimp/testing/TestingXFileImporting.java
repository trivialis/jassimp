package com.trivialis.java.jassimp.testing;

import java.io.File;

import com.trivialis.java.jassimp.port.code.BaseImporter.ScopeGuard;
import com.trivialis.java.jassimp.port.code.DefaultIOStream;
import com.trivialis.java.jassimp.port.code.Importer;
import com.trivialis.java.jassimp.port.code.ObjExporter;
import com.trivialis.java.jassimp.port.code.XFileImporter;
import com.trivialis.java.jassimp.port.include.assimp.IOStream;
import com.trivialis.java.jassimp.port.include.assimp.IOSystem;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.ctype;
import java.io.IOException;
import java.nio.file.Files;

public class TestingXFileImporting {

	public static void main(String[] args) throws IOException {
                
		String path = (!System.getProperty("os.name").contains("Windows"))?
				System.getProperty("user.home")+"/Projects/RTR/RTR/{app}/Scenes/xfiles/mozd02.X"
                        :System.getProperty("user.name").toLowerCase().contains("s23")?
                        		System.getProperty("user.home")+"/My Documents/Projects/RTR/{app}/Scenes/xfiles/mozd02.X":
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
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(0).mKey.data));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(1).mKey.data));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(2).mKey.data));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(3).mKey.data));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(4).mKey.data));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(5).mKey.data));
//		System.out.println(new String(result.get().mMaterials[0].mProperties.get(6).mKey.data));
//                
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(0).mType.toString()));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(1).mType.toString()));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(2).mType.toString()));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(3).mType.toString()));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(4).mType.toString()));
//                System.out.println(new String(result.get().mMaterials[0].mProperties.get(5).mType.toString()));
//		System.out.println(new String(result.get().mMaterials[0].mProperties.get(6).mType.toString()));
		
		//TODO: Change paths of texture pictures. Make it follow jme3 rules.
		
		
		
		ObjExporter obj = new ObjExporter(Pointer.valueOf(new StringBuilder("test.obj")), result.get());
		File f = new File("../openRail/assets/Models/test.obj");
		File f2 = new File("../openRail/assets/Models/test.obj.mtl");
		Files.write(f.toPath(), obj.mOutput.toString().getBytes());
		System.out.println(obj.mOutput.toString().getBytes().length);
		Files.write(f2.toPath(), obj.mOutputMat.toString().getBytes());
	}
	
}
