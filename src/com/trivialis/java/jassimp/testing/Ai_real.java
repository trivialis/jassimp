package com.trivialis.java.jassimp.testing;

public abstract class Ai_real {

	public static Mode MODE;
	
	public static enum Mode {
		FLOAT,DOUBLE;
	}
	
	public static Ai_real newInstance(Number value) {
		switch(MODE) {
			case DOUBLE:
				return new aiDouble(value);
			case FLOAT:
				return new aiFloat(value);
			default:
				throw new RuntimeException();
		}
	}
	
	abstract Number opMultiply(Number a);
	abstract Number opAdd(Number a);
	abstract Number opSubtract(Number a);
	abstract Number opDivide(Number a);
	abstract boolean opBigger(Number a);
	abstract boolean opSmaller(Number a);
	abstract boolean opEquals(Number a);
	abstract Number NaNNumber();
	abstract Number getInfinityNumber();
	abstract Number opNegateNumber();
	abstract Ai_real forValue(Number value);
	
	abstract Ai_real opMultiply(Ai_real a);
	abstract Ai_real opAdd(Ai_real a);
	abstract Ai_real opSubtract(Ai_real a);
	abstract Ai_real opDivide(Ai_real a);
	abstract boolean opBigger(Ai_real a);
	abstract boolean opSmaller(Ai_real a);
	abstract boolean opEquals(Ai_real a);
	abstract Ai_real NaN();
	abstract Ai_real getInfinity();
	abstract Ai_real opNegate();

	protected Number _value;
	
	private static class aiDouble extends Ai_real {
		
		private final double value;

		public aiDouble(Number value)
		{
			this.value=value.doubleValue();
			this._value=value.doubleValue();
		}

		@Override
		Number opMultiply(Number a)
		{
			return value*a.doubleValue();
		}

		@Override
		Number opAdd(Number a)
		{
			return value+a.doubleValue();
		}

		@Override
		Number opSubtract(Number a)
		{
			return value-a.doubleValue();
		}

		@Override
		Number opDivide(Number a)
		{
			return value/a.doubleValue();
		}

		@Override
		boolean opBigger(Number a)
		{
			return value>a.doubleValue();
		}

		@Override
		boolean opSmaller(Number a)
		{
			return value<a.doubleValue();
		}

		@Override
		boolean opEquals(Number a)
		{
			return value==a.doubleValue();
		}

		@Override
		Number NaNNumber()
		{
			return Double.NaN;
		}

		@Override
		Number getInfinityNumber()
		{
			return Double.POSITIVE_INFINITY;
		}

		@Override
		Number opNegateNumber()
		{
			return value*-1;
		}

		@Override
		Ai_real forValue(Number value)
		{
			return new aiDouble(value);
		}

		@Override
		Ai_real opMultiply(Ai_real a)
		{
			return forValue(opMultiply(a._value));
		}

		@Override
		Ai_real opAdd(Ai_real a)
		{
			return forValue(opAdd(a._value));
		}

		@Override
		Ai_real opSubtract(Ai_real a)
		{
			return forValue(opSubtract(a._value));
		}

		@Override
		Ai_real opDivide(Ai_real a)
		{
			return forValue(opDivide(a._value));
		}

		@Override
		boolean opBigger(Ai_real a)
		{
			return opBigger(a._value);
		}

		@Override
		boolean opSmaller(Ai_real a)
		{
			return opSmaller(a._value);
		}

		@Override
		boolean opEquals(Ai_real a)
		{
			return opEquals(a._value);
		}

		@Override
		Ai_real NaN()
		{
			return forValue(NaNNumber());
		}

		@Override
		Ai_real getInfinity()
		{
			return forValue(getInfinityNumber());
		}

		@Override
		Ai_real opNegate()
		{
			return forValue(opNegateNumber());
		}
		
	}
	
		private static class aiFloat extends Ai_real {
		
		private final float value;

		public aiFloat(Number value)
		{
			this.value=value.floatValue();
			this._value=value.floatValue();
		}

		@Override
		Number opMultiply(Number a)
		{
			return value*a.floatValue();
		}

		@Override
		Number opAdd(Number a)
		{
			return value+a.floatValue();
		}

		@Override
		Number opSubtract(Number a)
		{
			return value-a.floatValue();
		}

		@Override
		Number opDivide(Number a)
		{
			return value/a.floatValue();
		}

		@Override
		boolean opBigger(Number a)
		{
			return value>a.floatValue();
		}

		@Override
		boolean opSmaller(Number a)
		{
			return value<a.floatValue();
		}

		@Override
		boolean opEquals(Number a)
		{
			return value==a.floatValue();
		}

		@Override
		Number NaNNumber()
		{
			return Float.NaN;
		}

		@Override
		Number getInfinityNumber()
		{
			return Float.POSITIVE_INFINITY;
		}

		@Override
		Number opNegateNumber()
		{
			return value*-1;
		}

		@Override
		Ai_real forValue(Number value)
		{
			return new aiDouble(value);
		}

		@Override
		Ai_real opMultiply(Ai_real a)
		{
			return forValue(opMultiply(a._value));
		}

		@Override
		Ai_real opAdd(Ai_real a)
		{
			return forValue(opAdd(a._value));
		}

		@Override
		Ai_real opSubtract(Ai_real a)
		{
			return forValue(opSubtract(a._value));
		}

		@Override
		Ai_real opDivide(Ai_real a)
		{
			return forValue(opDivide(a._value));
		}

		@Override
		boolean opBigger(Ai_real a)
		{
			return opBigger(a._value);
		}

		@Override
		boolean opSmaller(Ai_real a)
		{
			return opSmaller(a._value);
		}

		@Override
		boolean opEquals(Ai_real a)
		{
			return opEquals(a._value);
		}

		@Override
		Ai_real NaN()
		{
			return forValue(NaNNumber());
		}

		@Override
		Ai_real getInfinity()
		{
			return forValue(getInfinityNumber());
		}

		@Override
		Ai_real opNegate()
		{
			return forValue(opNegateNumber());
		}
		
	}
	
	public static void main(String[] args) {
		Number a = 0.0D;
		aiDouble b = new aiDouble(0.0);
		int c = (int) (a.intValue()+b.value);
		System.out.println(c);;
	}
	

	
}
