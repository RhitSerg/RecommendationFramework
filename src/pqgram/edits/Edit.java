package pqgram.edits;
/**
 * Base class for tree edits
 */
public abstract class Edit {
	protected String a;
	protected String b;
	protected int lineNumber;
	protected int startPosition;
	protected int endPosition;
	
	public Edit(String a, String b) {
		this(a, b, -1, -1, -1);
	}
	
	public Edit(String a, String b, int lineNumber, int startPosition, int endPosition) {
		this.a = a;
		this.b = b;
		this.lineNumber = lineNumber;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}
	
	public String getA() {
		return this.a;
	}
	
	public void setA(String a) {
		this.a = a;
	}
	
	public String getB() {
		return this.b;
	}
	
	public void setB(String b) {
		this.b = b;
	}
	
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}
	
	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
	
	public int getStartPosition() {
		return this.startPosition;
	}
	
	public int getEndPosition() {
		return this.endPosition;
	}
}
