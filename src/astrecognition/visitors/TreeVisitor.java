package astrecognition.visitors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;

import astrecognition.model.Tree;
import fromastview.NodeLabel;
/**
 * Build labeled, ordered trees from AST while visiting
 */
public class TreeVisitor extends GeneralVisitor {
	private static String ROOT_LABEL = "Package";
	
	private Tree root;
	protected Map<ASTNode, Tree> nodes;
	private CompilationUnit compilationUnit;
	
	public TreeVisitor(CompilationUnit compilationUnit) {
		super();
		this.root = new Tree(ROOT_LABEL);
		this.nodes = new HashMap<ASTNode, Tree>();
		this.compilationUnit = compilationUnit;
	}
	
	public Tree getRoot() {
		if (this.root.getChildren().size() == 1)
			return this.root.getChildren().get(0);
		return this.root;
	}
	
	@Override
	public void endVisit(CompilationUnit node) {
		Tree cuNode = this.nodes.get(node);
		String name = cuNode.getChildren().get(0).getLabel();
		name = name.substring(name.indexOf(':')+2);
		this.nodes.get(node).setOriginalLabel(name);
		super.endVisit(node);
	}

	protected void generalVisit(ASTNode node) {
		Tree current = new Tree(NodeLabel.getLabel(node));
		current.setStartPosition(node.getStartPosition());
		current.setEndPosition(node.getStartPosition() + node.getLength());
		this.addToTree(node, current);
		addAttributeChildren(node, current);
	}
	
	@Override
	public boolean visit(ExpressionStatement node) {
		this.skipVisit(node);
		return true;
	}
	
	protected void skipVisit(ASTNode node) {
		Tree parentNode = getParentTree(node);
		this.nodes.put(node, parentNode);
	}

	private void addToTree(ASTNode astNode, Tree treeNode) {
		Tree parentNode = getParentTree(astNode);
		parentNode.addChild(treeNode);
		treeNode.setParent(parentNode);
		this.nodes.put(astNode, treeNode);
	}
	
	private Tree getParentTree(ASTNode astNode) {
		ASTNode astParent = astNode.getParent();
		if (astParent == null) {
			return this.root;
		} else {
			return this.nodes.get(astParent);
		}
	}
	
	private void addAttributeChildren(ASTNode astNode, Tree treeNode) {
		int lineNumber = this.compilationUnit.getLineNumber(astNode.getStartPosition());
		treeNode.setLineNumber(lineNumber);
		for (Object object : NodeLabel.getChildren(astNode)) {
			String text = NodeLabel.getText(object);
			if (!text.equals("") && !text.contains("EXPRESSION")){// && !text.contains("variable binding") && !text.contains("IDENTIFIER")) {
				Tree newTreeNode = new Tree(NodeLabel.getText(object));
				newTreeNode.setLineNumber(lineNumber);
				newTreeNode.setStartPosition(astNode.getStartPosition());
				newTreeNode.setEndPosition(astNode.getStartPosition() + astNode.getLength());
				treeNode.addChild(newTreeNode);
			}
		}
	}

}
