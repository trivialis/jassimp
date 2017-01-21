package com.trivialis.java.jassimp.port.code;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.trivialis.java.jassimp.port.include.assimp.IOSystem;
import com.trivialis.java.jassimp.port.include.assimp.ProgressHandler;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;

public abstract class BaseImporter {

	public static class ScopeGuard<T> {
		private T obj;
		private boolean mdismiss;

		public ScopeGuard(T o) {
			obj = o;
			mdismiss = false;
		}

		public void destroy() {
			if(!mdismiss) {
				obj=null;
			}
			obj = null;
		}

		public T dismiss() {
			mdismiss=true;
			return obj;
		}

		public T cast() {
			return obj;
		}

		public T get() {
			return obj;
		}

		private ScopeGuard() {

		}

		private ScopeGuard(ScopeGuard<T> o) {
			obj=o.obj;
			mdismiss=o.mdismiss;
		}

	}

	public String m_ErrorText;
	public ProgressHandler m_progress;

	public BaseImporter() {
		m_progress = new ProgressHandler();
	}

	public void destroy() {

	}

	public ScopeGuard<aiScene> ReadFile( Importer pImp,  String pFile, IOSystem pIOHandler)
	{
	    m_progress = pImp.GetProgressHandler();
	    assert(m_progress!=null);

	    // Gather configuration properties for this run
	    SetupProperties( pImp );

	    // Construct a file system filter to improve our success ratio at reading external files
	    FileSystemFilter filter = new FileSystemFilter(pFile,pIOHandler);

	    // create a scene object to hold the data
	    ScopeGuard<aiScene> sc = new ScopeGuard<aiScene>(new aiScene());

	    // dispatch importing
	    try
	    {
	        InternReadFile( pFile, sc, Pointer.valueOf(filter));

	    } catch( Exception err)    {
	        // extract error description
	        m_ErrorText = err.getMessage();
	        Logger.getLogger("default").log(Level.SEVERE, m_ErrorText);
	        return null;
	    }

	    // return what we gathered from the import.
	    sc.dismiss();
	    return sc;
	}

	public void SetupProperties(Importer pImp)
	{
		// TODO Auto-generated method stub

	}

	public abstract void InternReadFile(String pFile, ScopeGuard<aiScene> sc, IPointer<FileSystemFilter> valueOf);

	public void ConvertToUTF8(ArrayList<Character> mBuffer)
	{
		// TODO Auto-generated method stub

	}


}
