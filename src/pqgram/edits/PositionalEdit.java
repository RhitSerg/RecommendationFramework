package pqgram.edits;
/**
 * An edit which requires some positional description
 */
public class PositionalEdit extends Edit {
	protected int start;
	
	public PositionalEdit(String a, String b, int start) {
		super(a, b);
		this.start = start;
	}
	
	public int getPosition() {
		return this.start;
	}
}
