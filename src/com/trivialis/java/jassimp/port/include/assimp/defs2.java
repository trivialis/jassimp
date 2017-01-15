package com.trivialis.java.jassimp.port.include.assimp;

@SuppressWarnings("unchecked")
public class defs2 {
	
	private enum Types {
		DOUBLE(Double.class) {
			@Override
			<T extends Number> T opAdd(T a, T b)
			{
				return (T) Double.valueOf(a.doubleValue()+b.doubleValue());
			}

			@Override
			<T, TOther extends Number> T cast(TOther a)
			{
				return (T) Double.valueOf(a.doubleValue());
			}
		}, INTEGER(Integer.class) {
			@Override
			<T extends Number> T opAdd(T a, T b)
			{
				return (T) Integer.valueOf(a.intValue()+b.intValue());
			}

			@Override
			<T, TOther extends Number> T cast(TOther a)
			{
				return (T) Integer.valueOf(a.intValue());
			}
		}, FLOAT(Float.class) {
			@Override
			<T extends Number> T opAdd(T a, T b)
			{
				return (T) Float.valueOf(a.floatValue()+b.floatValue());
			}

			@Override
			<T, TOther extends Number> T cast(TOther a)
			{
				return (T) Float.valueOf(a.floatValue());
			}
		}, LONG(Long.class) {
			@Override
			<T extends Number> T opAdd(T a, T b)
			{
				return (T) Long.valueOf(a.longValue()+b.longValue());
			}

			@Override
			<T, TOther extends Number> T cast(TOther a)
			{
				return (T) Long.valueOf(a.longValue());			}
		};
		
		private Class<? extends Number> type;
		private Types(Class<? extends Number> t) {
			type = t;
		}
		private static Types lookUp(Class<? extends Number> t) {
			for(Types typ : Types.values()) {
				if(typ.type==t) return typ;
			}
			throw new RuntimeException();
		}
		abstract <T extends Number> T opAdd(T a, T b);
		abstract <T, TOther extends Number> T cast(TOther a);
	}

	public static class ai_real<T extends Number> {
		
		private T value;
		public ai_real(T val) {
			value=val;
		}
		public T getValue() {
			return value;
		}
		public T opAdd(T a) {
			return Types.lookUp(a.getClass()).opAdd(value, a);
		}
		public ai_real<T> opAdd(ai_real<T> a) {
			return new ai_real<T>(Types.lookUp(a.value.getClass()).opAdd(value, a.value));
		}
		public <TOther extends Number> ai_real<T> cast(TOther a) {
			return new ai_real<T>(Types.lookUp(value.getClass()).cast(a));
		}
		@Override
		public String toString() {
			return value.toString();
		}
		
	}
	
	public abstract static class aiMatrix4x4t<T extends ai_real> {
		public ai_real a1, a2, a3, a4;
		public ai_real b1, b2, b3, b4;
		public ai_real c1, c2, c3, c4;
		public ai_real d1, d2, d3, d4;
		
		public aiMatrix4x4t() {
			
		}
		
	    public aiMatrix4x4t (  ai_real _a1, ai_real _a2, ai_real _a3, ai_real _a4,
                ai_real _b1, ai_real _b2, ai_real _b3, ai_real _b4,
                ai_real _c1, ai_real _c2, ai_real _c3, ai_real _c4,
                ai_real _d1, ai_real _d2, ai_real _d3, ai_real _d4) {
	    	a1=_a1;
	    	a2=_a2;
	    	a3=_a3;
	    	a4=_a4;
	    	b1=_b1;
	    	b2=_b2;
	    	b3=_b3;
	    	b4=_b4;
	    	c1=_c1;
	    	c2=_c2;
	    	c3=_c3;
	    	c4=_c4;
	    	d1=_d1;
	    	d2=_d2;
	    	d3=_d3;
	    	d4=_d4;
	    }
	    
	    //Todo cast function.
	    
	    public aiMatrix4x4t(aiMatrix3x3t<ai_real> m) {
	    	a1=m.a1;a2=m.a2;a3=m.a3;a4=a1.cast(0.0);
	    	b1=m.b1;b2=m.b2;b3=m.b3;b4=a1.cast(0.0);
	    	c1=m.c1;c2=m.c2;c3=m.c3;c4=a1.cast(0.0);
	    	d1=a1.cast(0.0);d2=a1.cast(0.0);d3=a1.cast(0.0);d4=a1.cast(0.0);
	    }
		
	}
	
	public static class aiMatrix3x3t<TReal extends ai_real> {
		TReal a1, a2, a3;
		TReal b1, b2, b3;
		TReal c1, c2, c3;
	}
	
	public static class aiMatrix4x4<T extends ai_real> extends aiMatrix4x4t<T> {
		
	}
	
	public static void main(String[] args) {
		ai_real<Double> a = new ai_real<Double>(1.0);
		ai_real<Double> b = new ai_real<Double>(5.0);
		System.out.println(a.opAdd(b.value));
		System.out.println(a.opAdd(b));
	}
	
}
