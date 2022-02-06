package com.revature.bank.util;

import java.util.Arrays;

public class ArrayList<T> implements List<T> {
	private static final int INITIAL_CAPACITY = 10;
	private int size = 0;
	private Object elementData[] = {};

	public ArrayList() {
		super();
		this.elementData = new Object[INITIAL_CAPACITY];
		;
	}

	@Override
	public boolean add(T element) {
		if (size == elementData.length) {
			ensureCapacity();
		}
		elementData[size++] = element;
		return false;
	}

	private void ensureCapacity() {
		int newIncreasedCapacity = elementData.length * 2;
		elementData = Arrays.copyOf(elementData, newIncreasedCapacity);

	}

	@Override
	public boolean contains(T element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(T element) {
		int index = foundIndexOfElement(element);
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
		}
		for (int i = index; i < size - 1; i++) {
			elementData[i] = elementData[i + 1];
		}
		size--;
		return true;
	}

	private int foundIndexOfElement(T element) {
		for (int i = 0; i < elementData.length; i++) {
			if (elementData[i] == element) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
		}
		return (T) elementData[index];
	}

	@Override
	public String toString() {
		String data = "";
		for (int i = 0; i < size; i++) {
          data += elementData[i] + "\n";
     }
		return data;
	}

}
