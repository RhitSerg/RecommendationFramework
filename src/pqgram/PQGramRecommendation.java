package pqgram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pqgram.edits.Deletion;
import pqgram.edits.Edit;
import pqgram.edits.Insertion;
import pqgram.edits.PositionalEdit;
import pqgram.edits.Relabeling;
import astrecognition.Settings;
import astrecognition.model.Graph;
import astrecognition.model.Tree;
/**
 * Attempts to find minimal number of steps to transform a source tree to the given target via insertions, deletions, and relabelings
 */
public class PQGramRecommendation {

	public static List<Edit> getEdits(Profile profile1, Profile profile2, Tree sourceTree, Tree targetTree) {
		Profile common = profile1.intersect(profile2);
		Profile missing = profile2.difference(common);
		Profile extra = profile1.difference(common);

		Map<String, Tree> built = new HashMap<String, Tree>();
		Map<String, String> childToParent = new HashMap<String, String>();

		buildCommonTrees(common, built, childToParent);
		
		List<Deletion> deletions = getDeletions(extra, built, childToParent);
		List<Insertion> insertions = getInsertions(missing, built, childToParent);
		
		// minimizing deletions/insertions by finding eligible relabelings and matching up pairs due to relabeling propagations
		Map<String, String> relabelings = RecommendationMinimizer.getRelabelings(insertions, deletions, sourceTree, targetTree);
		RecommendationMinimizer.minimizeDeletions(insertions, deletions, relabelings);
		RecommendationMinimizer.minimizeInsertions(insertions, deletions, relabelings);
				
		List<Edit> edits = new ArrayList<Edit>();
		
		for (String oldName : relabelings.keySet()) {
			if (!oldName.equals(relabelings.get(oldName)) && !sourceTree.find(oldName).getOriginalLabel().equals(targetTree.find(relabelings.get(oldName)).getOriginalLabel())) {
				edits.add(new Relabeling(oldName, relabelings.get(oldName)));
			}
		}
		
		edits.addAll(deletions);
		edits.addAll(insertions);
		
		for (Edit edit : edits) {
			Tree correspondingTree = sourceTree.find(edit.getA());
			if (correspondingTree != null) {
				edit.setLineNumber(correspondingTree.getLineNumber());
				edit.setStartPosition(correspondingTree.getStartPosition());
				edit.setEndPosition(correspondingTree.getEndPosition());
			}
		}
		
		// Condensing edits that correspond to the same place in the code
		Collections.sort(edits, new Comparator<Edit>() {
			public int compare(Edit edit1, Edit edit2) {
				return edit1.getLineNumber() - edit2.getLineNumber();
			}
		});

		return edits;
	}
	
	private static List<Deletion> getDeletions(Profile extra, Map<String, Tree> built, Map<String, String> childToParent) {
		List<PositionalEdit> posEdits = getPositionalEdits(extra, built, childToParent);
		
		List<Deletion> deletions = new ArrayList<Deletion>();
		for (PositionalEdit posEdit : posEdits) {
			deletions.add(new Deletion(posEdit.getA(), posEdit.getB(), posEdit.getPosition()));
		}
		return deletions;
	}
	
	private static List<Insertion> getInsertions(Profile missing, Map<String, Tree> built, Map<String, String> childToParent) {
		List<PositionalEdit> posEdits = getPositionalEdits(missing, built, childToParent);
		
		List<Insertion> insertions = new ArrayList<Insertion>();
		for (PositionalEdit posEdit : posEdits) {
			insertions.add(new Insertion(posEdit.getA(), posEdit.getB(), posEdit.getPosition(), posEdit.getPosition()));
		}
		return insertions;
	}

	private static List<PositionalEdit> getPositionalEdits(Profile pieces, Map<String, Tree> built, Map<String, String> childToParent) {
		pieces = pieces.clone();
		built = Utilities.cloneMap(built);
		childToParent = Utilities.cloneMap(childToParent);
		
		List<PositionalEdit> edits = new ArrayList<PositionalEdit>();
		
		// each 2,3-Gram looks like (ancestor, parent, child1, child2, child3)
		for (Tuple<Graph> tup : pieces.getAllElements()) {
			Tree ancestor = getTree(tup.get(0), built);
			Tree parent = ancestor;
			int position;
			for (int i = 1; i < Settings.P; i++) {
				parent = getTree(tup.get(1), built);
				position = addChildToParent(ancestor, parent, childToParent);
				if (position >= 0) {
					edits.add(new PositionalEdit(ancestor.getUniqueLabel(), parent.getUniqueLabel(), position));
				}
				ancestor = parent;
			}
			for (int i = Settings.P; i < tup.length(); i++) {				
				Graph currentGraph = tup.get(i);
				Tree currentTree = getTree(currentGraph, built);
				position = addChildToParent(parent, currentTree, childToParent);
				if (position >= 0 && !currentGraph.equals(PQGram.STAR_LABEL)) {
					/*Graph left, right;
					if (i == Settings.P) {
						left = null;
						right = tup.get(i+1);
					} else if (i == tup.length() - 1) {
						left = tup.get(i-1);
						right = null;
					} else {
						left = tup.get(i-1);
						right = tup.get(i+1);
					}*/
					edits.add(new PositionalEdit(parent.getUniqueLabel(), currentGraph.getUniqueLabel(), position));
				}
			}
		}
		
		Collections.reverse(edits);
		for (PositionalEdit edit : edits) {
			Tree parent = built.get(edit.getA());
			parent.deleteChild(edit.getPosition());
		}
		Collections.reverse(edits);
		return edits;
	}
	
	// if tree has already been constructed, grab it; otherwise create it and add it
	private static Tree getTree(String label, Map<String, Tree> builtTrees) {
		if (!builtTrees.containsKey(label)) {
			builtTrees.put(label, new Tree(label));
		}
		return builtTrees.get(label);
	}
	
	private static Tree getTree(Graph graph, Map<String, Tree> builtTrees) {
		return getTree(graph.getUniqueLabel(), builtTrees);
	}
	
	// hook up a child to its parent if not already done and return position; return -1 if already done
	private static int addChildToParent(Tree parent, Tree child, Map<String, String> childToParent) {
		if (!childToParent.containsKey(child.getUniqueLabel())) {
			childToParent.put(child.getUniqueLabel(), parent.getUniqueLabel());
			return parent.addChild(child);
		}
		return -1;
	}

	private static Set<Tree> buildCommonTrees(Profile common, Map<String, Tree> built, Map<String, String> childToParent) {
		Set<Tree> commonTrees = new HashSet<Tree>();
		for (Tuple<Graph> tup : common.getAllElements()) {
			Tree ancestor = getTree(tup.get(0), built);
			commonTrees.add(ancestor);
			Tree parent = ancestor;
			for (int i = 1; i < Settings.P; i++) {
				parent = getTree(tup.get(1), built);
				addChildToParent(parent, ancestor, childToParent);
				ancestor = parent;
			}
			for (int i = Settings.P; i < tup.length(); i++) {				
				Graph currentGraph = tup.get(i);
				Tree currentTree = getTree(currentGraph, built);
				commonTrees.add(currentTree);
				addChildToParent(parent, currentTree, childToParent);
				commonTrees.remove(currentTree);
			}
		}
		if (commonTrees.size() == 1) {
			Tree falseRoot = commonTrees.toArray(new Tree[1])[0];
			if (falseRoot.getUniqueLabel().equals(PQGram.STAR_LABEL)) {
				commonTrees.remove(falseRoot);
				commonTrees.add(falseRoot.getChildren().get(0));
			}
		}
		return commonTrees;
	}
}