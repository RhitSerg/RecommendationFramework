package bubbleSort;

public class E0 {
	/*None*/
	public void source() {
		int[] num = new int[10];
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

	public void target() {
		int[] num = new int[10];
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