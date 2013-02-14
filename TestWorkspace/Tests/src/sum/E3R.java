package sum;

public class E3R {
	public void source() {
		int sum = 0;
		for (int i = 0; i >= 10; i++) {
			sum = sum - i;
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
 * Actual differences:
 * (R) 6: '>=' operator
 * (R) 7: '-' operator
 * (R) 7: 'i' identifier
 * 
 * Expected difference score:
 * ???
 * 
 * Expected recommended edits:
 * 6: '>=' -> '<'
 * 7: '-' -> '+'
 * 7: 'i' -> '1'
 * 
 */