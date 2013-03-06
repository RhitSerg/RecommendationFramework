import java.util.ArrayList;
import java.util.List;

public class E0 {

	public static List<Integer> source(int max) {
		List<Integer> primes = new ArrayList<Integer>();

		// start from 2
		OUTERLOOP: for (int i = 2; i <= max; i++) {
			// try to divide it by all known primes
			for (Integer p : primes)
				if (i % p == 0)
					continue OUTERLOOP; // i is not prime

			// i is prime
			primes.add(i);
		}
		return primes;
	}

	public static List<Integer> target(int max) {
		List<Integer> primes = new ArrayList<Integer>();

		// start from 2
		OUTERLOOP: for (int i = 2; i <= max; i++) {
			// try to divide it by all known primes
			for (Integer p : primes)
				if (i % p == 0)
					continue OUTERLOOP; // i is not prime

			// i is prime
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