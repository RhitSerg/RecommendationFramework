package pqgram.edits;

import java.util.ArrayList;
import java.util.Collection;

public class Insertion extends PositionalEdit {
	
	private static String INSERTION_STRING = "%d: Insert %s on to %s (%d, %d) {%s}";
	
	private int end;
	private Collection<String> inheritedChildren;
	
	public Insertion(String a, String b, int start, int end) {
		super(a, b, start);
		this.end = end;
		this.inheritedChildren = new ArrayList<String>();
	}
	
	public int getStart() {
		return super.getPosition();
	}
	
	public int getEnd() {
		return this.end;
	}
	
	public void addInheritedChild(String inheritedChild) {
		this.inheritedChildren.add(inheritedChild);
	}

	@Override
	public String toString() {
		String inheritedChildrenList = "";
		for (String inheritedChild : this.inheritedChildren) {
			inheritedChildrenList += inheritedChild + ", ";
		}
		return String.format(INSERTION_STRING, this.lineNumber, this.b, this.a, this.start, this.end, inheritedChildrenList);
	}
}
