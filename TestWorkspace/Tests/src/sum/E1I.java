package sum;

public class E1I {
	public void source() {
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			sum = 1;
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
 * Actual differences: (I) 7: Missing infix expression
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 7: Insert infix expression
 * 							   7: Insert operator '+'
 * 							   7: Insert ID 'sum'
 */
