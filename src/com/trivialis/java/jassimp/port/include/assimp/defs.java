package com.trivialis.java.jassimp.port.include.assimp;

public class defs {

	public static Mode MODE = Mode.DOUBLE;

	public static enum Mode {
		FLOAT(new aiFloat(), Float.BYTES), DOUBLE(new aiDouble(), Double.BYTES);
		private final ai_real_Proto math;
		private final int size;

		private Mode(ai_real_Proto m, int s)
		{
			math = m;
			size = s;
		}
	}

	public static class ai_real {
		
		@Override
		public String toString() {
			return value.toString();
		}

		private Number value;

		public ai_real(Number n)
		{
			this.value = n;
		}

		public static int getSize()
		{
			return MODE.size;
		}

		public Number getValue()
		{
			return value;
		}

		public Number opMultiply(Number a)
		{
			return MODE.math.opMultiply(value, a);
		}

		public Number opAdd(Number a)
		{
			return MODE.math.opAdd(value, a);
		}

		public Number opSubtract(Number a)
		{
			return MODE.math.opSubtract(value, a);
		}

		public Number opDivide(Number a)
		{
			return MODE.math.opDivide(value, a);
		}

		public boolean opBigger(Number a)
		{
			return MODE.math.opBigger(value, a);
		}

		public boolean opSmaller(Number a)
		{
			return MODE.math.opSmaller(value, a);
		}

		public boolean opEquals(Number a)
		{
			return MODE.math.opEquals(value, a);
		}

		public Number NaNNumber()
		{
			return MODE.math.NaNNumber();
		}

		public Number getInfinityNumber()
		{
			return MODE.math.getInfinityNumber();
		}

		public Number opNegateNumber()
		{
			return MODE.math.opNegateNumber(value);
		}

		public ai_real forValue(Number value)
		{
			return new ai_real(value);
		}

		public ai_real opMultiply(ai_real a)
		{
			return new ai_real(opMultiply(a.value));
		}

		public ai_real opAdd(ai_real a)
		{
			return new ai_real(opAdd(a.value));
		}

		public ai_real opSubtract(ai_real a)
		{
			return new ai_real(opSubtract(a.value));
		}

		public ai_real opDivide(ai_real a)
		{
			return new ai_real(opDivide(a.value));
		}

		public boolean opBigger(ai_real a)
		{
			return opBigger(a.value);
		}

		public boolean opSmaller(ai_real a)
		{
			return opSmaller(a.value);
		}

		public boolean opEquals(ai_real a)
		{
			return opEquals(a.value);
		}

		public ai_real NaN()
		{
			return new ai_real(MODE.math.NaNNumber());
		}

		public ai_real getInfinity()
		{
			return new ai_real(MODE.math.getInfinityNumber());
		}

		public ai_real opNegate()
		{
			return new ai_real(MODE.math.opNegateNumber(value));
		}

		public ai_real cast(ai_real ai_real)
		{
			if (ai_real.getValue() instanceof Double)
			{
				return new ai_real(ai_real.getValue().doubleValue());
			}
			if (ai_real.getValue() instanceof Float)
			{
				return new ai_real(ai_real.getValue().floatValue());
			}
			if (ai_real.getValue() instanceof Integer)
			{
				return new ai_real(ai_real.getValue().intValue());
			}
			if (ai_real.getValue() instanceof Long)
			{
				return new ai_real(ai_real.getValue().longValue());
			}
			throw new RuntimeException("Unknown Number type: " + ai_real.getValue().getClass());
		}

		public boolean opSmallerOrEqual(ai_real epsilon)
		{
			return opSmaller(epsilon) || opEquals(epsilon);
		}

	}

	public static abstract class ai_real_Proto {

		public abstract Number opMultiply(Number value, Number a);

		public abstract Number opAdd(Number value, Number a);

		public abstract Number opSubtract(Number value, Number a);

		public abstract Number opDivide(Number value, Number a);

		public abstract boolean opBigger(Number value, Number a);

		public abstract boolean opSmaller(Number value, Number a);

		public abstract boolean opEquals(Number value, Number a);

		public abstract Number NaNNumber();

		public abstract Number getInfinityNumber();

		public abstract Number opNegateNumber(Number value);

		public abstract int getSize();


	}

	static class aiDouble extends ai_real_Proto {

		@Override
		public Number opMultiply(Number value, Number a)
		{
			return value.doubleValue() * a.doubleValue();
		}

		@Override
		public Number opAdd(Number value, Number a)
		{
			return value.doubleValue() + a.doubleValue();
		}

