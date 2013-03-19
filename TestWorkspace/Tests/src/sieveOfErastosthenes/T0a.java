package sieveOfErastosthenes;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class T0a {

	public static List<Integer> source(int max) {
		BitSet sieve = new BitSet((max + 2) / 2); // +1 to include max itself
		for (int i = 3; i * i <= max; i += 2) {
			boolean flag1;
			assert i >= 3 && (i % 2) == 1;
			flag1 = sieve.get((i - 3) / 2);

			if (flag1)
				continue;

			// We increment by 2*i to skip even multiples of i
			for (int multiple_i = i * i; multiple_i <= max; multiple_i += 2 * i) {
				assert multiple_i >= 3 && (multiple_i % 2) == 1;
				sieve.set((multiple_i - 3) / 2);
			}
		}
		List<Integer> primes = new ArrayList<Integer>();
		primes.add(2);
		for (int i = 3; i <= max; i += 2) {
			boolean flag2;

			assert i >= 3 && (i % 2) == 1;
			flag2 = sieve.get((i - 3) / 2);

			if (flag2)
				primes.add(i);
		}

		return primes;
	}

	public static List<Integer> target(int max) {
		BitSet sieve = new BitSet((max + 2) / 2); // +1 to include max itself
		for (int i = 3; i * i <= max; i += 2) {
			boolean flag1;
			assert i >= 3 && (i % 2) == 1;
			flag1 = sieve.get((i - 3) / 2);

			if (flag1)
				continue;

			// We increment by 2*i to skip even multiples of i
			for (int multiple_i = i * i; multiple_i <= max; multiple_i += 2 * i) {
				assert multiple_i >= 3 && (multiple_i % 2) == 1;
				sieve.set((multiple_i - 3) / 2);
			}
		}
		List<Integer> primes = new ArrayList<Integer>();
		primes.add(2);
		for (int i = 3; i <= max; i += 2) {
			boolean flag2;

			assert i >= 3 && (i % 2) == 1;
			flag2 = sieve.get((i - 3) / 2);

			if (flag2)
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