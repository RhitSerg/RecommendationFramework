package astrecognition.model;

import java.util.ArrayList;
import java.util.List;

public class Tree {
	protected Tree parent;
	protected List<Tree> children;
	private String label;
	private int lineNumber;

	public Tree(Tree parent, String label) {
		this.label = label;
		this.parent = parent;
		this.children = new ArrayList<Tree>();
		this.lineNumber = 0;
	}
	
	public Tree(String label) {
		this(null, label);
	}

	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Tree getParent() {
		return this.parent;
	}
	
	public void setParent(Tree parent) {
		this.parent = parent;
	}

	public int addChild(Tree tree) {
		this.children.add(tree);
		return this.children.size() - 1;
	}
	
	public void deleteChild(int position) {
		this.children.remove(position);
	}

	public List<Tree> getChildren() {
		return this.children;
	}

	public boolean isLeaf() {
		return this.children.size() == 0;
	}
	
	public boolean isDescendant(Tree tree) {
		if (children.contains(tree)) {
			return true;
		}

		for (Tree t : children) {
			if (t.isDescendant(tree)) {
				return true;
			}
		}

		return false;
	}

	public Tree find(String label) {
		if (this.label.equals(label)) {
			return this;
		} else {
			for (Tree t : this.children) {
				Tree goal = t.find(label);
				if (null != goal) {
					return goal;
				}
			}
		}
		return null;
	}
	
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return this.lineNumber;
	}

	public String toString() {
		StringBuilder treeString = new StringBuilder();
		treeString.append(this.label);
		if (this.children.size() > 0) {
			treeString.append("\n");
		}
		for (Tree child : this.children) {
			treeString.append(this.label + ":" + child.toString());
			treeString.append("\n");
		}
		return treeString.toString();
	}
}
