package pqgram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import astrecognition.model.Tree;

public class PQGramUniqueLabels {
	public static String STAR_LABEL = "*";

	public static double getDistance(Tree T1, Tree T2, int p, int q) {
		Profile profile = PQGram.getProfile(T1, p, q);
		Profile profile2 = PQGram.getProfile(T2, p, q);
		Profile mUnion = profile.union(profile2);
		Profile mIntersection = profile.intersect(profile2);
		return 1 - (2.0 * mIntersection.size()) / mUnion.size();
	}

	public static Profile getProfile(Tree t, int p, int q) {
		Map<String, Integer> labelIds = new HashMap<String, Integer>();
		Profile profile = new Profile();
		String[] stem = new String[p];
		Arrays.fill(stem, STAR_LABEL);
		profile = getLabelTuples(t, p, q, profile, t, stem, labelIds);
		return profile;
	}

	private static Profile getLabelTuples(Tree g, int p, int q, Profile profile, Tree a, String[] stem, Map<String, Integer> labelIds) {
		String[] base = new String[q];
		Arrays.fill(base, STAR_LABEL);
		stem = shift(stem, a.getUniqueLabel());
		if (a.isLeaf()) {
			profile.add(concatenate(stem, base));
		} else {
			for (Tree c : a.getChildren()) {
				base = shift(base, c.getUniqueLabel());
				profile.add(concatenate(stem, base));
				profile = getLabelTuples(g, p, q, profile, c, stem, labelIds);
			}
			for (int k = 1; k < q; k++) {
				base = shift(base, STAR_LABEL);
				profile.add(concatenate(stem, base));
			}
		}
		return profile;
	}

	private static String[] concatenate(String[] stem, String[] base) {
		String[] result = new String[stem.length + base.length];
		for (int i = 0; i < stem.length; i++) {
			result[i] = stem[i];
		}
		for (int i = stem.length; i < result.length; i++) {
			result[i] = base[i - stem.length];
		}
		return result;
	}

	private static String[] shift(String[] arr, String label) {
		String[] newArr = new String[arr.length];
		for (int i = 1; i < arr.length; i++) {
			newArr[i - 1] = arr[i];
		}
		newArr[arr.length - 1] = label;
		return newArr;
	}
}
