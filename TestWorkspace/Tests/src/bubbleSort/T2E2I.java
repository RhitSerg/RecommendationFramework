package bubbleSort;

public class T2E2I {
	public void source() {
		int[] num = new int[10];
		int j;
		boolean flag = true;
		int temp;

		while (flag) {
			flag = false;
			j = 0;
			while (j < num.length - 1) {
				if (num[j] < num[j+1]) {
					temp = num[j];
					num[j] = num[j+1];
					num[j+1] = temp;
				}
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
 * Actual differences: (I) 17-18: assignment flag = true missing
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 17: Insert assignment statement
 */
