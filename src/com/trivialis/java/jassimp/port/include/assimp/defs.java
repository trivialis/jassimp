package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;

public class defs {
	@SuppressWarnings("unchecked")
	private enum Types {
		DOUBLE(Double.class) {
			@Override
			<T extends Number> T opAdd(T a, T b)
			{
				return (T) Double.valueOf(a.doubleValue()+b.doubleValue());
			}

			@Override
			<T extends Number, TOther extends Number> T forValue(TOther a)
			{
				return (T) Double.valueOf(a.doubleValue());
			}

			@Override
			<T extends Number> T opMultiply(T a, T b)
			{
				return (T) Double.valueOf(a.doubleValue()*b.doubleValue());
			}

			@Override
			<T extends Number> T opSubtract(T a, T b)
			{
				return (T) Double.valueOf(a.doubleValue()-b.doubleValue());
			}

			@Override
			<T extends Number> T opDivide(T a, T b)
			{
				return (T) Double.valueOf(a.doubleValue()/b.doubleValue());
			}

			@Override
			<T extends Number> boolean opBigger(T a, T b)
			{
				return a.doubleValue()>b.doubleValue();
			}

			@Override
			<T extends Number> boolean opSmaller(T a, T b)
			{
				return a.doubleValue()<b.doubleValue();
			}

			@Override
			<T extends Number> boolean opEquals(T a, T b)
			{
				return a.doubleValue()==b.doubleValue();
			}

			@Override
			<T extends Number> T NaN()
			{
				 return (T) Double.valueOf(Double.NaN);
			}
		}, INTEGER(Integer.class) {
			@Override
			<T extends Number> T opAdd(T a, T b)
			{
				return (T) Integer.valueOf(a.intValue()+b.intValue());
			}

			@Override
			<T extends Number, TOther extends Number> T forValue(TOther a)
			{
				return (T) Integer.valueOf(a.intValue());
			}

			@Override
			<T extends Number> T opMultiply(T a, T b)
			{
				return (T) Integer.valueOf(a.intValue()*b.intValue());
			}

			@Override
			<T extends Number> T opSubtract(T a, T b)
			{
				return (T) Integer.valueOf(a.intValue()-b.intValue());
			}

			@Override
			<T extends Number> T opDivide(T a, T b)
			{
				return (T) Integer.valueOf(a.intValue()/b.intValue());
			}

			@Override
			<T extends Number> boolean opBigger(T a, T b)
			{
				return a.intValue()>b.intValue();
			}

			@Override
			<T extends Number> boolean opSmaller(T a, T b)
			{
				return a.intValue()<b.intValue();
			}

			@Override
			<T extends Number> boolean opEquals(T a, T b)
			{
				return a.intValue()==b.intValue();
			}

			@Override
			<T extends Number> T NaN()
			{
				return (T) Integer.valueOf(Integer.MAX_VALUE);
			}
		}, FLOAT(Float.class) {
			@Override
			<T extends Number> T opAdd(T a, T b)
			{
				return (T) Float.valueOf(a.floatValue()+b.floatValue());
			}

			@Override
			<T extends Number, TOther extends Number> T forValue(TOther a)
			{
				return (T) Float.valueOf(a.floatValue());
			}

			@Override
			<T extends Number> T opMultiply(T a, T b)
			{
				return (T) Float.valueOf(a.floatValue()*b.floatValue());
			}

			@Override
			<T extends Number> T opSubtract(T a, T b)
			{
				return (T)Float.valueOf(a.floatValue()-b.floatValue());
			}

			@Override
			<T extends Number> T opDivide(T a, T b)
			{
				return (T)Float.valueOf(a.floatValue()/b.floatValue());
			}

			@Override
			<T extends Number> boolean opBigger(T a, T b)
			{
				return a.floatValue()>b.floatValue();
			}

			@Override
			<T extends Number> boolean opSmaller(T a, T b)
			{
				return a.floatValue()<b.floatValue();
			}

			@Override
			<T extends Number> boolean opEquals(T a, T b)
			{
				return a.floatValue()==b.floatValue();
			}

			@Override
			<T extends Number> T NaN()
			{
				return (T) Float.valueOf(Float.NaN);
			}
		}, LONG(Long.class) {
			@Override
			<T extends Number> T opAdd(T a, T b)
			{
				return (T) Long.valueOf(a.longValue()+b.longValue());
			}

			@Override
			<T extends Number, TOther extends Number> T forValue(TOther a)
			{
				return (T) Long.valueOf(a.longValue());			}

			@Override
			<T extends Number> T opMultiply(T a, T b)
			{
				return (T) Long.valueOf(a.longValue()*b.longValue());
			}

			@Override
			<T extends Number> T opSubtract(T a, T b)
			{
				return (T) Long.valueOf(a.longValue()-b.longValue());
			}

			@Override
			<T extends Number> T opDivide(T a, T b)
			{
				return (T) Long.valueOf(a.longValue()/b.longValue());
			}

			@Override
			<T extends Number> boolean opBigger(T a, T b)
			{
				return a.longValue()>b.longValue();
			}

			@Override
			<T extends Number> boolean opSmaller(T a, T b)
			{
				return a.longValue()<b.longValue();
			}

			@Override
			<T extends Number> boolean opEquals(T a, T b)
			{
				return a.longValue()==b.longValue();
			}

			@Override
			<T extends Number> T NaN()
			{
				return (T) Long.valueOf(Long.MAX_VALUE);
			}
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
		abstract <T extends Number> T opMultiply(T a, T b);
		abstract <T extends Number> T opAdd(T a, T b);
		abstract <T extends Number> T opSubtract(T a, T b);
		abstract <T extends Number> T opDivide(T a, T b);
		abstract <T extends Number> boolean opBigger(T a, T b);
		abstract <T extends Number> boolean opSmaller(T a, T b);
		abstract <T extends Number> boolean opEquals(T a, T b);
		abstract <T extends Number, TOther extends Number> T forValue(TOther a);
		abstract <T extends Number> T NaN();
	}

	public static class ai_real<T extends Number> {

		private T value;
		public ai_real(T val) {
			value=val;
		}
		@Override
		public String toString() {
			return value.toString();
		}
		public T getValue() {
			return value;
		}
		public T opAdd(T a) {
			return Types.lookUp(a.getClass()).opAdd(value, a);
		}
		public <TOther extends Number> ai_real<T> opAdd2(ai_real<TOther> a) {
			return this.forValue(Types.lookUp(a.value.getClass()).opAdd(value, a.value));
		}
		public ai_real<T> opAdd(ai_real<T> a) {
			return new ai_real<T>(Types.lookUp(value.getClass()).opAdd(value, a.value));
		}
		public ai_real<T> opSubtract(ai_real<T> a)
		{
			return new ai_real<T>(Types.lookUp(value.getClass()).opSubtract(value, a.value));
		}
		public ai_real<T> opMultiply2(ai_real<? extends Number> a) {
			return this.forValue((Types.lookUp(value.getClass()).opMultiply(value, a.value)));
		}
		public ai_real<T> opMultiply(ai_real<T> a) {
			return new ai_real<T>(Types.lookUp(value.getClass()).opMultiply(value, a.value));
		}
		public ai_real<T> opDivide(ai_real<T> a)
		{
			return new ai_real<T>(Types.lookUp(value.getClass()).opDivide(value, a.value));
		}
		public <TOther extends Number> boolean opBigger2(ai_real<TOther> a)
		{
			return Types.lookUp(value.getClass()).opBigger(value, a.value);
		}
		public boolean opBigger(ai_real<T> a)
		{
			return Types.lookUp(value.getClass()).opBigger(value, a.value);
		}
		public <TOther extends Number> ai_real<T> forValue(TOther a) {
			return new ai_real<T>(Types.lookUp(value.getClass()).forValue(a));
		}
		public <TOther extends Number> ai_real<TOther> cast(TOther a) {
			return new ai_real<TOther>(Types.lookUp(a.getClass()).forValue(value));
		}
		public boolean opEquals(ai_real<T> a)
		{
			return Types.lookUp(value.getClass()).opEquals(value, a.value);
		}
		public ai_real<T> getNaN() {
			return new ai_real<T>(Types.lookUp(value.getClass()).NaN());
		}
		public boolean opSmaller(ai_real<T> r)
		{
			return Types.lookUp(value.getClass()).opSmaller(value, r.value);
		}
		public boolean opSmallerOrEqual(ai_real<T> o)
		{
			return opSmaller(o)||opEquals(o);
		}
		public void setValue(ai_real<T> value)
		{
			this.value = value.value;
		}
		public void setValue(T value)
		{
			this.value = value;
		}



	}


	public static void main(String[] args) {
		ai_real<Double> a = new ai_real<Double>(1.0);
		ai_real<Double> b = new ai_real<Double>(5.0);
		System.out.println(a.opAdd(b.value));
		System.out.println(a.opAdd(b));
	}

}
