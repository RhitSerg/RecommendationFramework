package linearSearch;



public class T0 {

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
		return 0;
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */