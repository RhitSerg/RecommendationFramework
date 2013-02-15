package mean;

public class E3R {
	public void source() {
		int[] nums = new int[10];
		int mean = 0;
		int i = 1;
		while (i < nums.length) {
			mean -= nums[i];
			i++;
		}
		mean *= nums.length;
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
 * Actual differences: 7: '1' token
 * 					   9: '-' operator
 * 					   12: '*' operator
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 7: '1' -> '0'
 * 							   9: '-' -> '+'
 * 							   12: '*' -> '/'
 */