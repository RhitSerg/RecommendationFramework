package inPlaceHeapSort;

public class T2E1D {
	public static void source(int[] array) {
		/*
		 * This method performs an in-place heapsort. Starting from the
		 * beginning of the array, the array is swapped into a binary max heap.
		 * Then elements are removed from the heap, and added to the front of
		 * the sorted section of the array.
		 */

		/* Insertion onto heap */
		for (int heapsize = 0; heapsize < array.length - 1; heapsize++) {
			/*
			 * Step one in adding an element to the heap in the place that
			 * element at the end of the heap array- in this case, the element
			 * is already there.
			 */
			int n = heapsize; // the index of the inserted int
			while (n > 0) { // until we reach the root of the heap
				int p = (n - 1) / 2; // the index of the parent of n
				if (array[n] > array[p]) { // child is larger than parent
					int temp = array[n];
					array[n] = array[p];
					array[p] = temp;

					n = p; // check parent
				} else
					// parent is larger than child
					break; // all is good in the heap
			}
		}

		/* Removal from heap */
		for (int heapsize = array.length; heapsize > 0;) {
			int temp = array[0];
			array[0] = array[--heapsize];
			array[--heapsize] = temp;

			int n = 0; // index of the element being moved down the tree
			while (true) {
				int left = (n * 2) + 1;
				if (left >= heapsize) // node has no left child
					break; // reached the bottom; heap is heapified
				int right = left + 1;
				if (right >= heapsize) { // node has a left child, but no right
											// child
					if (array[left] > array[n]) { // if left child is greater
													// than
													// node
						temp = array[left];
						array[left] = array[n];
						array[n] = temp;
					}
					break; // heap is heapified
				}
				if (array[left] > array[n]) { // (left > n)
					if (array[left] > array[right]) { // (left > right) & (left
														// > n)
						temp = array[left];
						array[left] = array[n];
						array[n] = temp;
						n = left;
						continue; // continue recursion on left child
					} else { // (right > left > n)
						temp = array[right];
						array[right] = array[n];
						array[n] = temp;
						n = right;
						continue; // continue recursion on right child
					}
				} else { // (n > left)
					if (array[right] > array[n]) { // (right > n > left)
						temp = array[right];
						array[right] = array[n];
						array[n] = temp;
						n = right;
						continue; // continue recursion on right child
					} else { // (n > left) & (n > right)
						break; // node is greater than both children, so it's
								// heapified
					}
				}
			}
		}
	}

	public static void target(int[] array) {
		/*
		 * This method performs an in-place heapsort. Starting from the
		 * beginning of the array, the array is swapped into a binary max heap.
		 * Then elements are removed from the heap, and added to the front of
		 * the sorted section of the array.
		 */

		/* Insertion onto heap */
		for (int heapsize = 0; heapsize < array.length; heapsize++) {
			/*
			 * Step one in adding an element to the heap in the place that
			 * element at the end of the heap array- in this case, the element
			 * is already there.
			 */
			int n = heapsize; // the index of the inserted int
			while (n > 0) { // until we reach the root of the heap
				int p = (n - 1) / 2; // the index of the parent of n
				if (array[n] > array[p]) { // child is larger than parent
					int temp = array[n];
					array[n] = array[p];
					array[p] = temp;

					n = p; // check parent
				} else
					// parent is larger than child
					break; // all is good in the heap
			}
		}

		/* Removal from heap */
		for (int heapsize = array.length; heapsize > 0;) {
			int temp = array[0];
			array[0] = array[--heapsize];
			array[--heapsize] = temp;

			int n = 0; // index of the element being moved down the tree
			while (true) {
				int left = (n * 2) + 1;
				if (left >= heapsize) // node has no left child
					break; // reached the bottom; heap is heapified
				int right = left + 1;
				if (right >= heapsize) { // node has a left child, but no right
											// child
					if (array[left] > array[n]) { // if left child is greater
													// than
													// node
						temp = array[left];
						array[left] = array[n];
						array[n] = temp;
					}
					break; // heap is heapified
				}
				if (array[left] > array[n]) { // (left > n)
					if (array[left] > array[right]) { // (left > right) & (left
														// > n)
						temp = array[left];
						array[left] = array[n];
						array[n] = temp;
						n = left;
						continue; // continue recursion on left child
					} else { // (right > left > n)
						temp = array[right];
						array[right] = array[n];
						array[n] = temp;
						n = right;
						continue; // continue recursion on right child
					}
				} else { // (n > left)
					if (array[right] > array[n]) { // (right > n > left)
						temp = array[right];
						array[right] = array[n];
						array[n] = temp;
						n = right;
						continue; // continue recursion on right child
					} else { // (n > left) & (n > right)
						break; // node is greater than both children, so it's
								// heapified
					}
				}
			}
		}
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */