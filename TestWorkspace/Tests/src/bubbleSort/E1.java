package bubbleSort;

public class E1 {

	public void source(int[] num) {

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

	public void target() {
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
 * Actual differences: 
 * 
 * Expected difference score: 
 * 
 * Expected recommended edits:
 */
