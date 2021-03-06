package gnomeSort;

public class T0 {
	static void source(int[] theArray) {
		int index = 1;
		while (index < theArray.length) {
			if (theArray[index - 1] <= theArray[index]) {
				++index;
			} else {
				int tempVal = theArray[index];
				theArray[index] = theArray[index - 1];
				theArray[index - 1] = tempVal;
				--index;
				if (index == 0) {
					index = 1;
				}
			}
		}
	}

	static void target(int[] theArray) {
		for (int index = 1; index < theArray.length;) {
			if (theArray[index - 1] <= theArray[index]) {
				++index;
			} else {
				int tempVal = theArray[index];
				theArray[index] = theArray[index - 1];
				theArray[index - 1] = tempVal;
				--index;
				if (index == 0) {
					index = 1;
				}
			}
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