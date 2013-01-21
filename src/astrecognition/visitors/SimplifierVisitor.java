package astrecognition.visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
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
//		this.skipVisit(node);
		return true;
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
	
	@Override
	public void endVisit(VariableDeclarationExpression node) {
		// Trying to get rid of extraneous information
		Tree varDecNode = this.nodes.get(node);
		while ((varDecNode.getChildren().get(0).getOriginalLabel().charAt(0) == '>') || varDecNode.getChildren().get(0).getOriginalLabel().contains("EXTRA")) {
			varDecNode.deleteChild(0);
		}
//		if (varDecNode.getChildren().size() > 2) {
//			varDecNode.deleteChild(0);
//			varDecNode.deleteChild(0);
//		}
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(PrimitiveType node) {
//		this.nodes.get(node).deleteChild(1); // Deleting repeated type
//		super.endVisit(node);
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
		//this.nodes.get(node).deleteChild(0); // Deleting Expression type binding
		super.endVisit(node);
	}
	
	private static String getOperator(String fullOperatorDescriptor) {
		int lastQuotePosition = fullOperatorDescriptor.indexOf("'", 11);
		return fullOperatorDescriptor.substring(11, lastQuotePosition);
	}
	
	private static String getLeftOperator(String doubleOperator) {
		return "" + doubleOperator.charAt(0);
	}
	
	private void convertToAssignmentNode(ASTNode node, String operator) {
		Tree treeNode = this.nodes.get(node);
		Tree extraPiece = treeNode.getChildren().get(2);
		Tree assignee = treeNode.getChildren().get(1);
		String variable = assignee.getOriginalLabel();
		treeNode.deleteChild(0);
		treeNode.deleteChild(0);
		treeNode.deleteChild(0);
		treeNode.addChild(new Tree("OPERATOR: '='"));
		treeNode.addChild(assignee);
		Tree infixExpressionTree = new Tree("InfixExpression");
		Tree opTree = new Tree("OPERATOR: '" + operator + "'");
		opTree.setLineNumber(treeNode.getLineNumber());
		opTree.setStartPosition(treeNode.getStartPosition());
		opTree.setEndPosition(treeNode.getEndPosition());
		infixExpressionTree.addChild(opTree);
		Tree idTree = new Tree(variable);
		idTree.setLineNumber(treeNode.getLineNumber());
		idTree.setStartPosition(treeNode.getStartPosition());
		idTree.setEndPosition(treeNode.getEndPosition());
		infixExpressionTree.addChild(idTree);
		infixExpressionTree.addChild(extraPiece);
		treeNode.addChild(infixExpressionTree);
	}
	
	@Override
	public void endVisit(Assignment node) {
		Tree treeNode = this.nodes.get(node);
		treeNode.deleteChild(0); // Deleting Expression type binding
		List<Tree> treeChildren = treeNode.getChildren();
		String operator = getOperator(treeChildren.get(0).getOriginalLabel());
		if (operator.length() > 1) {
			this.convertToAssignmentNode(node, getLeftOperator(operator));
		}
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(InfixExpression node) {
		this.nodes.get(node).deleteChild(0); // Deleting Expression type binding
		super.endVisit(node);
	}
	
	private void convertToAssignmentNode(ASTNode node) {
		Tree treeNode = this.nodes.get(node);
		List<Tree> treeChildren = treeNode.getChildren();
		Tree operatorTree = treeChildren.get(1);
		String operator;
		if (operatorTree.getOriginalLabel().contains("++")) {
			operator = "+";
		} else {
			operator = "-";
		}
		Tree variableTree = treeChildren.get(2);
		String variable = variableTree.getOriginalLabel();
		treeNode.deleteChild(0);
		treeNode.deleteChild(0);
		treeNode.deleteChild(0);
		treeNode.setOriginalLabel("Assignment");
		Tree opTree = new Tree("OPERATOR: '='");
		opTree.setLineNumber(treeNode.getLineNumber());
		opTree.setStartPosition(treeNode.getStartPosition());
		opTree.setEndPosition(treeNode.getEndPosition());
		treeNode.addChild(opTree);
		Tree idTree = new Tree(variable);
		idTree.setLineNumber(treeNode.getLineNumber());
		idTree.setStartPosition(treeNode.getStartPosition());
		idTree.setEndPosition(treeNode.getEndPosition());
		treeNode.addChild(idTree);
		Tree infixExpressionTree = new Tree("InfixExpression");
		Tree opTree2 = new Tree("OPERATOR: '" + operator + "'");
		opTree2.setLineNumber(treeNode.getLineNumber());
		opTree2.setStartPosition(treeNode.getStartPosition());
		opTree2.setEndPosition(treeNode.getEndPosition());
		infixExpressionTree.addChild(opTree2);
		Tree idTree2 = new Tree(variable);
		idTree2.setLineNumber(treeNode.getLineNumber());
		idTree2.setStartPosition(treeNode.getStartPosition());
		idTree2.setEndPosition(treeNode.getEndPosition());
		infixExpressionTree.addChild(idTree2);
		Tree tokenTree = new Tree("TOKEN: '1'");
		tokenTree.setLineNumber(treeNode.getLineNumber());
		tokenTree.setStartPosition(treeNode.getStartPosition());
		tokenTree.setEndPosition(treeNode.getEndPosition());
		infixExpressionTree.addChild(tokenTree);
		treeNode.addChild(infixExpressionTree);
	}
	
	@Override
	public void endVisit(PrefixExpression node) {
		this.convertToAssignmentNode(node);
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(PostfixExpression node) {
		this.convertToAssignmentNode(node);
		super.endVisit(node);
	}

}
