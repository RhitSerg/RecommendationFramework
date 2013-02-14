package sum;

public class E1D {
	public void source() {
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			sum = sum + 1;
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
 * Actual differences: (D) 8: Extra assignment
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 8: Delete unnecessary code
 */
