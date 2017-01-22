package com.trivialis.java.jassimp.util;

public class Tuples {

	public static class Tuple<X, Y, Z> {
		public X x;
		public Y y;
		public Z z;

		public Tuple(X x, Y y, Z z) {
			this.x=x;this.y=y;this.z=z;
		}
	}

}
