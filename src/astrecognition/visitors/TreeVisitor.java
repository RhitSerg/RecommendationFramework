package astrecognition.visitors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
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
	private Map<String, Integer> labelIds;
	private CompilationUnit compilationUnit;
	
	public TreeVisitor(CompilationUnit compilationUnit) {
		super();
		this.root = new Tree(ROOT_LABEL);
		this.nodes = new HashMap<ASTNode, Tree>();
		this.labelIds = new HashMap<String, Integer>();
		this.compilationUnit = compilationUnit;
	}
	
	public Tree getRoot() {
		return this.root;
	}

	protected void generalVisit(ASTNode node) {
		Tree current = new Tree(NodeLabel.getLabel(node));
		this.addToTree(node, current);
		addAttributeChildren(node, current);
	}
	
	protected void skipVisit(ASTNode node) {
		Tree current = new Tree(NodeLabel.getLabel(node));
		Tree parentNode = getParentTree(node);
		this.makeLabelUnique(current);
		this.nodes.put(node, parentNode);
	}

	private void addToTree(ASTNode astNode, Tree treeNode) {
		Tree parentNode = getParentTree(astNode);
		parentNode.addChild(treeNode);
		treeNode.setParent(parentNode);
		this.makeLabelUnique(treeNode);
		this.nodes.put(astNode, treeNode);
	}

	protected void makeLabelUnique(Tree treeNode) {
		if (this.labelIds.containsKey(treeNode.getLabel())) {
			int currentValue = this.labelIds.get(treeNode.getLabel());
			this.labelIds.put(treeNode.getLabel(), currentValue + 1);
			treeNode.setLabel(treeNode.getLabel() + " (" + currentValue + ")");
		} else {
			this.labelIds.put(treeNode.getLabel(), 0);
		}
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
				this.makeLabelUnique(newTreeNode);
				treeNode.addChild(newTreeNode);
			}
		}
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		// For now we're comparing one method to another method, so the unique label IDs can start over
		this.labelIds = new HashMap<String, Integer>();
		return super.visit(node);
	}

}
