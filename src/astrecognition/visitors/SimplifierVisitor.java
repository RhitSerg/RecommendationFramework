package astrecognition.visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;

import astrecognition.model.Tree;
/**
 * Attempt at reforming equivalent node structures during visit
 */
public class SimplifierVisitor extends TreeVisitor {
	
	public SimplifierVisitor(CompilationUnit compilationUnit) {
		super(compilationUnit);
	}

	@Override
	public void endVisit(DoStatement node) {
		Tree nodeTree = this.nodes.get(node);
		int numChildren = nodeTree.getChildren().size();
		List<Tree> nodeTreeChildren = new ArrayList<Tree>();
		for (int i = 0; i < numChildren; i++) {
			nodeTreeChildren.add(nodeTree.getChildren()
					.get(numChildren - i - 1));
			nodeTree.deleteChild(numChildren - i - 1);
		}
		for (int i = 0; i < numChildren; i++) {
			nodeTree.addChild(nodeTreeChildren.get(i));
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(ForStatement node) {
		int assignmentPosition = 2;
		int blockAfterAssignmentRemovedPositioned = 2;
		int varDeclarationPosition = 0;
		Tree nodeTree = this.nodes.get(node);
		Tree assignmentTree = nodeTree.getChildren().get(assignmentPosition);
		nodeTree.deleteChild(assignmentPosition);
		nodeTree.getChildren().get(blockAfterAssignmentRemovedPositioned).addChild(assignmentTree);
		Tree varDeclarationTree = nodeTree.getChildren().get(varDeclarationPosition);
		int nodeTreePosition = nodeTree.getParent().getChildren().indexOf(nodeTree);
		nodeTree.getParent().getChildren().add(nodeTreePosition, varDeclarationTree);
		nodeTree.deleteChild(varDeclarationPosition);
		super.endVisit(node);
	}

}
