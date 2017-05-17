package com.trivialis.java.jassimp.testing;

import java.io.File;

import com.trivialis.java.jassimp.port.code.BaseImporter.ScopeGuard;
import com.trivialis.java.jassimp.port.code.DefaultIOStream;
import com.trivialis.java.jassimp.port.code.Importer;
import com.trivialis.java.jassimp.port.code.XFileImporter;
import com.trivialis.java.jassimp.port.include.assimp.IOStream;
import com.trivialis.java.jassimp.port.include.assimp.IOSystem;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.ctype;

public class TestingXFileImporting {

	public static void main(String[] args) {
		System.out.println(ctype.isspace('\r'));
                
		String path = ""
                        + "/home/frank/Projects/RTR/"
                        //+ "X:/My Documents/Projects/"
                        + "RTR/{app}/Scenes/xfiles/mozd02.X";
                
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
	}
	
}
