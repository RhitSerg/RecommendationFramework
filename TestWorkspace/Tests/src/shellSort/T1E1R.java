package shellSort;

public class E0 {

	public static <T extends Comparable<? super T>> void source(T[] array) {
		// loop over the gaps
		for (int gap = array.length / 2; gap > 0; gap /= 2) {
			// do the insertion sort
			for (int i = gap; i < array.length; i++) {
				T val = array[i];
				int j;
				for (j = i; j >= gap && array[j + gap].compareTo(val) > 0; j -= gap) {
					array[j] = array[j - gap];
				}
				array[j] = val;
			}
		}
	}

	public static <T extends Comparable<? super T>> void target(T[] array) {
		// loop over the gaps
		for (int gap = array.length / 2; gap > 0; gap /= 2) {
			// do the insertion sort
			for (int i = gap; i < array.length; i++) {
				T val = array[i];
				int j;
				for (j = i; j >= gap && array[j - gap].compareTo(val) > 0; j -= gap) {
					array[j] = array[j - gap];
				}
				array[j] = val;
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