package com.trivialis.java.jassimp.util;

public class Pointer<T> implements IPointer<T> {

	private final int size;
	private final T[] val;
	private int currentOffset;

	@SuppressWarnings("unchecked")
	private Pointer(int dim1)
	{
		val = (T[]) new Object[dim1];
		size=val.length;
	}

	@SuppressWarnings("unchecked")
	private Pointer(T value) {
		val=(T[]) new Object[]{value};
		currentOffset=0;
		size=val.length;
	}

	private Pointer(T[] value, int offset)
	{
		val = value;
		currentOffset = offset;
		size=val.length;
	}

	public static <T> Pointer<T> create(int dim1)
	{
		return new Pointer<T>(dim1);
	}

	public static <T> IPointer<T> valueOf(T value)
	{
		return new Pointer<T>(value);
	}

	public static <T> IPointer<T> valueOf(T[] value)
	{
		return new Pointer<T>(value, 0);
	}

	@Override
	public IPointer<T> pointerCopy()
	{
		return new Pointer<T>(val, currentOffset);
	}

	@Override
	public IPointer<T> pointerOffset(int cnt)
	{
		return new Pointer<T>(val, currentOffset + cnt);
	}

	@Override
	public IPointer<T> pointerAdjust(int cnt) {
		currentOffset += cnt;
		return this;
	}

	@Override
	public boolean canInc() {
		return currentOffset+1<size;
	}

	@Override
	public boolean canDec() {
		return currentOffset-1>0;
	}

	@Override
	public IPointer<T> postInc()
	{
		int temp = currentOffset++;
		//if(temp>=size) throw new ArrayIndexOutOfBoundsException(temp);
		return new Pointer<T>(val, temp);
	}

	@Override
	public IPointer<T> pointerPostDec()
	{
		int temp = currentOffset--;
		//if(temp<=0) throw new ArrayIndexOutOfBoundsException(temp);
		return new Pointer<T>(val, temp);
	}

	@Override
	public IPointer<IPointer<T>> pointerAddressOf()
	{
		return Pointer.valueOf((IPointer<T>) this);
	}

	@Override
	public int pointerCompare()
	{
		return currentOffset + 1;
	}
	
	@Override
	public T getAtOffset(int offset) {
		return val[currentOffset+offset];
	}

	@Override
	public T get()
	{
		return val[currentOffset];
	}

	@Override
	public T set(T value)
	{
		val[currentOffset] = value;
		return value;
	}

	@Override
	public T[] deep()
	{
		return val;
	}

	public static void main(String[] args) {
		StringUtil.toCharacterArray("Hello world!".toCharArray());
		System.out.println("");
	}

	@Override
	public boolean opSmaller(IPointer<Character> end)
	{
		return currentOffset < end.getOffset();
	}

	@Override
	public int getOffset()
	{
		return currentOffset;
	}

}
