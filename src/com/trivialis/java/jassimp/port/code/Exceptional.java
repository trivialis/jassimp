package com.trivialis.java.jassimp.port.code;

public class Exceptional {

	public static class DeadlyImportError extends RuntimeException {

		/**
		 *
		 */
		private static final long serialVersionUID = 9154124124823900117L;
		public DeadlyImportError(String message) {
			super(message);
		}
	}

}
