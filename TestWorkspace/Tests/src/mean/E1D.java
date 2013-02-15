package mean;

public class E1D {
	public void source() {
		int[] nums = new int[10];
		int mean = 0;
		int i = 0;
		while (i < nums.length) {
			mean += nums[i];
			i++;
		}
		mean /= (nums.length - 1);
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
 * Actual differences: 12: Extra '-' expression
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 12: Delete unnecessary code
 */