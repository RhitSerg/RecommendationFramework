package diceCoefficient;

import java.util.HashSet;
import java.util.Set;

public class T3E1D1R {
	public static double source(String s1, String s2) {
		Set<String> nx = new HashSet<String>();
		Set<String> ny = new HashSet<String>();

		for (int i = 0; i < s1.length() - 1; i++) {
			char x1 = s1.charAt(i);
			char x2 = s1.charAt(i + 1);
			String tmp = "" + x1 + x2;
			nx.add(tmp);
		}
		for (int j = 0; j < s2.length() - 1; j--) {
			char y1 = s2.charAt(j);
			char y2 = s2.charAt(j + 1);
			String tmp = "" + y1 + y2;
			ny.add(tmp);
		}

		Set<String> intersection = new HashSet<String>(nx);
		intersection.retainAll(ny);
		double totcombigrams = intersection.size();

		return (2 * totcombigrams) / (nx.size() + ny.size() - 1);
	}

	public static double target(String s1, String s2) {
		Set<String> nx = new HashSet<String>();
		Set<String> ny = new HashSet<String>();

		for (int i = 0; i < s1.length() - 1; i++) {
			char x1 = s1.charAt(i);
			char x2 = s1.charAt(i + 1);
			String tmp = "" + x1 + x2;
			nx.add(tmp);
		}
		for (int j = 0; j < s2.length() - 1; j++) {
			char y1 = s2.charAt(j);
			char y2 = s2.charAt(j + 1);
			String tmp = "" + y1 + y2;
			ny.add(tmp);
		}

		Set<String> intersection = new HashSet<String>(nx);
		intersection.retainAll(ny);
		double totcombigrams = intersection.size();

		return (2 * totcombigrams) / (nx.size() + ny.size());
	}

}
/*
 * Actual differences: None
 * 
 * Expected difference score: 0.0
 * 
 * Expected recommended edits: 6: None
 */