		@Override
		public Number opSubtract(Number value, Number a)
		{
			return value.doubleValue() - a.doubleValue();
		}

		@Override
		public Number opDivide(Number value, Number a)
		{
			return value.doubleValue() / a.doubleValue();
		}

		@Override
		public boolean opBigger(Number value, Number a)
		{
			return value.doubleValue() > a.doubleValue();
		}

		@Override
		public boolean opSmaller(Number value, Number a)
		{
			return value.doubleValue() < a.doubleValue();
		}

		@Override
		public boolean opEquals(Number value, Number a)
		{
			return value.doubleValue() == a.doubleValue();
		}

		@Override
		public Number NaNNumber()
		{
			return Double.NaN;
		}

		@Override
		public Number getInfinityNumber()
		{
			return Double.POSITIVE_INFINITY;
		}

		@Override
		public Number opNegateNumber(Number value)
		{
			return value.doubleValue() * -1;
		}


		@Override
		public int getSize()
		{
			return 8;
		}

	}

	private static class aiFloat extends ai_real_Proto {

		@Override
		public Number opMultiply(Number value, Number a)
		{
			return value.floatValue() * a.floatValue();
		}

		@Override
		public Number opAdd(Number value, Number a)
		{
			return value.floatValue() + a.floatValue();
		}

		@Override
		public Number opSubtract(Number value, Number a)
		{
			return value.floatValue() - a.floatValue();
		}

		@Override
		public Number opDivide(Number value, Number a)
		{
			return value.floatValue() / a.floatValue();
		}

		@Override
		public boolean opBigger(Number value, Number a)
		{
			return value.floatValue() > a.floatValue();
		}

		@Override
		public boolean opSmaller(Number value, Number a)
		{
			return value.floatValue() < a.floatValue();
		}

		@Override
		public boolean opEquals(Number value, Number a)
		{
			return value.floatValue() == a.floatValue();
		}

		@Override
		public Number NaNNumber()
		{
			return Float.NaN;
		}

		@Override
		public Number getInfinityNumber()
		{
			return Float.POSITIVE_INFINITY;
		}

		@Override
		public Number opNegateNumber(Number value)
		{
			return value.floatValue() * -1;
		}

		@Override
		public int getSize()
		{
			return 4;
		}

	}

	private enum Types2 {
		DOUBLE(0, Double.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.doubleValue() + b.doubleValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.doubleValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.doubleValue() * b.doubleValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.doubleValue() - b.doubleValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.doubleValue() / b.doubleValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.doubleValue() > b.doubleValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.doubleValue() < b.doubleValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.doubleValue() == b.doubleValue();
			}

			@Override
			Number NaN()
			{
				return Double.NaN;
			}

			@Override
			Number getInfinity()
			{
				return Double.POSITIVE_INFINITY;
			}

			@Override
			Number opNegate(Number a)
			{
				return -1 * a.doubleValue();
			}
		},
		INTEGER(3, Integer.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.intValue() + b.intValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.intValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.intValue() * b.intValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.intValue() - b.intValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.intValue() / b.intValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.intValue() > b.intValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.intValue() < b.intValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.intValue() == b.intValue();
			}

			@Override
			Number NaN()
			{
				return Integer.MAX_VALUE;
			}

			@Override
			Number getInfinity()
			{
				return Integer.MAX_VALUE;
			}

