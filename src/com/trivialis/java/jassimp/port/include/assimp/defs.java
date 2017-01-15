package com.trivialis.java.jassimp.port.include.assimp;

import com.trivialis.java.jassimp.port.include.assimp.defs.ai_real;

public class defs {

	public static interface ai_real {

		ai_real opAdd(ai_real a);
		ai_real opSubtract(ai_real a);
		ai_real opMultiply(ai_real a);
		ai_real opDivide(ai_real a);
		boolean opEquals(ai_real a);
		boolean opSmaller(ai_real a);
		float getFloatValue();
		int getIntValue();
		double getDoubleValue();
		<T extends ai_real> ai_real cast(T o);
		<T extends Number> ai_real forValue(T value);
	}
	
	public static class ai_real_float implements ai_real {

		private float value;

		public ai_real_float(float f) {
			this.value = f;
		}
		
		public float getValue() {
			return value;
		}
		
		@Override
		public ai_real opAdd(ai_real a)
		{
			return new ai_real_float(value+a.getFloatValue());
		}

		@Override
		public ai_real opSubtract(ai_real a)
		{
			return new ai_real_float(value-a.getFloatValue());
		}

		@Override
		public ai_real opMultiply(ai_real a)
		{
			return new ai_real_float(value*a.getFloatValue());
		}

		@Override
		public ai_real opDivide(ai_real a)
		{
			return new ai_real_float(value/a.getFloatValue());
		}

		@Override
		public boolean opEquals(ai_real a)
		{
			return value==a.getFloatValue();
		}

		@Override
		public float getFloatValue()
		{
			return value;
		}

		@Override
		public int getIntValue()
		{
			return (int) value;
		}

		@Override
		public double getDoubleValue()
		{
			return value;
		}

		@Override
		public boolean opSmaller(ai_real a)
		{
			return value < a.getFloatValue();
		}

		@Override
		public <T extends ai_real> ai_real cast(T o)
		{
			if(o instanceof ai_real_double) {
				return new ai_real_double(value);
			}
			if(o instanceof ai_real_float) {
				return new ai_real_float(value);
			}
			return null;
		}
		
		@Override
		public <T extends Number> ai_real forValue(T value) {
			return new ai_real_float((float) value);
		}
		
//		@Override
//		public <T extends ai_real> ai_real castNew(T value)
//		{
//			if(value instanceof Double) {
//				return new ai_real_double(value);
//			}
//			if(value instanceof Float) {
//				return new ai_real_float(value);
//			}
//			return null;
//		}

	}
	
	public static class ai_real_double implements ai_real {

		private double value;

		public ai_real_double(double f) {
			this.value = f;
		}
		
		public double getValue() {
			return value;
		}
		
		@Override
		public ai_real opAdd(ai_real a)
		{
			return new ai_real_double(value+a.getDoubleValue());
		}

		@Override
		public ai_real opSubtract(ai_real a)
		{
			return new ai_real_double(value-a.getDoubleValue());
		}

		@Override
		public ai_real opMultiply(ai_real a)
		{
			return new ai_real_double(value*a.getDoubleValue());
		}

		@Override
		public ai_real opDivide(ai_real a)
		{
			return new ai_real_double(value/a.getDoubleValue());
		}

		@Override
		public boolean opEquals(ai_real a)
		{
			return value==a.getDoubleValue();
		}

		@Override
		public float getFloatValue()
		{
			return (float) value;
		}

		@Override
		public int getIntValue()
		{
			return (int) value;
		}

		@Override
		public double getDoubleValue()
		{
			return value;
		}

		@Override
		public boolean opSmaller(ai_real a)
		{
			return value < a.getDoubleValue();
		}
		
		@Override
		public <T extends Number> ai_real forValue(T value) {
			return new ai_real_double((double) value);
		}
		
		@Override
		public <T extends ai_real> ai_real cast(T o)
		{
			if(o instanceof ai_real_double) {
				return new ai_real_double(value);
			}
			if(o instanceof ai_real_float) {
				return new ai_real_float((float) value);
			}
			return null;
		}
		
	}
	
	
}
