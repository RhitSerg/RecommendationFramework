package astrecognition.actions;

import java.util.HashSet;
import java.util.Set;

import astrecognition.model.Graph;

public class NodeSet {
	public static Set<Graph> getNodeSet(Graph g) {
		return NodeSet.getNodeSet(g, new HashSet<Graph>());
	}
	
	private static Set<Graph> getNodeSet(Graph g, Set<Graph> visitedGraphs) {
		visitedGraphs.add(g);
		for (Graph h : g.getConnections()) {
			if (!visitedGraphs.contains(h)) {
				NodeSet.getNodeSet(h, visitedGraphs);
			}
		}
		return visitedGraphs;
	}
}
