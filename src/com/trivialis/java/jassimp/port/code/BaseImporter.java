package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.port.include.assimp.IOSystem;
import com.trivialis.java.jassimp.port.include.assimp.ProgressHandler;
import com.trivialis.java.jassimp.port.include.assimp.scene.aiScene;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	        InternReadFile( Pointer.valueOf(new StringBuilder(pFile)), sc.obj, Pointer.valueOf(filter));

	    } catch( Exception err)    {
	        // extract error description
	        m_ErrorText = err.getMessage();
	        Logger.getLogger("default").log(Level.SEVERE, m_ErrorText);
                err.printStackTrace();
	        return null;
	    }

	    // return what we gathered from the import.
	    sc.dismiss();
	    return sc;
	}

	public void SetupProperties(Importer pImp)
	{

	}

	//public abstract void InternReadFile(IPointer<StringBuilder> pFile, ScopeGuard<aiScene> sc, IPointer<FileSystemFilter> valueOf);

	public void ConvertToUTF8(IPointer<byte[]> data)
	{
		 if(data.get().length < 8) {
		        throw new Exceptional.DeadlyImportError("File is too small");
		    }

		    // UTF 8 with BOM
		    if((byte)data.get()[0] == 0xEF && (byte)data.get()[1] == 0xBB && (byte)data.get()[2] == 0xBF) {
		        Logger.getLogger("default").log(Level.FINEST,"Found UTF-8 BOM ...");
		        data.set(new String(data.get(), Charset.forName("UTF-8")).getBytes(Charset.forName("UTF-8")));
		        return;
		    }

		    // UTF 32 BE with BOM
		    if(((ByteBuffer) ByteBuffer.wrap(Arrays.copyOfRange(data.get(), 0, 4))).getInt() == 0xFFFE0000) {

//		    	ByteBuffer temp = ByteBuffer.allocate(data.get().length);
//		        // swap the endianness ..
//		    	IntBuffer ib = ByteBuffer.wrap(data.get()).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
//		    	for(int i : ib.array()) {
//		    		temp.putInt(i);
//		    	}
//		    	data.set(temp.array());
		    	data.set(new String(data.get(), Charset.forName("UTF-32BE")).getBytes(Charset.forName("UTF-8"))); return;
		    }

		    // UTF 32 LE with BOM
		    if(((ByteBuffer) ByteBuffer.wrap(Arrays.copyOfRange(data.get(), 0, 4))).getInt()== 0x0000FFFE) {
		    	Logger.getLogger("default").log(Level.FINEST,"Found UTF-32 BOM ...");
		    	data.set(new String(data.get(), Charset.forName("UTF-32LE")).getBytes(Charset.forName("UTF-8"))); return;
//		        int sstart = (uint32_t*)&data.front()+1, *send = (uint32_t*)&data.back()+1;
//		        char* dstart,*dend;
//		        std::vector<char> output;
//		        do {
//		            output.resize(output.size()?output.size()*3/2:data.size()/2);
//		            dstart = &output.front(),dend = &output.back()+1;
//
//		            result = ConvertUTF32toUTF8((const UTF32**)&sstart,(const UTF32*)send,(UTF8**)&dstart,(UTF8*)dend,lenientConversion);
//		        } while(result == targetExhausted);
//
//		        ReportResult(result);
//
//		        // copy to output buffer.
//		        const size_t outlen = (size_t)(dstart-&output.front());
//		        data.assign(output.begin(),output.begin()+outlen);
//		        return;
		    }

		    // UTF 16 BE with BOM
		    if(ByteBuffer.wrap(Arrays.copyOfRange(data.get(), 0, 2)).getShort() == 0xFFFE) {
		    	data.set(new String(data.get(), Charset.forName("UTF-16BE")).getBytes(Charset.forName("UTF-8"))); return;
//		        // swap the endianness ..
//		        for(uint16_t* p = (uint16_t*)&data.front(), *end = (uint16_t*)&data.back(); p <= end; ++p) {
//		            ByteSwap::Swap2(p);
//		        }
		    }

		    // UTF 16 LE with BOM
		    if(ByteBuffer.wrap(Arrays.copyOfRange(data.get(), 0, 2)).getShort() == 0xFEFF) {
		    	Logger.getLogger("default").log(Level.FINEST,"Found UTF-16 BOM ...");
		    	data.set(new String(data.get(), Charset.forName("UTF-16LE")).getBytes(Charset.forName("UTF-8"))); return;
//		        const uint16_t* sstart = (uint16_t*)&data.front()+1, *send = (uint16_t*)(&data.back()+1);
//		        char* dstart,*dend;
//		        std::vector<char> output;
//		        do {
//		            output.resize(output.size()?output.size()*3/2:data.size()*3/4);
//		            dstart = &output.front(),dend = &output.back()+1;
//
//		            result = ConvertUTF16toUTF8((const UTF16**)&sstart,(const UTF16*)send,(UTF8**)&dstart,(UTF8*)dend,lenientConversion);
//		        } while(result == targetExhausted);
//
//		        ReportResult(result);
//
//		        // copy to output buffer.
//		        const size_t outlen = (size_t)(dstart-&output.front());
//		        data.assign(output.begin(),output.begin()+outlen);
//		        return;
		    }

	}

	public abstract void InternReadFile(IPointer<StringBuilder> pFile, aiScene pScene, IPointer<IOSystem> pIOHandler);


}
