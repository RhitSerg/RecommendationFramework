package pqgram;

import java.util.Arrays;
import java.util.HashSet;

import astrecognition.model.Graph;
import astrecognition.model.Tree;
import cfgrecognition.model.CFG;

/**
 * Computes pq-Gram distance (adapted from
 * http://www.vldb2005.org/program/paper/wed/p301-augsten.pdf)
 */
public class PQGram {
	public static String STAR_LABEL = "*";

	public static double getDistance(Graph T1, Graph T2, int p, int q) {
		Profile profile = PQGram.getProfile(T1, p, q);
		Profile profile2 = PQGram.getProfile(T2, p, q);
		Profile mUnion = profile.union(profile2);
		Profile mIntersection = profile.intersect(profile2);
		return 1 - (2.0 * mIntersection.size()) / mUnion.size();
	}

	public static Profile getProfile(Graph t, int p, int q) {
		HashSet<Graph> visited = new HashSet<Graph>();

		Profile profile = new Profile();
		Graph[] stem = new Graph[p];
		Arrays.fill(stem, new Tree(STAR_LABEL));
		profile = getLabelTuples(p, q, profile, t, stem, visited);
		return profile;
	}

	private static Profile getLabelTuples(int p, int q, Profile profile,
			Graph a, Graph[] stem, HashSet<Graph> visited) {
		if (visited.contains(a)) {
			return profile;
		}

		visited.add(a);

		Graph[] base = new Graph[q];
		Arrays.fill(base, new Tree(STAR_LABEL));
		stem = shift(stem, a);
		if (a.isSink()) {
			profile.add(concatenate(stem, base));
		} else {
			for (Graph c : a.getConnections()) {
				base = shift(base, c);
				profile.add(concatenate(stem, base));
				profile = getLabelTuples(p, q, profile, c, stem, visited);
			}
			for (int k = 1; k < q; k++) {
				base = shift(base, new Tree(STAR_LABEL));
				profile.add(concatenate(stem, base));
			}
		}
		return profile;
	}

	private static Graph[] concatenate(Graph[] stem, Graph[] base) {
		Graph[] result = new Graph[stem.length + base.length];
		for (int i = 0; i < stem.length; i++) {
			result[i] = stem[i];
		}
		for (int i = stem.length; i < result.length; i++) {
			result[i] = base[i - stem.length];
		}
		return result;
	}

	private static Graph[] shift(Graph[] arr, Graph graph) {
		Graph[] newArr = new Graph[arr.length];
		for (int i = 1; i < arr.length; i++) {
			newArr[i - 1] = arr[i];
		}
		newArr[arr.length - 1] = graph;
		return newArr;
	}

}
