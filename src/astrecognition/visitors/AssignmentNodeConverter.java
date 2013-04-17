package astrecognition.visitors;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;

import astrecognition.model.Tree;

public class AssignmentNodeConverter {
	public static String getOperator(String fullOperatorDescriptor) {
		int lastQuotePosition = fullOperatorDescriptor.indexOf("'", 11);
		return fullOperatorDescriptor.substring(11, lastQuotePosition);
	}
	
	public static String getLeftOperator(String doubleOperator) {
		return "" + doubleOperator.charAt(0);
	}
	
	public static void convertToAssignment(ASTNode node, String operator, Map<ASTNode, Tree> nodes) {
		Tree treeNode = nodes.get(node);
		Tree extraPiece = treeNode.getChildren().get(2);
		Tree assignee = treeNode.getChildren().get(1);
		String variable = assignee.getOriginalLabel();
		treeNode.deleteChild(0);
		treeNode.deleteChild(0);
		treeNode.deleteChild(0);
		Tree eqOp = new Tree("OPERATOR: '='");
		eqOp.setLineNumber(treeNode.getLineNumber());
		eqOp.setStartPosition(treeNode.getStartPosition());
		eqOp.setEndPosition(treeNode.getEndPosition());
		treeNode.addChild(eqOp);
		treeNode.addChild(assignee);
		Tree infixExpressionTree = new Tree("InfixExpression");
		infixExpressionTree.setLineNumber(treeNode.getLineNumber());
		infixExpressionTree.setStartPosition(treeNode.getStartPosition());
		infixExpressionTree.setEndPosition(treeNode.getEndPosition());
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
	
	public static void convertToAssignment(ASTNode node, Map<ASTNode, Tree> nodes) {
		Tree treeNode = nodes.get(node);
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
		infixExpressionTree.setLineNumber(treeNode.getLineNumber());
		infixExpressionTree.setStartPosition(treeNode.getStartPosition());
		infixExpressionTree.setEndPosition(treeNode.getEndPosition());
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
}
