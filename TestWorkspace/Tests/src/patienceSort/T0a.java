package patienceSort;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class T0a {

	public static <E extends Comparable<? super E>> void source(E[] n) {
		List<Stack<E>> piles = new ArrayList<Stack<E>>();
		// sort into piles
		for (E x : n) {
			Stack<E> newPile = new Stack<E>();
			newPile.push(x);
			// int i = Collections.binarySearch(piles, newPile);
			int i = 0;
			if (i < 0)
				i = ~i;
			if (i != piles.size())
				piles.get(i).push(x);
			else
				piles.add(newPile);
		}
		System.out.println("longest increasing subsequence has length = "
				+ piles.size());

		// priority queue allows us to retrieve least pile efficiently
		PriorityQueue<Stack<E>> heap = new PriorityQueue<Stack<E>>(piles);
		for (int c = 0; c < n.length; c++) {
			Stack<E> smallPile = heap.poll();
			n[c] = smallPile.pop();
			if (!smallPile.isEmpty())
				heap.offer(smallPile);
		}
		assert (heap.isEmpty());
	}

	public static <E extends Comparable<? super E>> void target(E[] n) {
		List<Stack<E>> piles = new ArrayList<Stack<E>>();
		// sort into piles
		for (E x : n) {
			Stack<E> newPile = new Stack<E>();
			newPile.push(x);
			// int i = Collections.binarySearch(piles, newPile);
			int i = 0;
			if (i < 0)
				i = ~i;
			if (i != piles.size())
				piles.get(i).push(x);
			else
				piles.add(newPile);
		}
		System.out.println("longest increasing subsequence has length = "
				+ piles.size());

		// priority queue allows us to retrieve least pile efficiently
		PriorityQueue<Stack<E>> heap = new PriorityQueue<Stack<E>>(piles);
		for (int c = 0; c < n.length; c++) {
			Stack<E> smallPile = heap.poll();
			n[c] = smallPile.pop();
			if (!smallPile.isEmpty())
				heap.offer(smallPile);
		}
		assert (heap.isEmpty());
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */