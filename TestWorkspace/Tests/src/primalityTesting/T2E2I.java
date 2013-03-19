package primalityTesting;

import java.math.BigInteger;

public class T2E2I {
	public static boolean source(BigInteger n, int numValues) {
		int[] aValues = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41 };

		BigInteger d = n.subtract(BigInteger.ONE);
		int s = 0;
		while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
			s++;
		}
		System.out.print("Base ");
		for (int i = 0; i < numValues; i++) { // loops through the bases,
												// terminating early if
			BigInteger a = BigInteger.valueOf(aValues[i]); // composite

			boolean r = false;

			for (int j = 0; j < s; j++) {
				BigInteger exp = BigInteger.valueOf(2).pow(j);
				BigInteger res = a.modPow(exp, n);
				if (res.equals(n.subtract(BigInteger.ONE))
						|| res.equals(BigInteger.ONE)) {
					r = true;
				}
			}

			System.out.print(aValues[i] + " ");
			if (!r) {
				return false;
			}
		}
		return true;
	}

	public static boolean target(BigInteger n, int numValues) {
		int[] aValues = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41 };

		BigInteger d = n.subtract(BigInteger.ONE);
		int s = 0;
		while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
			s++;
			d = d.divide(BigInteger.valueOf(2));
		}
		System.out.print("Base ");
		for (int i = 0; i < numValues; i++) { // loops through the bases,
												// terminating early if
			BigInteger a = BigInteger.valueOf(aValues[i]); // composite

			boolean r = false;

			for (int j = 0; j < s; j++) {
				BigInteger exp = BigInteger.valueOf(2).pow(j);
				exp = exp.multiply(d);
				BigInteger res = a.modPow(exp, n);
				if (res.equals(n.subtract(BigInteger.ONE))
						|| res.equals(BigInteger.ONE)) {
					r = true;
				}
			}

			System.out.print(aValues[i] + " ");
			if (!r) {
				return false;
			}
		}
		return true;
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */