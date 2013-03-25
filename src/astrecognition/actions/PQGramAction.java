package astrecognition.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;

import pqgram.PQGram;
import astrecognition.ASTBuilder;
import astrecognition.Settings;
import astrecognition.model.Tree;

/**
 * Base class for actions requiring ASTs and pq-Gram functionality
 */
public abstract class PQGramAction extends Action {
	
	protected Tree getWorkspaceTree() {
		return ASTBuilder.getWorkspaceASTs(Settings.VISITOR_CLASS);
	}
	
	private Tree getTypeDeclarationTree(Tree workspaceTree) {
		return workspaceTree.getChildren().get(15).getChildren().get(3);
	}
	
	protected Tree getSourceMethodBody(Tree workspaceTree) {
		Tree typeDec = this.getTypeDeclarationTree(workspaceTree);
		return typeDec.getChildren().get(6)
				.getChildren().get(typeDec.getChildren().get(6).getChildren().size()-1);
	}
	
	protected Tree getFirstTargetMethodBody(Tree workspaceTree) {
		Tree typeDec = this.getTypeDeclarationTree(workspaceTree);
		System.out.println("Type Dec: " + typeDec.getParent().getLabel());
		return typeDec.getChildren().get(7)
				.getChildren().get(typeDec.getChildren().get(7).getChildren().size()-1);
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
		return PQGram.getDistance(sourceMethodTree, targetMethodTree, Settings.P, Settings.Q);
	}
	
	public abstract void run();

}