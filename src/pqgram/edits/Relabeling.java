package pqgram.edits;

public class Relabeling extends Edit {
	
	private static String RELABELING_STRING = "%d: Relabel %s to %s";
	
	public Relabeling(String a, String b) {
		super(a, b);
	}
	
	@Override
	public String toString() {
		return String.format(RELABELING_STRING, this.lineNumber, this.a, this.b);
	}
}