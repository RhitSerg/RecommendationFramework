package sum;

public class E1 {

	public void mySort() {
		int[] num = new int[10];
		int j;
		boolean flag = true;
		int temp;
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;

		while (flag) {
			flag = false;
			for (j = 0; j < num.length - 1; j++) {
				if (num[j] < num[j + 1]) {
					temp = num[j];
					num[j] = num[j + 1];
					num[j + 1] = temp;
					flag = true;
				}
			}
		}
		while (flag) {
			flag = false;
			for (j = 0; j < num.length - 1; j++) {
				if (num[j] < num[j + 1]) {
					temp = num[j];
					num[j] = num[j + 1];
					num[j + 1] = temp;
					flag = true;
				}
			}
		}
	}

	public void bubbleSort() {
		int[] num = new int[10];
		int j;
		boolean flag = true;
		int temp;
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;

		while (flag) {
			flag = false;
			for (j = 0; j < num.length - 1; j++) {
				if (num[j] < num[j + 1]) {
					temp = num[j];
					num[j] = num[j + 1];
					num[j + 1] = temp;
					flag = true;
				}
			}
		}
		while (flag) {
			flag = false;
			for (j = 0; j < num.length - 1; j++) {
				if (num[j] < num[j + 1]) {
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
 * Actual differences: (R) 6: '--' operator
 * 
 * Expected difference score: ???
 * 
 * Expected recommended edits: 6: '-' -> '+'
 */
