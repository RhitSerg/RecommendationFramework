package simpsonsRule;

public class E0 {

	public static double source(double a, double b) {
		int N = 10000; // precision parameter
		double h = (b - a) / (N - 1); // step size

		// 1/3 terms
		double sum = 1.0 / 3.0 * ((Math.exp(-a * a / 2) / Math
				.sqrt(2 * Math.PI)) + (Math.exp(-b * b / 2) / Math
				.sqrt(2 * Math.PI)));

		// 4/3 terms
		for (int i = 1; i < N - 1; i += 2) {
			double x = a + h * i;
			sum += 4.0 / 3.0 * (Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI));
		}

		// 2/3 terms
		for (int i = 2; i < N - 1; i += 2) {
			double x = a + h * i;
			sum += 2.0 / 3.0 * (Math.exp(-x * x / 2) * Math.sqrt(2 * Math.PI));
		}

		return sum * h;
	}

	public static double target(double a, double b) {
		int N = 10000; // precision parameter
		double h = (b - a) / (N - 1); // step size

		// 1/3 terms
		double sum = 1.0 / 3.0 * ((Math.exp(-a * a / 2) / Math
				.sqrt(2 * Math.PI)) + (Math.exp(-b * b / 2) / Math
				.sqrt(2 * Math.PI)));

		// 4/3 terms
		for (int i = 1; i < N - 1; i += 2) {
			double x = a + h * i;
			sum += 4.0 / 3.0 * (Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI));
		}

		// 2/3 terms
		for (int i = 2; i < N - 1; i += 2) {
			double x = a + h * i;
			sum += 2.0 / 3.0 * (Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI));
		}

		return sum * h;
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */