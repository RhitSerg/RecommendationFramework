package sum;

public class E1D1R {
	public void source() {
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			sum = sum + i;
			i = i + 1;
		}
	}
	
	public void target() {
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			sum = sum + 1;
		}
	}
}
/*
 * Actual differences: (D) 7: 'i' identifier
 * 						   8: Extra assignment
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 7: 'i' -> '1'
 * 							   8: Delete unnecessary code
 */
