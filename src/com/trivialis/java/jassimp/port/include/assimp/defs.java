package com.trivialis.java.jassimp.port.include.assimp;

public class defs {

	public static boolean DOUBLE_PRECISION=false;

	private enum Types {
		DOUBLE(0, Double.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.doubleValue()+b.doubleValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.doubleValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.doubleValue()*b.doubleValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.doubleValue()-b.doubleValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.doubleValue()/b.doubleValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.doubleValue()>b.doubleValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.doubleValue()<b.doubleValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.doubleValue()==b.doubleValue();
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
				return -1*a.doubleValue();
			}
		},
//		INTEGER(3, Integer.class) {
//			@Override
//			Number opAdd(Number a, Number b)
//			{
//				return a.intValue()+b.intValue();
//			}
//
//			@Override
//			Number forValue(Number a)
//			{
//				return a.intValue();
//			}
//
//			@Override
//			Number opMultiply(Number a, Number b)
//			{
//				return a.intValue()*b.intValue();
//			}
//
//			@Override
//			Number opSubtract(Number a, Number b)
//			{
//				return a.intValue()-b.intValue();
//			}
//
//			@Override
//			Number opDivide(Number a, Number b)
//			{
//				return a.intValue()/b.intValue();
//			}
//
//			@Override
//			boolean opBigger(Number a, Number b)
//			{
//				return a.intValue()>b.intValue();
//			}
//
//			@Override
//			boolean opSmaller(Number a, Number b)
//			{
//				return a.intValue()<b.intValue();
//			}
//
//			@Override
//			boolean opEquals(Number a, Number b)
//			{
//				return a.intValue()==b.intValue();
//			}
//
//			@Override
//			Number NaN()
//			{
//				return Integer.MAX_VALUE;
//			}
//
//			@Override
//			Number getInfinity()
//			{
//				return Integer.MAX_VALUE;
//			}
//
//			@Override
//			Number opNegate(Number a)
//			{
//				return -1*a.intValue();
//			}
//		},
		FLOAT(1, Float.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.floatValue()+b.floatValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.floatValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.floatValue()*b.floatValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.floatValue()-b.floatValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.floatValue()/b.floatValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.floatValue()>b.floatValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.floatValue()<b.floatValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.floatValue()==b.floatValue();
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
				return -1*a.floatValue();
			}
		},
//		LONG(2, Long.class) {
//			@Override
//			Number opAdd(Number a, Number b)
//			{
//				return a.longValue()+b.longValue();
//			}
//
//			@Override
//			Number forValue(Number a)
//			{
//				return a.longValue();
//			}
//
//			@Override
//			Number opMultiply(Number a, Number b)
//			{
//				return a.longValue()*b.longValue();
//			}
//
//			@Override
//			Number opSubtract(Number a, Number b)
//			{
//				return a.longValue()-b.longValue();
//			}
//
//			@Override
//			Number opDivide(Number a, Number b)
//			{
//				return a.longValue()/b.longValue();
//			}
//
//			@Override
//			boolean opBigger(Number a, Number b)
//			{
//				return a.longValue()>b.longValue();
//			}
//
//			@Override
//			boolean opSmaller(Number a, Number b)
//			{
//				return a.longValue()<b.longValue();
//			}
//
//			@Override
//			boolean opEquals(Number a, Number b)
//			{
//				return a.longValue()==b.longValue();
//			}
//
//			@Override
//			Number NaN()
//			{
//				return Long.MAX_VALUE;
//			}
//
//			@Override
//			Number getInfinity()
//			{
//				return Long.MAX_VALUE;
//			}
//
//			@Override
//			Number opNegate(Number a)
//			{
//				return -1*a.longValue();
//			}
//		}
		;


		//private static final Class<? extends Number>[] HIERARCHY = new Class[]{Double.class, Float.class, Long.class, Integer.class};

		private Class<? extends Number> type;

		private int rank;
		private Types(int r, Class<? extends Number> t) {
			rank  = r;
			type = t;
		}
		private static Types lookUp(Class<? extends Number> t) {
			for(Types typ : Types.values()) {
				if(typ.type==t) return typ;
			}
                        throw new RuntimeException("Type not found: " + t.getName());
//			return null;
		}

