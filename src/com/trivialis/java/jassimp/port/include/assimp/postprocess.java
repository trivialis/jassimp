package com.trivialis.java.jassimp.port.include.assimp;

public class postprocess {
	
	public static int aiProcess_MakeLeftHanded = 0x4;
	
	public enum aiPostProcessSteps {
		aiProcess_MakeLeftHanded(0x4);
		
		private int value;

		private aiPostProcessSteps(int value) {
			this.value=value;
		}
		
		public int getValue() {
			return this.value;
		}
	}

}
