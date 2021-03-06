package com.trivialis.java.jassimp.port.code;

import com.trivialis.java.jassimp.util.IPointer;
import com.trivialis.java.jassimp.util.Pointer;
import com.trivialis.java.jassimp.util.std;

public class fast_atof {

	private static final int AI_FAST_ATOF_RELAVANT_DECIMALS = 15;

	private static final double[] fast_atof_table = new double[] {
				    0.0,
				    0.1,
				    0.01,
				    0.001,
				    0.0001,
				    0.00001,
				    0.000001,
				    0.0000001,
				    0.00000001,
				    0.000000001,
				    0.0000000001,
				    0.00000000001,
				    0.000000000001,
				    0.0000000000001,
				    0.00000000000001,
				    0.000000000000001
	};

	public static IPointer<Character> fast_atoreal_move(IPointer<Character> c, IPointer<Float> out) {
		return fast_atoreal_move(c, out, true);
	}

	public static IPointer<Character> fast_atoreal_move(IPointer<Character> c, IPointer<Float> out, boolean check_comma) {
		c=c.pointerCopy();
		Float f = out.get(); //if(true) throw new RuntimeException(f.getValue().getClass().toString());

		boolean inv = (c.get()=='-');
		if(inv || c.get()=='+') {
			c.postInc();
		}

		if((c.pointerOffset(0).get()=='N'|| c.pointerOffset(0).get() =='n') && StringComparison.ASSIMP_strincmp(c, "nan", 3) ==0 )
		{
			out.set(out.get().NaN);
			c.pointerAdjust(3);
			return c;
		}

		if ((c.pointerOffset(0).get() == 'I' || c.pointerOffset(0).get() == 'i') && StringComparison.ASSIMP_strincmp(c, "inf", 3) == 0)
		{
			out.set(out.get().POSITIVE_INFINITY);
			if (inv) {
				out.set(out.get()*-1.0F);
			}
			c.pointerAdjust(3);
			if ((c.pointerOffset(0).get() == 'I' || c.pointerOffset(0).get() == 'i') && StringComparison.ASSIMP_strincmp(c, "inity", 5) == 0)
			{
				c.pointerAdjust(5);
			}
			return c;
		}

		if (!(c.pointerOffset(0).get() >= '0' && c.pointerOffset(0).get() <= '9') &&
				!((c.pointerOffset(0).get() == '.' || (check_comma && c.pointerOffset(0).get() == ',')) && c.pointerOffset(1).get() >= '0' && c.pointerOffset(1).get() <= '9'))
		{
			throw new IllegalArgumentException("Cannot parse string "+
					"as real number: does not start with digit "+
					"or decimal point followed by digit.");
		}

		if (c.get() != '.' && (! check_comma || c.pointerOffset(0).get() != ','))
		{
			f = (float) strtoul10_64( c, c.pointerAddressOf());
		}

		if ((c.get() == '.' || (check_comma && c.pointerOffset(0).get() == ',')) && c.pointerOffset(1).get() >= '0' && c.pointerOffset(1).get() <= '9')
		{
			c.postInc();

			IPointer<Integer> diff = Pointer.valueOf(AI_FAST_ATOF_RELAVANT_DECIMALS);
			double pl = (double)( strtoul10_64 ( c, c.pointerAddressOf(), diff));

			pl = pl * fast_atof_table[diff.get()];
			f = (float) (f+(pl));
		}

		else if (c.get() == '.') {
			c.postInc();
		}

		if (c.get() == 'e' || c.get() == 'E') {

			c.postInc();
			boolean einv = (c.get()=='-');
			if (einv || c.get()=='+') {
				c.postInc();
			}

			float exp = strtoul10_64(c, c.pointerAddressOf());
			if (einv) {
				exp = exp*-1.0F;
			}
			f = (float) (f * (std.pow((Double)10.0D, (double)exp)));
		}

		if (inv) {
			f = f*-1;
		}
		out.set(f);
		return c;
	}

	public static long strtoul10_64(IPointer<Character> in) {
		return strtoul10_64(in, null);
		//return strtoul10_64(in, Pointer.valueOf('\0').pointerAddressOf());
	}

public static long strtoul10_64(IPointer<Character> in, IPointer<IPointer<Character>> out)
{
	return strtoul10_64(in , out, null);
//	return strtoul10_64(in , out, Pointer.valueOf(0));
}

public static long strtoul10_64(IPointer<Character> in, IPointer<IPointer<Character>> out, IPointer<Integer> max_inout)
{
    int cur = 0;
    long value = 0;

    if ( in.get() < '0' || in.get() > '9' )
            throw new IllegalArgumentException("The string \"" + in + "\" cannot be converted into a value.");

    boolean running = true;
    while ( running )
    {
        if ( in.get() < '0' || in.get() > '9' )
            break;

        long new_value = ( value * 10 ) + ( in.get() - '0' );

        if (new_value < value)
            throw new RuntimeException("Converting the string \"" + in + "\" into a value resulted in overflow.");

        value = new_value;

        in.postInc();
        ++cur;

        if (max_inout!=null && max_inout.get() == cur) {

            if (out!=null) {
                while (in.get() >= '0' && in.get() <= '9')
                    in.postInc();
                out.set(in);
            }

            return value;
        }
    }
    if (out!=null)
        out.set(in);

    if (max_inout!=null)
        max_inout.set(cur);

    return value;
}

public static byte HexOctetToDecimal(char[] in)
{

	return (byte) (((byte)HexDigitToDecimal(in[0])<<4)+(byte)HexDigitToDecimal(in[1]));
}

private static int HexDigitToDecimal(char in)
{
    int out = Integer.MAX_VALUE;
    if (in >= '0' && in <= '9')
        out = in - '0';

    else if (in >= 'a' && in <= 'f')
        out = 10 + in - 'a';

    else if (in >= 'A' && in <= 'F')
        out = 10 + in - 'A';

    // return value is UINT_MAX if the input is not a hex digit
    return out;
}

}
