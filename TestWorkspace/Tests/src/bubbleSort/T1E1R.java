package bubbleSort;

public class T1E1R {
	public void source(int[] num) {
		boolean flag = true;
		int temp;

		while (flag) {
			flag = true;
			int j = 0;
			while (j < num.length - 1) {
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
	
	public void target(int[] num) {
		boolean flag = true;
		int temp;

		while (flag) {
			flag = false;
			for (int j = 0; j < num.length - 1; j++) {
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
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 11: 'true' -> 'false'
 */
