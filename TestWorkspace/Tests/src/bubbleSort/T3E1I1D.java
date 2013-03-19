package bubbleSort;

public class T3E1I1D {
	public void source() {
		int[] num = new int[10];
		int j;
		boolean flag = true;
		int temp;

		while (flag) {
			flag = false;
			j = 0;
			while (j < num.length - 1) {
				flag = false;
				if (num[j] < num[j+1]) {
					temp = num[j];
					num[j] = num[j+1];
					num[j+1] = temp;
				}
				j++;
			}
		}
	}
	
	public void target() {
		int[] num = new int[10];
		int j;
		boolean flag = true;
		int temp;

		while (flag) {
			flag = false;
			for (j = 0; j < num.length - 1; j++) {
				if (num[j] < num[j + 1])
				{
					temp = num[j];
					num[j] = num[j + 1];
					num[j + 1] = temp;
					flag = true;
				}
			}
		}
	}
}
/*
 * Actual differences: (I) 13: '<=' token
 * 						   17-18: assignment flag = true missing
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 13: '<=' -> '<'
 * 							   17: Insert assignment statement
 */
