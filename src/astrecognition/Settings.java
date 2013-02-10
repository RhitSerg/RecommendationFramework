package astrecognition;

import astrecognition.visitors.GeneralVisitor;
import astrecognition.visitors.SimplifierVisitor;

public class Settings {
	public static int P = 2;
	public static int Q = 3;
	public static Class<? extends GeneralVisitor> VISITOR_CLASS = SimplifierVisitor.class;
}
