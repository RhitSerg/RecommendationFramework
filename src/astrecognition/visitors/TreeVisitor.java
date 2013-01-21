package astrecognition.visitors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import astrecognition.model.Tree;
import fromastview.NodeLabel;
/**
 * Build labeled, ordered trees from AST while visiting
 */
public class TreeVisitor extends GeneralVisitor {
	private static String ROOT_LABEL = "Project";
	
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
		return this.root;
	}

	protected void generalVisit(ASTNode node) {
		Tree current = new Tree(NodeLabel.getLabel(node));
		current.setStartPosition(node.getStartPosition());
		current.setEndPosition(node.getStartPosition() + node.getLength());
		this.addToTree(node, current);
		addAttributeChildren(node, current);
	}
	
	protected void generalEndVisit(ASTNode node) {
		// LABEL UNIQUENESS DONE IN PQ-GRAM, nothing left here right now
	}
	
	@Override
	public boolean visit(ExpressionStatement node) {
		this.skipVisit(node); // SKIPPING EXPRESSION STATEMENTS BECAUSE THEY HAVE NO USE
		return true;
//		return super.visit(node);
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
			if (!text.equals("")) {
				Tree newTreeNode = new Tree(NodeLabel.getText(object));
				newTreeNode.setLineNumber(lineNumber);
				newTreeNode.setStartPosition(astNode.getStartPosition());
				newTreeNode.setEndPosition(astNode.getStartPosition() + astNode.getLength());
				treeNode.addChild(newTreeNode);
			}
		}
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		// LABEL UNIQUENESS DONE IN PQ-GRAM, nothing left here right now
		return super.visit(node);
	}

}
