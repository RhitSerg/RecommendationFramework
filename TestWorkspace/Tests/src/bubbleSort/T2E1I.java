package bubbleSort;

public class T2E1I {
	public void source(int[] num) {
		boolean flag = true;
		int temp;

		while (flag) {
			flag = false;
			int j = 0;
			while (j < num.length - 1) {
				if (num[j] < num[j+1]) {
					temp = num[j];
					num[j] = num[j+1];
					num[j+1] = temp;
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
 * Actual differences: (I) 17-18: assignment flag = true missing
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 17: Insert assignment statement
 */
