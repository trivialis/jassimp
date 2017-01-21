package com.trivialis.java.jassimp.port.include.assimp;

public class material {

	public enum aiShadingMode {
		aiShadingMode_Gouraud(0x2);

		public int value;

		private aiShadingMode(int val) {
			value = val;
		}
	}

	public static String AI_MATKEY_SHADING_MODEL = "$mat.shadingm\0\0";
	public static String AI_MATKEY_COLOR_EMISSIVE = "$clr.emissive\0\0";
	public static String AI_MATKEY_COLOR_SPECULAR;
	public static String AI_MATKEY_COLOR_DIFFUSE;
	public static String AI_MATKEY_SHININESS;
}
