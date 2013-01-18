package astrecognition.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;

import pqgram.PQGram;
import astrecognition.ASTBuilder;
import astrecognition.model.Tree;

/**
 * Base class for actions requiring ASTs and pq-Gram functionality
 */
public abstract class PQGramAction extends Action {
	
	protected static int P = 2;
	protected static int Q = 3;
	
	protected Tree getWorkspaceTree() {
		return ASTBuilder.getWorkspaceASTs();
	}
	
	private Tree getTypeDeclarationTree(Tree workspaceTree) {
		return workspaceTree.getChildren().get(0).getChildren().get(0).getChildren().get(3);
	}
	
	protected Tree getSourceMethodBody(Tree workspaceTree) {
		Tree typeDec = this.getTypeDeclarationTree(workspaceTree);
		return typeDec.getChildren().get(6).getChildren().get(6);
	}
	
	protected Tree getFirstTargetMethodBody(Tree workspaceTree) {
		Tree typeDec = this.getTypeDeclarationTree(workspaceTree);
		return typeDec.getChildren().get(7).getChildren().get(6);
	}
	
	protected List<Tree> getTargetMethods(Tree workspaceTree) {
		Tree typeDec = this.getTypeDeclarationTree(workspaceTree);
		int numMethods = typeDec.getChildren().size() - 7;
		List<Tree> methodTrees = new ArrayList<Tree>();
		for (int i = 0; i < numMethods; i++) {
			methodTrees.add(typeDec.getChildren().get(i + 7));
		}
		return methodTrees;
	}
	
	protected double getSourceTargetDistance() {
		Tree workspaceTree = this.getWorkspaceTree();
		Tree sourceMethodTree = this.getSourceMethodBody(workspaceTree);
		Tree targetMethodTree = this.getFirstTargetMethodBody(workspaceTree);
		return PQGram.getDistance(sourceMethodTree, targetMethodTree, P, Q);
	}
	
	public abstract void run();

}