package pqgram;

import java.util.HashSet;
import java.util.Set;

import astrecognition.Settings;
import astrecognition.model.Graph;
import astrecognition.model.Tree;
import cfgrecognition.model.CFG;

public class PQGramDifferenceFinder {

	public static Set<Integer> getDifferencesLineNumbers(CFG sourceCFG,
			CFG targetCFG) {
		Profile profile1 = PQGram.getProfile(sourceCFG, Settings.P, Settings.Q);
		Profile profile2 = PQGram.getProfile(targetCFG, Settings.P, Settings.Q);

		Profile common = profile1.intersect(profile2);
		Set<CFG> commonCFGs = buildCommonCFGs(common);
		// Profile missing = profile2.difference(common);
		Profile extra = profile1.difference(common);

		Set<Integer> differences = new HashSet<Integer>();
		for (Tuple<Graph> e : extra.getAllElements()) {

			Graph graph = e.get(Settings.P);

			if (graph instanceof Tree) {
				continue;
			}
			CFG g = (CFG) graph;
			if (!commonCFGs.contains(g)) {
				differences.add(g.getId());
			}
		}

		return differences;
	}

	private static Set<CFG> buildCommonCFGs(Profile common) {
		Set<CFG> commonCFGs = new HashSet<CFG>();
		for (Tuple<Graph> tup : common.getAllElements()) {
			for (int i = 0; i < tup.length(); i++) {
				Graph graph = tup.get(i);
				if (graph instanceof Tree) {
					continue;
				}

				CFG currentCFG = (CFG) graph;
				commonCFGs.add(currentCFG);
			}
		}
		return commonCFGs;
	}
}
