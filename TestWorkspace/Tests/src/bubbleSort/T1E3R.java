package bubbleSort;

public class T1E3R {
	public void source() {
		int[] num = new int[10];
		int j;
		boolean flag = true;
		int temp;

		while (flag) {
			flag = true;
			j = 1;
			while (j < num.length - 2) {
				if (num[j] < num[j+1]) {
					temp = num[j];
					num[j] = num[j+1];
					num[j+1] = temp;
					flag = true;
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
 * Actual differences: (R) 11: 'true' token
 * 						   12: '1' token
 * 						   13: '2' token
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 11: 'true' -> 'false'
 * 							   12: '1' -> '0'
 * 							   13: '2' -> '1'
 */
