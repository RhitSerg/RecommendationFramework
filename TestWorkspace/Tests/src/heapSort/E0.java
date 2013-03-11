package heapSort;

import java.util.PriorityQueue;

public class E0 {
	public static <E extends Comparable<? super E>> void source(E[] array) {

		// Java's PriorityQueue class functions as a min heap
		PriorityQueue<E> heap = new PriorityQueue<E>(array.length);

		// Add each array element to the heap
		for (E e : array)
			heap.add(e);

		// Elements come off the heap in ascending order
		for (int i = 0; i < array.length; i++)
			array[i] = heap.remove();

	}

	public static <E extends Comparable<? super E>> void target(E[] array) {

		// Java's PriorityQueue class functions as a min heap
		PriorityQueue<E> heap = new PriorityQueue<E>(array.length);

		// Add each array element to the heap
		for (E e : array)
			heap.add(e);

		// Elements come off the heap in ascending order
		for (int i = 0; i < array.length; i++)
			array[i] = heap.remove();

	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */