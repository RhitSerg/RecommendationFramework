package bubbleSort;

public class T2E1D {
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
 * Actual differences: (D) 14: assignment flag = false extra
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 14: Delete unnecessary code
 */
