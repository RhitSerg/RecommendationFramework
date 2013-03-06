import java.util.Arrays;

public class E0 {
	public static void source(int[] a, int low, int high) {
		int[] counts = new int[high - low + 1]; // this will hold all possible
												// values, from low to high
		for (int x : a)
			counts[x - low]++; // - low so the lowest possible value is always 0

		int current = 0;
		for (int i = 0; i < counts.length; i++) {
			Arrays.fill(a, current, current + counts[i], i + low); // fills
																	// counts[i]
																	// elements
																	// of value
																	// i + low
																	// in
																	// current
			current += counts[i]; // leap forward by counts[i] steps
		}
	}

	public static void target(int[] a, int low, int high) {
		int[] counts = new int[high - low + 1]; // this will hold all possible
												// values, from low to high
		for (int x : a)
			counts[x - low]++; // - low so the lowest possible value is always 0

		int current = 0;
		for (int i = 0; i < counts.length; i++) {
			Arrays.fill(a, current, current + counts[i], i + low); // fills
																	// counts[i]
																	// elements
																	// of value
																	// i + low
																	// in
																	// current
			current += counts[i]; // leap forward by counts[i] steps
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