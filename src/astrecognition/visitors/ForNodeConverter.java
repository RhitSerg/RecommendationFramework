package astrecognition.visitors;

import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ForStatement;

import astrecognition.model.Tree;

public class ForNodeConverter {
	public static void convertForToLoop(ForStatement node, Map<ASTNode, Tree> nodes) {
		Tree nodeTree = nodes.get(node);
		Tree blockTree = nodeTree.getChildren().get(nodeTree.getChildren().size()-1);
		while (nodeTree.getChildren().get(0).getLabel().contains("VariableDeclaration")) {
			Tree variableDeclarationTree = nodeTree.getChildren().get(0);
			int nodeTreePosition = nodeTree.getParent().getChildren().indexOf(nodeTree);
			nodeTree.getParent().getChildren().add(nodeTreePosition, variableDeclarationTree);
			nodeTree.getChildren().remove(variableDeclarationTree);
		}
		while (nodeTree.getChildren().size() > 1 && nodeTree.getChildren().get(nodeTree.getChildren().size()-2).getLabel().contains("Assignment")) {
			Tree assignmentTree = nodeTree.getChildren().get(nodeTree.getChildren().size()-2);
			blockTree.addChild(assignmentTree);
			nodeTree.getChildren().remove(assignmentTree);
		}
	}
}
