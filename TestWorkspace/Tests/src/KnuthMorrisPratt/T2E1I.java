package KnuthMorrisPratt;

public class T2E1I {
	int source(char[] w, char[] s, int[] t) {
		int m = 0;
		int i = 0;
		while (((m + i) < s.length) && (i < w.length)) {
			if (s[m + i] == w[i])
				i++;
			else {
				m += i - t[i];
				if (i > 0)
					i = t[i];
			}
		}
		if (i == w.length)
			return m;
		else
			return -1;
	}

	int target(char[] w, char[] s, int[] t) {
		int m = 0;
		int i = 0;
		while (((m + i) < s.length) && (i < w.length)) {
			if (s[m + i] == w[i])
				i++;
			else {
				m += i - t[i];
				if (i > 0)
					i = t[i];
				i++;
			}
		}
		if (i == w.length)
			return m;
		else
			return -1;
	}
}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */