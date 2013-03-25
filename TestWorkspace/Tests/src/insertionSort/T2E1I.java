package insertionSort;

public class T2E1I {
	public static void source(int[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			int copyNumber = numbers[i];
			int j = i;
			while (j > 0 && copyNumber < numbers[j - 1]) {
				numbers[j] = numbers[j - 1];
			}
			numbers[j] = copyNumber;
		}
	}

	public static void target(int[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			int copyNumber = numbers[i];
			int j = i;
			while (j > 0 && copyNumber < numbers[j - 1]) {
				numbers[j] = numbers[j - 1];
				j--;
			}
			numbers[j] = copyNumber;
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