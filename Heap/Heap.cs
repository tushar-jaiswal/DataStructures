//Author: Tushar Jaiswal
//Creation Date: 09/30/2018

using System;

namespace ConsoleApp
{
	public class DataStructures
	{
		static void Main(string[] args)
		{
			int[] arr = { 2, 5, 7, 3, 7, 2, 1, 8, -3 };
			Heap minHeap = new MinHeap();
			Heap maxHeap = new MaxHeap();

			Console.Write("Elements added to min heap and max heap: ");
			foreach(int i in arr)
			{
				minHeap.Add(i);
				maxHeap.Add(i);
				Console.Write(" " + i);
			}
			Console.WriteLine();
			Console.WriteLine("Min Heap poll: " + minHeap.Peek());
			Console.WriteLine("Max Heap poll: " + maxHeap.Peek());
			Console.WriteLine("Min Heap poll: " + minHeap.Poll());
			Console.WriteLine("Max Heap poll: " + maxHeap.Poll());
			Console.WriteLine("Min Heap poll: " + minHeap.Poll());
			Console.WriteLine("Max Heap poll: " + maxHeap.Poll());
		}
	}

	public abstract class Heap
	{
		private int capacity;
		public int Count { get; set; }
		protected int[] items;

		public Heap()
		{
			capacity = 10;
			Count = 0;
			items = new int[capacity];
		}

		protected int GetLeftChildIndex(int parentIndex)
		{
			return 2 * parentIndex + 1;
		}

		protected int GetRightChildIndex(int parentIndex)
		{
			return 2 * parentIndex + 2;
		}

		protected int GetParentIndex(int childIndex)
		{
			if (childIndex == 0)
			{
				return -1;
			}
			return (childIndex - 1) / 2;
		}

		protected bool HasLeftChild(int index)
		{
			return GetLeftChildIndex(index) < Count;
		}

		protected bool HasRightChild(int index)
		{
			return GetRightChildIndex(index) < Count;
		}

		protected bool HasParent(int index)
		{
			return GetParentIndex(index) >= 0;
		}

		protected int LeftChild(int index)
		{
			return items[GetLeftChildIndex(index)];
		}

		protected int RightChild(int index)
		{
			return items[GetRightChildIndex(index)];
		}

		protected int Parent(int index)
		{
			return items[GetParentIndex(index)];
		}

		protected void Swap(int a, int b)
		{
			int temp = items[a];
			items[a] = items[b];
			items[b] = temp;
		}

		private void EnsureCapacity()
		{
			if (Count == capacity)
			{
				capacity *= 2;
				Array.Resize<int>(ref items, capacity);
			}
		}

		public int Peek()
		{
			if (Count == 0)
			{
				throw new Exception();
			}
			return items[0];
		}

		public int Poll()
		{
			if (Count == 0)
			{
				throw new Exception();
			}
			int item = items[0];
			items[0] = items[Count - 1];
			Count--;
			HeapifyDown();
			return item;
		}

		public void Add(int item)
		{
			EnsureCapacity();
			items[Count++] = item;
			HeapifyUp();
		}

		protected abstract void HeapifyDown();

		protected abstract void HeapifyUp();
	}

	public class MinHeap : Heap
	{
		protected override void HeapifyDown()
		{
			int index = 0;
			while (HasLeftChild(index))
			{
				int smallerChildIndex = GetLeftChildIndex(index);
				if (HasRightChild(index) && RightChild(index) < LeftChild(index))
				{
					smallerChildIndex = GetRightChildIndex(index);
				}
				if (items[index] < items[smallerChildIndex])
				{
					break;
				}
				Swap(index, smallerChildIndex);
				index = smallerChildIndex;
			}
		}

		protected override void HeapifyUp()
		{
			int index = Count - 1;
			while (HasParent(index) && Parent(index) > items[index])
			{
				Swap(index, GetParentIndex(index));
				index = GetParentIndex(index);
			}
		}
	}

	public class MaxHeap : Heap
	{
		protected override void HeapifyDown()
		{
			int index = 0;
			while (HasLeftChild(index))
			{
				int largerChildIndex = GetLeftChildIndex(index);
				if (HasRightChild(index) && RightChild(index) > LeftChild(index))
				{
					largerChildIndex = GetRightChildIndex(index);
				}
				if (items[index] > items[largerChildIndex])
				{
					break;
				}
				Swap(index, largerChildIndex);
				index = largerChildIndex;
			}
		}

		protected override void HeapifyUp()
		{
			int index = Count - 1;
			while (HasParent(index) && Parent(index) < items[index])
			{
				Swap(index, GetParentIndex(index));
				index = GetParentIndex(index);
			}
		}
	}
}
