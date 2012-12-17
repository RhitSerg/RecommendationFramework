package astrecognition.actions;

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
		return typeDec.getChildren().get(6).getChildren().get(7);
	}
	
	protected Tree getTargetMethodBody(Tree workspaceTree) {
		Tree typeDec = this.getTypeDeclarationTree(workspaceTree);
		return typeDec.getChildren().get(7).getChildren().get(7);
	}
	
	protected double getSourceTargetDistance() {
		Tree workspaceTree = this.getWorkspaceTree();
		Tree sourceMethodTree = this.getSourceMethodBody(workspaceTree);
		Tree targetMethodTree = this.getTargetMethodBody(workspaceTree);
		return PQGram.getDistance(sourceMethodTree, targetMethodTree, P, Q);
	}
	
	public abstract void run();

}