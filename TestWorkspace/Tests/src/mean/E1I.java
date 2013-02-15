package mean;

public class E1I {
	public void source() {
		int[] nums = new int[10];
		int mean = 0;
		int i = 0;
		while (i < nums.length) {
			mean += nums[i];
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
 * Actual differences: 9-10: Missing 'i' increment
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 9: Insert assignment
 */