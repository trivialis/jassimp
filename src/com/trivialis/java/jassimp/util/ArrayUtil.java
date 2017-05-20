package com.trivialis.java.jassimp.util;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class ArrayUtil {
	
	public static <T> ArrayList<T> toList(T[] array) {
		ArrayList<T> result = new ArrayList<>();
		for(T a : array) result.add(a);
		return result;
	}


	public static abstract class Generator<T extends Object> {

		private Class<T> type;

		public Generator()
		{
			this.type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}

		public T generate() throws InstantiationException, IllegalAccessException
		{
			return type.newInstance();
		}
		
		public static <T> ArrayList<T> ensureSize(ArrayList<T> list, int amount, Generator<T> g) {
			int i = list.size();
			try {
			while(i < amount) {
				i++;
				list.add(g.generate());
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		public static <T> ArrayList<T> populateList(int amount, Generator<T> g) {
			ArrayList<T> result = new ArrayList<T>(amount);
			int i = 0;
			try {
			while(i < amount) {
				i++;
				result.add(g.generate());
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		public static <T> T[] populateArray(T[] array, Generator<T> g)
		{
			try {
			for (int i = 0; i < array.length; i++)
			{
				array[i] = g.generate();
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return array;
		}


	}
	

	public static void main(String[] args) throws InstantiationException, IllegalAccessException
	{
		ArrayList[] s = Generator.populateArray(new ArrayList[10], new Generator<ArrayList>() {});
		System.out.println(s[0].equals(s[1]));
	}
	
}
