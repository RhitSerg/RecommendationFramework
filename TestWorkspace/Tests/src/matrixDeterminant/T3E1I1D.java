package matrixDeterminant;

public class T3E1I1D {

	public static double source(int NMAX, double[][] x) {
		double ret = 0;
		if (NMAX < 4)// base case
		{
			double prod1 = 1, prod2 = 1;
			for (int i = 0; i < NMAX; i++) {
				prod1 = 1;
				prod2 = 1;

				for (int j = 0; j < NMAX; j++) {
					prod1 = 1;
					prod1 *= x[(j + i + 1) % NMAX][j];
					prod2 *= x[(j + i + 1) % NMAX][NMAX - j - 1];
				} // end inner loop
				//ret += prod1 - prod2;
			} // end outer loop
			return ret;
		} // end base case
		double[][] y = new double[NMAX - 1][NMAX - 1];
		for (int h = 0; h < NMAX; h++) {
			if (x[h][0] == 0)
				continue;
			int r = h;
			int c = 0;
			int n = NMAX;

			for (int a = 0, j = 0; a < n; a++) {
				if (a == r)
					continue;
				for (int i = 0, k = 0; i < n; i++) {
					if (i == c)
						continue;
					y[j][k] = x[a][i];
					k++;
				} // end inner loop
				j++;
			} // end outer loop

			if (h % 2 == 0)
				ret -= source(NMAX - 1, y) * x[h][0];
			if (h % 2 == 1)
				ret += source(NMAX - 1, y) * x[h][0];
		} // end loop
		return ret;
	} // end method

	public static double target(int NMAX, double[][] x) {
		double ret = 0;
		if (NMAX < 4)// base case
		{
			double prod1 = 1, prod2 = 1;
			for (int i = 0; i < NMAX; i++) {
				prod1 = 1;
				prod2 = 1;

				for (int j = 0; j < NMAX; j++) {
					prod1 *= x[(j + i + 1) % NMAX][j];
					prod2 *= x[(j + i + 1) % NMAX][NMAX - j - 1];
				} // end inner loop
				ret += prod1 - prod2;
			} // end outer loop
			return ret;
		} // end base case
		double[][] y = new double[NMAX - 1][NMAX - 1];
		for (int h = 0; h < NMAX; h++) {
			if (x[h][0] == 0)
				continue;
			int r = h;
			int c = 0;
			int n = NMAX;

			for (int a = 0, j = 0; a < n; a++) {
				if (a == r)
					continue;
				for (int i = 0, k = 0; i < n; i++) {
					if (i == c)
						continue;
					y[j][k] = x[a][i];
					k++;
				} // end inner loop
				j++;
			} // end outer loop

			if (h % 2 == 0)
				ret -= target(NMAX - 1, y) * x[h][0];
			if (h % 2 == 1)
				ret += target(NMAX - 1, y) * x[h][0];
		} // end loop
		return ret;
	} // end method

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */