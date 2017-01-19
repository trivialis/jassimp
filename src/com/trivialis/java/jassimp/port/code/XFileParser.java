package com.trivialis.java.jassimp.port.code;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.sun.org.apache.xerces.internal.impl.RevalidationHandler;
import com.trivialis.java.jassimp.port.code.Exceptional.DeadlyImportError;
import com.trivialis.java.jassimp.port.code.XFileHelper.Material;
import com.trivialis.java.jassimp.port.code.XFileHelper.Mesh;
import com.trivialis.java.jassimp.port.code.XFileHelper.Node;
import com.trivialis.java.jassimp.port.code.XFileHelper.Scene;
import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.port.include.assimp.matrix4x4.aiMatrix4x4;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.StringUtil;
import com.trivialis.java.jassimp.util.ctype;
import com.trivialis.java.jassimp.util.string;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class XFileParser {

	public static final int MSZIP_MAGIC = 0x4B43;
	public static final int MSZIP_BLOCK = 32786;
	private Object mMajorVersion;
	private boolean mIsBinaryFormat;
	private int mBinaryNumCount;
	private int mMinorVersion;
	private IPointer<Character> P;
	private IPointer<Character> End;
	private int mLineNumber;
	private Scene mScene;
	private int mBinaryFloatSize;

	public XFileParser(Character[] pBuffer)
	{
	    mMajorVersion = mMinorVersion = 0;
	    mIsBinaryFormat = false;
	    mBinaryNumCount = 0;
	    P = End = null;
	    mLineNumber = 0;
	    mScene = null;


	    ArrayList<Character> uncompressed  =new ArrayList<Character>();

	    P = Pointer.valueOf(pBuffer);
	    End = P.pointerCopy().pointerAdjust(pBuffer.length-1);

	    if(string.strncmp(P, "xof ", 4) != 0)
	    	throw new Exceptional.DeadlyImportError("Header mismatch, file is not an XFile.");

	    mMajorVersion = (P.pointerOffset(4).get()-48)*10 + (P.pointerOffset(5).get()-48);
	    mMinorVersion = (P.pointerOffset(6).get()-48)*10 + (P.pointerOffset(7).get()-48);

	    boolean compressed = false;

	    if(string.strncmp(P.pointerOffset(8), "txt ", 4)==0)
	    	mIsBinaryFormat = false;
	    else if(string.strncmp(P.pointerOffset(8), "bin ", 4)==0)
	    	mIsBinaryFormat=true;
	    else if(string.strncmp(P.pointerOffset(8), "tzip", 4)==0)
	    {
	    	mIsBinaryFormat=false;
	    	compressed=true;
	    }
	    else if(string.strncmp(P.pointerOffset(8), "bzip", 4)==0)
	    {
	    	mIsBinaryFormat=true;
	    	compressed=true;
	    }
	    else ThrowException(TinyFormatter.format("Unsupported xfile format '" ,
	    	       P.pointerOffset(8).get()+"" , P.pointerOffset(9).get()+"" , P.pointerOffset(10).get()+"" , P.pointerOffset(11).get()+"" , "'"));

	    mBinaryFloatSize = (P.pointerOffset(12).get() - 48) * 1000
	            + (P.pointerOffset(13).get() - 48) * 100
	            + (P.pointerOffset(14).get() - 48) * 10
	            + (P.pointerOffset(15).get() - 48);

	    if(mBinaryFloatSize!=32 && mBinaryFloatSize != 64)
	    	ThrowException(TinyFormatter.format("Unknown float size ", mBinaryFloatSize+""," specified in xfile header."));

	    mBinaryFloatSize/=8;

	    P=P.pointerAdjust(16);


	    if(compressed) {
	    	throw new NotImplementedException();
	    } else {
	    	ReadUntilEndOfLine();
	    }

	    mScene = new Scene();
	    ParseFile();

	    if(mScene.mRootNode!=null) {
	    	FilterHierarchy(mScene.mRootNode);
	    }

	}

	public void destroy() {
		mScene = null;
	}

	private void ParseFile()
	{
		boolean running = true;
		while(running) {
			String objectName = GetNextToken();
			if(objectName.length()==0) break;

			if( objectName == "template")
	            ParseDataObjectTemplate();
	        else
	        if( objectName == "Frame")
	            ParseDataObjectFrame(null);
	        else
	        if( objectName == "Mesh")
	        {
	            // some meshes have no frames at all
	            Mesh mesh = new Mesh();
	            ParseDataObjectMesh( mesh);
	            mScene.mGlobalMeshes.add(mesh);
	        } else
	        if( objectName == "AnimTicksPerSecond")
	            ParseDataObjectAnimTicksPerSecond();
	        else
	        if( objectName == "AnimationSet")
	            ParseDataObjectAnimationSet();
	        else
	        if( objectName == "Material")
	        {
	            // Material outside of a mesh or node
	            Material material = new Material();
	            ParseDataObjectMaterial(material);
	            mScene.mGlobalMaterials.add(material);
	        } else
	        if( objectName == "}")
	        {
	            // whatever?
	           Logger.getLogger("default").warning("} found in dataObject");
	        } else
	        {
	            // unknown format
	        	Logger.getLogger("default").warning("Unknown data object in animation of .x file");
	            ParseUnknownDataObject();
	        }
		}
	}

	private void ParseDataObjectTemplate()
	{
		IPointer<String> name = Pointer.valueOf("");
		readHeadOfDataObject(name);

		String guid = GetNextToken();

		boolean running  =true;
		while(running) {
			String s = GetNextToken();

			if(s=="}")
				break;

		     if( s.length() == 0)
		            ThrowException( "Unexpected end of file reached while parsing template definition");
		}

	}

	private void ParseDataObjectFrame(Node pParent)
	{
		IPointer<String> name = Pointer.valueOf("");
		readHeadOfDataObject(name);

		Node node = new Node(pParent);
		node.mName = name.get();
		if(pParent!=null) {
			pParent.mChildren.add(node);
		} else {
			if(mScene.mRootNode!=null)
			{
				if(mScene.mRootNode.mName != "$dummy_root")
				{
					Node exroot = mScene.mRootNode;
					mScene.mRootNode = new Node(null);
					mScene.mRootNode.mName = "$dummy_root";
					mScene.mRootNode.mChildren.add(exroot);
					exroot.mParent = mScene.mRootNode;
				}

				mScene.mRootNode.mChildren.add(node);
				node.mParent = mScene.mRootNode;
			} else {
				mScene.mRootNode = node;
			}
		}

		boolean running = true;
		while (running) {
			String objectName = GetNextToken();
	        if (objectName.length() == 0)
	            ThrowException( "Unexpected end of file reached while parsing frame");

	        if( objectName == "}")
	            break; // frame finished
	        else
	        if( objectName == "Frame")
	            ParseDataObjectFrame( node); // child frame
	        else
	        if( objectName == "FrameTransformMatrix")
	            ParseDataObjectTransformationMatrix( node.mTrafoMatrix);
	        else
	        if( objectName == "Mesh")
	        {
	            Mesh mesh = new Mesh(name.get());
	            node.mMeshes.add( mesh);
	            ParseDataObjectMesh( mesh);
	        } else
	        {
	        	Logger.getLogger("default").warning("Unknown data object in frame in x file");
	            ParseUnknownDataObject();
	        }
		}
	}

	private void ParseDataObjectTransformationMatrix(aiMatrix4x4<? extends Number> pMatrix)
	{
		readHeadOfDataObject(Pointer.valueOf(""));

	    pMatrix.a1 = ReadFloat(); pMatrix.b1 = ReadFloat();
	    pMatrix.c1 = ReadFloat(); pMatrix.d1 = ReadFloat();
	    pMatrix.a2 = ReadFloat(); pMatrix.b2 = ReadFloat();
	    pMatrix.c2 = ReadFloat(); pMatrix.d2 = ReadFloat();
	    pMatrix.a3 = ReadFloat(); pMatrix.b3 = ReadFloat();
	    pMatrix.c3 = ReadFloat(); pMatrix.d3 = ReadFloat();
	    pMatrix.a4 = ReadFloat(); pMatrix.b4 = ReadFloat();
	    pMatrix.c4 = ReadFloat(); pMatrix.d4 = ReadFloat();

	    CheckForSemicolon();
	    CheckForClosingBrace();
	}

	private ai_real ReadFloat() {
		if(mIsBinaryFormat) {
			throw new NotImplementedException();
		}

		FindNextNoneWhiteSpace();
		if(string.strncmp(P, "-1.#IND00", 9)==0 || string.strncmp(P, "1.#IND00", 8)==0 )
		{
			P.pointerAdjust(9);
			CheckForSeparator();
			return new ai_real<Double>(0.0D);
		}
	}

	private void CheckForSeparator()
	{
		// TODO Auto-generated method stub

	}

	private void CheckForClosingBrace()
	{
		// TODO Auto-generated method stub

	}

	private void CheckForSemicolon()
	{
		// TODO Auto-generated method stub

	}

	private ai_real<? extends Number> ReadFloat()
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void readHeadOfDataObject(IPointer<String> poName)
	{
		String nameOrBrace = GetNextToken();
		if(nameOrBrace!="{") {
			if(poName!=null)
				poName.set(nameOrBrace);

			if(GetNextToken() != "{")
				ThrowException("Opening brace expected");
		}
	}



	private void ParseUnknownDataObject()
	{
		// TODO Auto-generated method stub

	}

	private void ParseDataObjectMaterial(Material material)
	{
		// TODO Auto-generated method stub

	}

	private void ParseDataObjectAnimationSet()
	{
		// TODO Auto-generated method stub

	}

	private void ParseDataObjectAnimTicksPerSecond()
	{
		// TODO Auto-generated method stub

	}

	private void ParseDataObjectMesh(Mesh mesh)
	{
		// TODO Auto-generated method stub

	}





	private String GetNextToken()
	{
		String s = "";

		if(mIsBinaryFormat) {
			throw new NotImplementedException();
		} else {
			FindNextNoneWhiteSpace();
			if(P.getOffset()>=End.getOffset()) return s;

			while((P.getOffset()<End.getOffset()) && !ctype.isspace(P.get())) {
				if(P.get()==';' || P.get()=='}' || P.get()=='{' || P.get() == ',') {
					if(s==null || s.length()==0)
						s=s+P.get();P.pointerPostInc();

				}
				s=s+P.get();P.pointerPostInc();
			}
		}
		return s;
	}

	private void FindNextNoneWhiteSpace()
	{
		if(mIsBinaryFormat)
			return;

		boolean running = true;
		while(running) {
			while(P.getOffset()<End.getOffset() && ctype.isspace(P.get())) {
				if(P.get()=='\n')
					mLineNumber++;
				P.pointerPostInc();
			}

			if(P.getOffset()>=End.getOffset())
				return;

			if((P.pointerOffset(0).get()=='/' && P.pointerOffset(1).get()=='/') || P.pointerOffset(0).get()=='#')
				ReadUntilEndOfLine();
			else
				break;

		}

	}

	private void FilterHierarchy(Node mRootNode)
	{
		// TODO Auto-generated method stub

	}

	private void ReadUntilEndOfLine()
	{
		if(mIsBinaryFormat) return;

		while(P.opSmaller(End)) {
			if(P.get()=='\n' || P.get() == '\r') {
				P.pointerPostInc(); mLineNumber++;
				return;
			}
			P.pointerPostInc();
		}
	}

	private void ThrowException(String pText)
	{
		if(mIsBinaryFormat) throw new DeadlyImportError(pText);
		else throw new DeadlyImportError(TinyFormatter.format("Line ", mLineNumber+"",": ",pText));
	}



}
