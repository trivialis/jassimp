package com.trivialis.java.jassimp.util;

public interface IPointer<T> {

	IPointer<T> pointerCopy();

	IPointer<T> pointerOffset(int cnt);

	IPointer<T> pointerAdjust(int cnt);

	IPointer<T> pointerPostInc();

	IPointer<T> pointerPostDec();

	IPointer<IPointer<T>> pointerAddressOf();

	int pointerCompare();

	T get();

	T set(T value);

	T[] deep();

	boolean canInc();

	boolean canDec();

}
