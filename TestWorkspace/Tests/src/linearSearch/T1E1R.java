package linearSearch;

public class T1E1R {
	public int source(int[] values, int target) {
		for (int i = 0; i < values.length; ++i) {
			if (values[i] == target) {
				return i;
			}
		}
		return 0;
	}

	public int target(int[] values, int target) {
		for (int i = 0; i < values.length; ++i) {
			if (values[i] == target) {
				return i;
			}
		}
		return -1;
	}
}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */