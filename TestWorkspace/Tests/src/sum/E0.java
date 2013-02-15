package sum;

public class E0 {
	/*increment operator, iteration method*/
	public void source4() {
		int sum = 0;
		int i = 0;
		while (i < 10) {
			sum++;
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
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */

///*None*/
//public void source1() {
//	int sum = 0;
//	for (int i = 0; i < 10; i++) {
//		sum = sum + 1;
//	}
//}
//
///*increment operator*/
//public void source2() {
//	int sum = 0;
//	for (int i = 0; i < 10; i++) {
//		sum++;
//	}
//}
//
///*Currently doesn't work, variable name*/
//public void source2() {
//	int s = 0;
//	for (int i = 0; i < 10; i++) {
//		s++;
//	}
//}
//
///*iteration method*/
//public void source3() {
//	int sum = 0;
//	int i = 0;
//	while (i < 10) {
//		sum = sum + 1;
//	}
//}
*/