package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.port.include.assimp.IOStream;
import com.trivialis.java.jassimp.port.include.assimp.IOSystem;
import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.std;
import com.trivialis.java.jassimp.util.string;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemFilter extends IOSystem {

	private IOSystem wrapped;
	private String src_file, base;
	private char sep;

	public FileSystemFilter(String file, IOSystem old)
	{
		wrapped=old;
		src_file=file;
		sep=wrapped.getOsSeparator();

		assert(null!=wrapped);

		base=src_file;
		int ss2;
		if(std.string.npos!=(ss2=base.lastIndexOf("\\/"))) {
			base=base.substring(0, ss2);
		} else {
			base="";
		}

		char s;

		if(base.length()==0) {
			base= ".";
			base+=getOsSeparator();
		} else if((s=base.charAt(base.length()-1))!='\\' && s != '/') {
			base += getOsSeparator();
		}

		Logger.getLogger("default").log(Level.INFO, "Import root directory is \'" + base + "\'");
	}

	@Override
	public IOStream Open(IPointer<StringBuilder> pFile, String pMode)
	{
		assert(pFile!=null);
		assert(pMode!=null);


		IOStream s = wrapped.Open(pFile, pMode);

		if(s==null) {
			IPointer<StringBuilder> tmp = Pointer.valueOf(new StringBuilder(pFile.get().toString()));
			BuildPath(tmp);
			s=wrapped.Open(tmp, pMode);

			if(s==null) {
				tmp=pFile;
				Cleanup(tmp);
				BuildPath(tmp);
				s=wrapped.Open(tmp, pMode);
			}
		}



		return s;
	}

	public void BuildPath (IPointer<StringBuilder> in)
    {
        // if we can already access the file, great.
        if (in.get().length() < 3 || wrapped.Exists(in)) {
            return;
        }

        // Determine whether this is a relative path (Windows-specific - most assets are packaged on Windows).
        if (in.get().charAt(1) != ':') {

            // append base path and try
            StringBuilder tmp = new StringBuilder(base + in.get().toString());
            if (wrapped.Exists(Pointer.valueOf(tmp))) {
                in.set(tmp);
                return;
            }
        }

        // Chop of the file name and look in the model directory, if
        // this fails try all sub paths of the given path, i.e.
        // if the given path is foo/bar/something.lwo, try
        // <base>/something.lwo
        // <base>/bar/something.lwo
        // <base>/foo/bar/something.lwo
        int pos = in.get().lastIndexOf("/");
        if (std.string.npos == pos) {
            pos = in.get().lastIndexOf("\\");
        }

        if (std.string.npos != pos)   {
            StringBuilder tmp;
            int last_dirsep = std.string.npos;

            while(true) {
                tmp = new StringBuilder(base);
                tmp = tmp.append(sep);

                int dirsep = in.get().lastIndexOf("/", last_dirsep);
                if (std.string.npos == dirsep) {
                    dirsep = in.get().lastIndexOf("\\", last_dirsep);
                }

                if (std.string.npos == dirsep || dirsep == 0) {
                    // we did try this already.
                    break;
                }

                last_dirsep = dirsep-1;

                tmp = new StringBuilder(tmp.toString()+ in.get().substring(dirsep+1, in.get().length()-pos));
                if (wrapped.Exists(Pointer.valueOf(tmp))) {
                    in.set(tmp);
                    return;
                }
            }
        }

        // hopefully the underlying file system has another few tricks to access this file ...
    }

	private void Cleanup (IPointer<StringBuilder> in)
    {
        char last = 0;
        if(in.get().length()==0) {
            return;
        }

        // Remove a very common issue when we're parsing file names: spaces at the
        // beginning of the path.
        int it = 0;
        while (ParsingUtils.IsSpaceOrNewLine(in.get().charAt(it)))++it;
        if (it != 0) {
            in.get().replace(0, it, "");
        }

        char sep = getOsSeparator();
        for (it = 0; it != in.get().length(); ++it) {
            // Exclude :// and \\, which remain untouched.
            // https://sourceforge.net/tracker/?func=detail&aid=3031725&group_id=226462&atid=1067632
            if (0==string.strncmp(in.get().substring(it), "://", 3 )) {
                it += 3;
                continue;
            }
            if (it == 0 && 0==string.strncmp(in.get().substring(it), "\\\\", 2)) {
                it += 2;
                continue;
            }

            // Cleanup path delimiters
            if (in.get().charAt(it) == '/' || in.get().charAt(it) == '\\') {
               in.get().setCharAt(it, sep);

                // And we're removing double delimiters, frequent issue with
                // incorrectly composited paths ...
                if (last == in.get().charAt(it)) {
                    in.get().replace(it, it+1, "");
                    --it;
                }
            }
            else if (in.get().charAt(it) == '%' && in.get().length() - it > 2) {

                // Hex sequence in URIs
                if( IsHex((in.get().charAt(it))) && IsHex((in.get().charAt(it+1))) ) {
                    in.get().setCharAt(it, (char) fast_atof.HexOctetToDecimal(in.get().substring(it).toCharArray()));
                    in.get().replace(it+1,it+2,"");
                    --it;
                }
            }

            last = in.get().charAt(it);
        }
    }

	public static boolean IsHex(char s) {
		return (s>='0' && s<='9') || (s>='a' && s<='f') || (s>='A' && s<='F');
	}


	@Override
	public char getOsSeparator()
	{
		return sep;
	}

	@Override
	public boolean Exists(IPointer<StringBuilder> pFile)
    {
		IPointer<StringBuilder> tmp = Pointer.valueOf(new StringBuilder(pFile.get().toString()));

        // Currently this IOSystem is also used to open THE ONE FILE.
        if (tmp.get().toString() != src_file)    {
            BuildPath(tmp);
            Cleanup(tmp);
        }

        return wrapped.Exists(tmp);
    }

}
