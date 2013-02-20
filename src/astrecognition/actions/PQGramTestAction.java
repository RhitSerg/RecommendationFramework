package astrecognition.actions;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.widgets.Display;

import pqgram.PQGram;
import pqgram.PQGramRecommendation;
import pqgram.edits.Edit;
import soot.toolkits.graph.ExceptionalUnitGraph;
import astrecognition.Settings;
import astrecognition.model.Tree;
import astrecognition.views.AbstractView;
import astrecognition.views.ClassPickerDialog;
import astrecognition.views.ProjectPickerDialog;
import cfgrecognition.actions.CFGExceptionalUnitGraphAction;
import cfgrecognition.model.CFG;

public class PQGramTestAction extends PQGramAction {
	private AbstractView parentView;

	public PQGramTestAction(AbstractView parentView) {
		this.parentView = parentView;
	}

	@Override
	public void run() {
		ProjectPickerDialog projectPicker = new ProjectPickerDialog(Display
				.getDefault().getActiveShell());
		String title, message;
		try {
			IJavaProject javaProject = projectPicker.getProject();
			ClassPickerDialog classPicker = new ClassPickerDialog(Display
					.getDefault().getActiveShell(), javaProject);
			Tree classTree = classPicker.getClassTree();
			try {
				String className = classTree.getLabel();
				className = className.substring(0, className.length()-5);
				Tree typeDeclarationTree = classTree.getChildren().get(3).makeLabelsUnique(new HashMap<String, Integer>());
				Tree sourceMethodTree = typeDeclarationTree.getChildren().get(6)
						.getChildren().get(6);
				Tree targetMethodTree = typeDeclarationTree.getChildren().get(7)
						.getChildren().get(6);
				double distance = PQGram.getDistance(sourceMethodTree,
						targetMethodTree, Settings.P, Settings.Q);
				List<Edit> edits = PQGramRecommendation.getEdits(
						PQGram.getProfile(sourceMethodTree, Settings.P, Settings.Q),
						PQGram.getProfile(targetMethodTree,	Settings.P, Settings.Q), 
						sourceMethodTree, 
						targetMethodTree);
				title = String.format("Test results (%s)", className);
				message = String.format("%d,%d-Gram AST distance: %f\n\nNumber of edits: %d\n\nEdits:\n", Settings.P,Settings.Q, distance, edits.size());
				for (Edit edit : edits) {
					message += String.format("%s\n", edit);
				}
				message += "\n";
				
				List<ExceptionalUnitGraph> methodEUGs = CFGExceptionalUnitGraphAction.getMethodExceptionalUnitGraphs(className);
				CFG sourceCFG = CFGExceptionalUnitGraphAction.getCFG(methodEUGs.get(1).getBody());
				CFG targetCFG = CFGExceptionalUnitGraphAction.getCFG(methodEUGs.get(2).getBody());
				double cfgDistance = PQGram.getDistance(sourceCFG, targetCFG, Settings.P, Settings.Q);
				message += String.format("%d,%d-Gram CFG distance: %f\n\n", Settings.P, Settings.Q, cfgDistance);
			} catch (IndexOutOfBoundsException e) {
				title = "Test error";
				message = "ASTs not formatted as expected";
			}
			this.parentView.showMessage(title, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
