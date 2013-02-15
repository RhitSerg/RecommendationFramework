package mean;

public class E0 {
	public void source() {
		int[] nums = new int[10];
		int mean = 0;
		int i = 0;
		while (i < nums.length) {
			mean += nums[i];
			i++;
		}
		mean /= nums.length;
	}
	
	public void target() {
		int[] nums = new int[10];
		int mean = 0;
		for (int i = 0; i < nums.length; i++) {
			mean += nums[i];
		}
		mean /= nums.length;
	}
}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */