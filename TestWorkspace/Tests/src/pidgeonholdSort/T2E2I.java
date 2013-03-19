package pidgeonholdSort;

public class E0 {
	public static void source(int[] a) {
		// size of range of values in the list (ie, number of pigeonholes we
		// need)
		int min = a[0];
		for (int x : a) {
			min = Math.min(x, min);
		}
		final int size = max - min + 1;

		// our array of pigeonholes
		int[] holes = new int[size];

		// Populate the pigeonholes.
		for (int x : a)
			holes[x - min]++;

		// Put the elements back into the array in order.
		int i = 0;
		for (int count = 0; count < size; count++)
			while (holes[count]-- > 0)
				a[i++] = count + min;
	}
	
	public static void target(int[] a) {
		// size of range of values in the list (ie, number of pigeonholes we
		// need)
		int min = a[0], max = a[0];
		for (int x : a) {
			min = Math.min(x, min);
			max = Math.max(x, max);
		}
		final int size = max - min + 1;

		// our array of pigeonholes
		int[] holes = new int[size];

		// Populate the pigeonholes.
		for (int x : a)
			holes[x - min]++;

		// Put the elements back into the array in order.
		int i = 0;
		for (int count = 0; count < size; count++)
			while (holes[count]-- > 0)
				a[i++] = count + min;
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */