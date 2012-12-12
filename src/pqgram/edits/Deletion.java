package pqgram.edits;

public class Deletion extends PositionalEdit {
	
	private static String DELETION_STRING = "%d: Delete %s from %s (%d)";

	public Deletion(String a, String b, int start) {
		super(a, b, start);
	}

	@Override
	public String toString() {
		return String.format(DELETION_STRING, this.lineNumber, this.b, this.a, this.start);
	}
}
