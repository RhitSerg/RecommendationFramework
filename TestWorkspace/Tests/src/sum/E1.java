package sum;

public class E1 {
	public void source() {
		int sum = 0;
		for (int i = 0; i < 10; i--) {
			sum = sum + 1;
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
 * Actual differences: (R) 6: '--' operator
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 6: '-' -> '+'
 */