//		private static Types lookUpHighest(Class<? extends Number>... t) {
//			Types result = null;
//			for(Class<? extends Number> typ : t) {
//				Types subresult = lookUp(typ);
//				if(result==null) result = subresult;
//				if(subresult.rank<result.rank) result = subresult;
//			}
//			if(result==null) throw new RuntimeException();
//			return result;
//		}

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

	public static class ai_real {

		private Number value;
		private final Types type;

		private ai_real(Number val, Types t) {
			value=val;
			type=t;
		}

		public static int getSize() {
			return defs.DOUBLE_PRECISION?8:4;
		}

		private static Number enforce(Number val) {
			if(DOUBLE_PRECISION) {
				if(val instanceof Float)
					return val.doubleValue();
			} else {
				if(val instanceof Double)
					return val.floatValue();
			}
			return val;
		}

		public ai_real(Number val) {
			value=enforce(val);
			type=Types.lookUp(val.getClass());
		}
		public ai_real(Float val) {
			value=enforce(val);
			type=Types.lookUp(val.getClass());
		}
		public ai_real(Double val) {
			value=enforce(val);
			type=Types.lookUp(val.getClass());
		}
//		public ai_real(Integer val) {
//			this(val, Types.INTEGER);
//		}
//		public ai_real(Long val) {
//			this(val, Types.LONG);
//		}

		@Override
		public String toString() {
			return value.toString();
		}
		public Number getValue() {
			return value;
		}

		private Types highest(Types... types) {
			Types result = null;
			for(Types t : types) {
				if(result==null) result = t;
                                if(t==null) result = Types.FLOAT;
				if(result.rank>=t.rank) result = t;
			}
			return result;
		}
		
		public void setValue(ai_real a) {
			value=type.forValue(a.value);
		}

		public ai_real opAdd(ai_real a)
		{
			return new ai_real(highest(highest(type, a.type), a.type).opAdd(value, a.value));
		}

		public ai_real opSubtract(ai_real a)
		{
			return new ai_real(highest(type, a.type).opSubtract(value, a.value));
		}

		public ai_real opMultiply(ai_real a)
		{
			return new ai_real(highest(type, a.type).opMultiply(value, a.value));
		}

		public ai_real opDivide(ai_real a)
		{
			return new ai_real(highest(type, a.type).opDivide(value, a.value));
		}

		public boolean opBigger(ai_real a)
		{
			return type.opBigger(value, a.value);
		}

		public ai_real forValue(Number a)
		{
			return new ai_real(type.forValue(a));
		}

		public ai_real cast(ai_real a)
		{
			return new ai_real(a.type.forValue(value));
		}

		public boolean opEquals(ai_real a)
		{
			return type.opEquals(value, a.value);
		}

		public ai_real getNaN()
		{
			return new ai_real(type.NaN());
		}

		public boolean opSmaller(ai_real r)
		{
			return type.opSmaller(value, r.value);
		}

		public boolean opSmallerOrEqual(ai_real o)
		{
			return opSmaller(o) || opEquals(o);
		}

		public ai_real getInfinity()
		{
			return new ai_real(type.getInfinity());
		}

		public ai_real opNegate()
		{
			return new ai_real(type.opNegate(value));
		}





	}
	
	private enum Types2 {
		DOUBLE(0, Double.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.doubleValue()+b.doubleValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.doubleValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.doubleValue()*b.doubleValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.doubleValue()-b.doubleValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.doubleValue()/b.doubleValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.doubleValue()>b.doubleValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.doubleValue()<b.doubleValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.doubleValue()==b.doubleValue();
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
				return -1*a.doubleValue();
			}
		},
		INTEGER(3, Integer.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.intValue()+b.intValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.intValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.intValue()*b.intValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.intValue()-b.intValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.intValue()/b.intValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.intValue()>b.intValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.intValue()<b.intValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.intValue()==b.intValue();
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
				return -1*a.intValue();
			}
		},
		FLOAT(1, Float.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.floatValue()+b.floatValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.floatValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.floatValue()*b.floatValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.floatValue()-b.floatValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.floatValue()/b.floatValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.floatValue()>b.floatValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.floatValue()<b.floatValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.floatValue()==b.floatValue();
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
				return -1*a.floatValue();
			}
		},
		LONG(2, Long.class) {
			@Override
			Number opAdd(Number a, Number b)
			{
				return a.longValue()+b.longValue();
			}

			@Override
			Number forValue(Number a)
			{
				return a.longValue();
			}

			@Override
			Number opMultiply(Number a, Number b)
			{
				return a.longValue()*b.longValue();
			}

			@Override
			Number opSubtract(Number a, Number b)
			{
				return a.longValue()-b.longValue();
			}

			@Override
			Number opDivide(Number a, Number b)
			{
				return a.longValue()/b.longValue();
			}

			@Override
			boolean opBigger(Number a, Number b)
			{
				return a.longValue()>b.longValue();
			}

			@Override
			boolean opSmaller(Number a, Number b)
			{
				return a.longValue()<b.longValue();
			}

			@Override
			boolean opEquals(Number a, Number b)
			{
				return a.longValue()==b.longValue();
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
				return -1*a.longValue();
			}
		}
		;


		//private static final Class<? extends Number>[] HIERARCHY = new Class[]{Double.class, Float.class, Long.class, Integer.class};

		private Class<? extends Number> type;

		private int rank;
		private Types2(int r, Class<? extends Number> t) {
			rank  = r;
			type = t;
		}
		private static Types2 lookUp(Class<? extends Number> t) {
			for(Types2 typ : Types2.values()) {
				if(typ.type==t) return typ;
			}
                        throw new RuntimeException("Type not found: " + t.getName());
//			return null;
		}

