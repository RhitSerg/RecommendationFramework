package astrecognition.actions;

import java.util.ArrayList;
import java.util.Collection;

import pqgram.edits.Deletion;
import pqgram.edits.Edit;
import pqgram.edits.Insertion;
import pqgram.edits.Relabeling;

public class RecommendationTranslator {
	
	public static Collection<String> translate(Collection<Edit> edits) {
		Collection<String> editStrings = new ArrayList<String>();
		for (Edit edit : edits) {
			String translatedEdit = translate(edit);
			if (!translatedEdit.isEmpty()) {
				editStrings.add(translatedEdit);
			}
		}
		return editStrings;
	}
	
	public static String translate(Edit edit) {
		if (edit instanceof Insertion) {
			return translateInsertion((Insertion) edit);
		} else if (edit instanceof Deletion) {
			return translateDeletion((Deletion) edit);
		} else if (edit instanceof Relabeling) {
			return translateRelabeling((Relabeling) edit);
		} else {
			return "";
		}
	}
	
	private static boolean isUsable(String label) {
		String[] readableLabels = new String[] {"IDENTIFIER", "TOKEN", "OPERATOR", "BOOLEAN_VALUE"};
		for (String readableLabel : readableLabels) {
			if (label.contains(readableLabel)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isUsable(Edit edit) {
		return isUsable(edit.getA()) && isUsable(edit.getB());
	}
	
	private static String getType(String nodeLabel) {
		String[] types = new String[] {"IfStatement", "Assignment"};
		for (String type : types) {
			if (nodeLabel.contains(type)) {
				return type;
			}
		}
		return nodeLabel;
	}
	
	private static String translateInsertion(Insertion insertion) {
		if (isUsable(insertion)) {
			return insertion.toString();
		} else {
			String insertee = getType(insertion.getA());
			String newInsert = getType(insertion.getB());
			if (insertee.equals("IfStatement")) {
				return "Missing else block";
			} else if (newInsert.equals("Assignment")) {
				return "Missing assignment";
			} else if (newInsert.equals("IfStatement")) {
				return "Missing if statement";
			}
			return "Insert " + getReadableLabel(newInsert) + " onto " + getReadableLabel(insertee);
		}
	}
	
	private static String translateDeletion(Deletion deletion) {
		if (isUsable(deletion)) {
			return deletion.toString();
		} else {
			return "Delete unnecessary code.";
		}
	}
	
	private static String getReadableLabel(String label) {
		int start, end;
		if (label.contains("OPERATOR") || label.contains("IDENTIFIER") || label.contains("BOOLEAN_VALUE")) {
			start = label.indexOf(':') + 1;
			end = label.length();
			if (label.indexOf(':', start+1) > 0) {
				end = label.indexOf(':', start+1);
			}
			return label.substring(start, end).trim();
		}
		return label;
	}
	
	private static String translateRelabeling(Relabeling relabeling) {
		if (isUsable(relabeling)) {
			String oldLabel = relabeling.getA();
			String newLabel = relabeling.getB();
			return "Change " + getReadableLabel(oldLabel) + " to " + getReadableLabel(newLabel);
		} else {
			return relabeling.toString();
		}
	}

}