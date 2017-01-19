package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;
import com.trivialis.java.jassimp.util.IPointer;

public class fast_atof {

	public static IPointer<Character> fast_atoreal_move(IPointer<Character> c, ai_real<Number> out) {
		return fast_atoreal_move(c, out, true);
	}

	public static IPointer<Character> fast_atoreal_move(IPointer<Character> c, ai_real<Number> out, boolean check_comma) {
		c=c.pointerCopy();
		ai_real f = out.forValue(0);

		boolean inv = (c.get()=='-');
		if(inv || c.get()=='+') {
			c.pointerPostInc();
		}

		if((c.pointerOffset(0).get()=='N'|| c.pointerOffset(0).get() =='n') && StringComparison.ASSIMP_strincmp(c, "nan", 3) ==0 )
		{
			out.setValue(out.getNaN());
			c.pointerAdjust(3);
			return c;
		}

		 if ((c.pointerOffset(0) == 'I' || c.pointerOffset(0) == 'i') && StringComparison.ASSIMP_strincmp(c, "inf", 3) == 0)
		    {
		        out = std::numeric_limits<Real>::infinity();
		        if (inv) {
		            out = -out;
		        }
		        c += 3;
		        if ((c.pointerOffset(0) == 'I' || c.pointerOffset(0) == 'i') && StringComparison.ASSIMP_strincmp(c, "inity", 5) == 0)
		        {
		            c += 5;
		        }
		        return c;
		    }

		    if (!(c.pointerOffset(0) >= '0' && c.pointerOffset(0) <= '9') &&
		        !((c.pointerOffset(0) == '.' || (check_comma && c.pointerOffset(0) == ',')) && c.pointerOffset(1) >= '0' && c.pointerOffset(1) <= '9'))
		    {
		        throw new IllegalArgumentException("Cannot parse string "+
                        "as real number: does not start with digit "+
                        "or decimal point followed by digit.");
		    }

		    if (c.get() != '.' && (! check_comma || c.pointerOffset(0) != ','))
		    {
		        f = static_cast<Real>( strtoul10_64 ( c, &c) );
		    }

		    if ((c.get() == '.' || (check_comma && c.pointerOffset(0) == ',')) && c.pointerOffset(1) >= '0' && c.pointerOffset(1) <= '9')
		    {
		        ++c;

		        // NOTE: The original implementation is highly inaccurate here. The precision of a single
		        // IEEE 754 float is not high enough, everything behind the 6th digit tends to be more
		        // inaccurate than it would need to be. Casting to double seems to solve the problem.
		        // strtol_64 is used to prevent integer overflow.

		        // Another fix: this tends to become 0 for long numbers if we don't limit the maximum
		        // number of digits to be read. AI_FAST_ATOF_RELAVANT_DECIMALS can be a value between
		        // 1 and 15.
		        unsigned int diff = AI_FAST_ATOF_RELAVANT_DECIMALS;
		        double pl = static_cast<double>( strtoul10_64 ( c, &c, &diff ));

		        pl *= fast_atof_table[diff];
		        f += static_cast<Real>( pl );
		    }
		    // For backwards compatibility: eat trailing dots, but not trailing commas.
		    else if (c.get() == '.') {
		        ++c;
		    }

		    // A major 'E' must be allowed. Necessary for proper reading of some DXF files.
		    // Thanks to Zhao Lei to point out that this if() must be outside the if (*c == '.' ..)
		    if (c.get() == 'e' || c.get() == 'E') {

		        ++c;
		        const bool einv = (c.get()=='-');
		        if (einv || c.get()=='+') {
		            ++c;
		        }

		        // The reason float constants are used here is that we've seen cases where compilers
		        // would perform such casts on compile-time constants at runtime, which would be
		        // bad considering how frequently fast_atoreal_move<float> is called in Assimp.
		        Real exp = static_cast<Real>( strtoul10_64(c, &c) );
		        if (einv) {
		            exp = -exp;
		        }
		        f *= std::pow(static_cast<Real>(10.0), exp);
		    }

		    if (inv) {
		        f = -f;
		    }
		    out = f;
		    return c;
		}

	}

}