			@Override
			Number opNegate(Number a)
			{
				return -1 * a.intValue();
			}
		},
		FLOAT(1, Float.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.floatValue() + b.floatValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.floatValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.floatValue() * b.floatValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.floatValue() - b.floatValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.floatValue() / b.floatValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.floatValue() > b.floatValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.floatValue() < b.floatValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.floatValue() == b.floatValue();
			}

			@Override
			Number NaN()
			{
				return Float.NaN;
			}

			@Override
			Number getInfinity()
			{
				return Float.POSITIVE_INFINITY;
			}

			@Override
			Number opNegate(Number a)
			{
				return -1 * a.floatValue();
			}
		},
		LONG(2, Long.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.longValue() + b.longValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.longValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.longValue() * b.longValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.longValue() - b.longValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.longValue() / b.longValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.longValue() > b.longValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.longValue() < b.longValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.longValue() == b.longValue();
			}

			@Override
			Number NaN()
			{
				return Long.MAX_VALUE;
			}

			@Override
			Number getInfinity()
			{
				return Long.MAX_VALUE;
			}

			@Override
			Number opNegate(Number a)
			{
				return -1 * a.longValue();
			}
		};


		// private static final Class<? extends Number>[] HIERARCHY = new
		// Class[]{Double.class, Float.class, Long.class, Integer.class};

		private Class<? extends Number> type;

		private int rank;

		private Types2(int r, Class<? extends Number> t)
		{
			rank = r;
			type = t;
		}

		private static Types2 lookUp(Class<? extends Number> t)
		{
			for (Types2 typ : Types2.values())
			{
				if (typ.type == t)
					return typ;
			}
			throw new RuntimeException("Type not found: " + t.getName());
			// return null;
		}

		// private static Types lookUpHighest(Class<? extends Number>... t) {
		// Types result = null;
		// for(Class<? extends Number> typ : t) {
		// Types subresult = lookUp(typ);
		// if(result==null) result = subresult;
		// if(subresult.rank<result.rank) result = subresult;
		// }
		// if(result==null) throw new RuntimeException();
		// return result;
		// }

		abstract Number opMultiply(Number a, Number b);

		abstract Number opAdd(Number a, Number b);

		abstract Number opSubtract(Number a, Number b);

		abstract Number opDivide(Number a, Number b);

		abstract boolean opBigger(Number a, Number b);

		abstract boolean opSmaller(Number a, Number b);

		abstract boolean opEquals(Number a, Number b);

		abstract Number forValue(Number a);

		abstract Number NaN();

		abstract Number getInfinity();

		abstract Number opNegate(Number a);


	}

	public static class Real {

		private Number value;
		private final Types2 type;

		public static int getSize()
		{
			return defs.MODE == Mode.DOUBLE ? 8 : 4;
		}

		private static Number enforce(Number val)
		{
			if (defs.MODE == Mode.DOUBLE)
			{
				if (val instanceof Float)
					return val.doubleValue();
			} else
			{
				if (val instanceof Double)
					return val.floatValue();
			}
			return val;
		}

		public Real(Number val)
		{
			value = enforce(val);
			type = Types2.lookUp(val.getClass());
		}

		public Real(Float val)
		{
			value = enforce(val);
			type = Types2.lookUp(val.getClass());
		}

		public Real(Double val)
		{
			value = enforce(val);
			type = Types2.lookUp(val.getClass());
		}
		// public ai_real_Proto(Integer val) {
		// this(val, Types.INTEGER);
		// }
		// public ai_real_Proto(Long val) {
		// this(val, Types.LONG);
		// }

		@Override
		public String toString()
		{
			return value.toString();
		}

		public Number getValue()
		{
			return value;
		}

		private Types2 highest(Types2... types)
		{
			Types2 result = null;
			for (Types2 t : types)
			{
				if (result == null)
					result = t;
				if (t == null)
					result = Types2.FLOAT;
				if (result.rank >= t.rank)
					result = t;
			}
			return result;
		}

		public void setValue(Real a)
		{
			value = type.forValue(a.value);
		}

		public Real opAdd(Real a)
		{
			return new Real(highest(highest(type, a.type), a.type).opAdd(value, a.value));
		}

		public Real opSubtract(Real a)
		{
			return new Real(highest(type, a.type).opSubtract(value, a.value));
		}

		public Real opMultiply(Real a)
		{
			return new Real(highest(type, a.type).opMultiply(value, a.value));
		}

		public Real opDivide(Real a)
		{
			return new Real(highest(type, a.type).opDivide(value, a.value));
		}

		public boolean opBigger(Real a)
		{
			return type.opBigger(value, a.value);
		}

		public Real forValue(Number a)
		{
			return new Real(type.forValue(a));
		}

		public Real cast(Real a)
		{
			return new Real(a.type.forValue(value));
		}

		public boolean opEquals(Real a)
		{
			return type.opEquals(value, a.value);
		}

		public Real getNaN()
		{
			return new Real(type.NaN());
		}

		public boolean opSmaller(Real r)
		{
			return type.opSmaller(value, r.value);
		}

		public boolean opSmallerOrEqual(Real o)
		{
			return opSmaller(o) || opEquals(o);
		}

		public Real getInfinity()
		{
			return new Real(type.getInfinity());
		}

		public Real opNegate()
		{
			return new Real(type.opNegate(value));
		}





	}


	public static void main(String[] args)
	{
		// ai_real_Proto<Double> a = new ai_real_Proto<Double>(1.0);
		// ai_real_Proto<Double> b = new ai_real_Proto<Double>(5.0);
		// System.out.println(a.opAdd(b.value));
		// System.out.println(a.opAdd(b));
	}

}
