package astrecognition.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;

import astrecognition.model.Tree;
/**
 * Attempt at reforming equivalent node structures during visit
 */
public class SimplifierVisitor extends TreeVisitor {

	public SimplifierVisitor(CompilationUnit compilationUnit) {
		super(compilationUnit);
	}
	
	@Override
	public boolean visit(PrimitiveType node) {
		return true;
	}

	@Override
	public void endVisit(ForStatement node) {
		ForNodeConverter.convertForToLoop(node, this.nodes);
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(VariableDeclarationExpression node) {
		// Trying to get rid of extraneous information
		Tree varDecNode = this.nodes.get(node);
		while ((varDecNode.getChildren().get(0).getOriginalLabel().charAt(0) == '>') || varDecNode.getChildren().get(0).getOriginalLabel().contains("EXTRA")) {
			varDecNode.deleteChild(0);
		}
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(SimpleName node) {
		// Replacing SimpleName nodes with just the identifier
		Tree simpleNameNode = this.nodes.get(node);
		Tree simpleNameParentNode = simpleNameNode.getParent();
		int simpleNamePosition = simpleNameParentNode.getChildren().indexOf(simpleNameNode);
		Tree identifierNode = simpleNameNode.getChildren().get(simpleNameNode.getChildren().size() - 1);
		identifierNode.setParent(simpleNameParentNode);
		simpleNameParentNode.getChildren().remove(simpleNamePosition);
		simpleNameParentNode.getChildren().add(simpleNamePosition, identifierNode);
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(NumberLiteral node) {
		Tree numberLiteralNode = this.nodes.get(node);
		Tree numberLiteralParentNode = numberLiteralNode.getParent();
		int numberLiteralPosition = numberLiteralParentNode.getChildren().indexOf(numberLiteralNode);
		Tree tokenNode = numberLiteralNode.getChildren().get(numberLiteralNode.getChildren().size() - 1);
		tokenNode.setParent(numberLiteralParentNode);
		numberLiteralParentNode.getChildren().remove(numberLiteralPosition);
		numberLiteralParentNode.getChildren().add(numberLiteralPosition, tokenNode);
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(Assignment node) {
		Tree treeNode = this.nodes.get(node);
		treeNode.deleteChild(0); // Deleting Expression type binding
		List<Tree> treeChildren = treeNode.getChildren();
		String operator = AssignmentNodeConverter.getOperator(treeChildren.get(0).getOriginalLabel());
		if (operator.length() > 1) {
			AssignmentNodeConverter.convertToAssignment(node, AssignmentNodeConverter.getLeftOperator(operator), this.nodes);
		}
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(InfixExpression node) {
		this.nodes.get(node).deleteChild(0); // Deleting Expression type binding
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(PrefixExpression node) {
		AssignmentNodeConverter.convertToAssignment(node, this.nodes);
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(PostfixExpression node) {
		AssignmentNodeConverter.convertToAssignment(node, this.nodes);
		super.endVisit(node);
	}

}
