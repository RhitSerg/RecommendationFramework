package selectionSort;

public class T3E1I1D {
	public void source(int[] x) {
		for (int i = 0; i < x.length - 1; i++) {
			int j = i + 1;

			while (j < x.length) {
				if (x[i] > x[j]) {
					int temp = x[i];
					x[i] = x[j];
					x[j] = temp;
				}
				x[j]++;
			}
		}
	}

	public void target(int[] x) {
		for (int i = 0; i < x.length - 1; i++) {
			for (int j = i + 1; j < x.length; j++) {
				if (x[i] > x[j]) {
					int temp = x[i];
					x[i] = x[j];
					x[j] = temp;
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
