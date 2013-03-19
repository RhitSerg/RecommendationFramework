package bubbleSort;

public class T0a {
	public void source(int[] num) {
		int j;
		boolean flag = true;
		int temp;

		while (flag) {
			flag = false;
			j = 0;
			while (j < num.length - 1) {
				if (num[j] < num[j + 1]) {
					temp = num[j];
					num[j] = num[j + 1];
					num[j + 1] = temp;
					flag = true;
				}
				j++;
			}
		}
	}

	public void target(int[] num) {
		int j;
		boolean flag = true;
		int temp;

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
