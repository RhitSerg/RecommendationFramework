package naivePrimeGeneration;

import java.util.ArrayList;
import java.util.List;

public class T3E1I1D {

	public static List<Integer> source(int max) {
		List<Integer> primes = new ArrayList<Integer>();
		OUTERLOOP: for (int i = 2; i <= max; i++) {
			for (Integer p : primes)
				if (i % p == 0)
					continue OUTERLOOP;
			i++;
		}
		return primes;
	}

	public static List<Integer> target(int max) {
		List<Integer> primes = new ArrayList<Integer>();
		OUTERLOOP: for (int i = 2; i <= max; i++) {
			for (Integer p : primes)
				if (i % p == 0)
					continue OUTERLOOP;
			primes.add(i);
		}
		return primes;
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */