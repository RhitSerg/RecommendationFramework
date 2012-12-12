package pqgram.edits;
/**
 * Base class for tree edits
 */
public abstract class Edit {
	protected String a;
	protected String b;
	protected int lineNumber;
	
	public Edit(String a, String b) {
		this(a, b, 0);
	}
	
	public Edit(String a, String b, int lineNumber) {
		this.a = a;
		this.b = b;
		this.lineNumber = lineNumber;
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
}
