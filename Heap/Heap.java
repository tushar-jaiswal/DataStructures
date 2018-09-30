//Author: Tushar Jaiswal
//Creation Date: 09/30/2018

import java.util.*;

public class DataStructures
{
	public static void main(String[] args) {
		int[] arr = { 2, 5, 7, 3, 7, 2, 1, 8, -3 };
		Heap minHeap = new MinHeap();
		Heap maxHeap = new MaxHeap();

		System.out.print("Elements added to min heap and max heap: ");
		for(int i : arr)
		{
			minHeap.add(i);
			maxHeap.add(i);
			System.out.print(" " + i);
		}
		System.out.println();
		System.out.println("Min Heap poll: " + minHeap.peek());
		System.out.println("Max Heap poll: " + maxHeap.peek());
		System.out.println("Min Heap poll: " + minHeap.poll());
		System.out.println("Max Heap poll: " + maxHeap.poll());
		System.out.println("Min Heap poll: " + minHeap.poll());
		System.out.println("Max Heap poll: " + maxHeap.poll());
	}
}

abstract class Heap
{
	private int capacity;
	protected int size;
	protected int[] items;

	public Heap()
	{
		capacity = 10;
		size = 0;
		items = new int[capacity];
	}

	protected int getLeftChildIndex(int parentIndex)
	{
		return 2 * parentIndex + 1;
	}

	protected int getRightChildIndex(int parentIndex)
	{
		return 2 * parentIndex + 2;
	}

	protected int getParentIndex(int childIndex)
	{
		if (childIndex == 0)
		{
			return -1;
		}
		return (childIndex - 1) / 2;
	}

	protected boolean hasLeftChild(int index)
	{
		return getLeftChildIndex(index) < size;
	}

	protected boolean hasRightChild(int index)
	{
		return getRightChildIndex(index) < size;
	}

	protected boolean hasParent(int index)
	{
		return getParentIndex(index) >= 0;
	}

	protected int leftChild(int index)
	{
		return items[getLeftChildIndex(index)];
	}

	protected int rightChild(int index)
	{
		return items[getRightChildIndex(index)];
	}

	protected int parent(int index)
	{
		return items[getParentIndex(index)];
	}

	protected void swap(int a, int b)
	{
		int temp = items[a];
		items[a] = items[b];
		items[b] = temp;
	}

	private void ensureCapacity()
	{
		if (size == capacity)
		{
			capacity *= 2;
			items = Arrays.copyOf(items, capacity);
		}
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}

	public int peek()
	{
		if (size == 0)
		{
			throw new IllegalStateException("peek cannot operate on empty heap");
		}
		return items[0];
	}

	public int poll()
	{
		if (size == 0)
		{
			throw new IllegalStateException("poll cannot operate on empty heap");
		}
		int item = items[0];
		items[0] = items[size - 1];
		size--;
		heapifyDown();
		return item;
	}

	public void add(int item)
	{
		ensureCapacity();
		items[size++] = item;
		heapifyUp();
	}

	protected abstract void heapifyDown();

	protected abstract void heapifyUp();
}

class MinHeap extends Heap
{
	protected void heapifyDown()
	{
		int index = 0;
		while (hasLeftChild(index))
		{
			int smallerChildIndex = getLeftChildIndex(index);
			if (hasRightChild(index) && rightChild(index) < leftChild(index))
			{
				smallerChildIndex = getRightChildIndex(index);
			}
			if (items[index] < items[smallerChildIndex])
			{
				break;
			}
			swap(index, smallerChildIndex);
			index = smallerChildIndex;
		}
	}

	protected void heapifyUp()
	{
		int index = size - 1;
		while (hasParent(index) && parent(index) > items[index])
		{
			swap(index, getParentIndex(index));
			index = getParentIndex(index);
		}
	}
}

class MaxHeap extends Heap
{
	protected void heapifyDown()
	{
		int index = 0;
		while (hasLeftChild(index))
		{
			int largerChildIndex = getLeftChildIndex(index);
			if (hasRightChild(index) && rightChild(index) > leftChild(index))
			{
				largerChildIndex = getRightChildIndex(index);
			}
			if (items[index] > items[largerChildIndex])
			{
				break;
			}
			swap(index, largerChildIndex);
			index = largerChildIndex;
		}
	}

	protected void heapifyUp()
	{
		int index = size - 1;
		while (hasParent(index) && parent(index) < items[index])
		{
			swap(index, getParentIndex(index));
			index = getParentIndex(index);
		}
	}
}