//		private static Types lookUpHighest(Class<? extends Number>... t) {
//			Types result = null;
//			for(Class<? extends Number> typ : t) {
//				Types subresult = lookUp(typ);
//				if(result==null) result = subresult;
//				if(subresult.rank<result.rank) result = subresult;
//			}
//			if(result==null) throw new RuntimeException();
//			return result;
//		}

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

		private Real(Number val, Types2 t) {
			value=val;
			type=t;
		}

		public static int getSize() {
			return defs.DOUBLE_PRECISION?8:4;
		}

		private static Number enforce(Number val) {
			if(DOUBLE_PRECISION) {
				if(val instanceof Float)
					return val.doubleValue();
			} else {
				if(val instanceof Double)
					return val.floatValue();
			}
			return val;
		}

		public Real(Number val) {
			value=enforce(val);
			type=Types2.lookUp(val.getClass());
		}
		public Real(Float val) {
			value=enforce(val);
			type=Types2.lookUp(val.getClass());
		}
		public Real(Double val) {
			value=enforce(val);
			type=Types2.lookUp(val.getClass());
		}
//		public ai_real(Integer val) {
//			this(val, Types.INTEGER);
//		}
//		public ai_real(Long val) {
//			this(val, Types.LONG);
//		}

		@Override
		public String toString() {
			return value.toString();
		}
		public Number getValue() {
			return value;
		}

		private Types2 highest(Types2... types) {
			Types2 result = null;
			for(Types2 t : types) {
				if(result==null) result = t;
                                if(t==null) result = Types2.FLOAT;
				if(result.rank>=t.rank) result = t;
			}
			return result;
		}
		
		public void setValue(Real a) {
			value=type.forValue(a.value);
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


	public static void main(String[] args) {
//		ai_real<Double> a = new ai_real<Double>(1.0);
//		ai_real<Double> b = new ai_real<Double>(5.0);
		//System.out.println(a.opAdd(b.value));
		//System.out.println(a.opAdd(b));
	}

}
