package combSort;

public class T3E1D1R {
	public static void source(int a[]) {
		int gap = a.length;
		boolean swapped;
		do {
			swapped = false;

			gap = gap * 10 / 13;
			if (gap == 9 || gap == 10)
				gap = 11;
			if (gap < 1)
				gap = 1;
			for (int i = 0; i < (a.length - gap); i++) {
				if (a[i] > a[i + gap]) {
					swapped = true;
					int temp = a[i];
					a[i] = a[i + gap];
					a[i + gap] = temp;
				}
			}
			swapped = true;
		} while (gap > 1 && swapped);
	}

	public static void target(int a[]) {
		int gap = a.length;
		boolean swapped;
		do {
			swapped = false;

			gap = gap * 10 / 13;
			if (gap == 9 || gap == 10)
				gap = 11;
			if (gap < 1)
				gap = 1;
			for (int i = 0; i < (a.length - gap); i++) {
				if (a[i] > a[i + gap]) {
					swapped = true;
					int temp = a[i];
					a[i] = a[i + gap];
					a[i + gap] = temp;
				}
			}
		} while (gap > 1 || swapped);
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */