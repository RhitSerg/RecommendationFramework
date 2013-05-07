package astrecognition.actions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.widgets.Display;

import pqgram.PQGram;
import soot.toolkits.graph.ExceptionalUnitGraph;
import astrecognition.ASTBuilder;
import astrecognition.Settings;
import astrecognition.model.Tree;
import astrecognition.views.ProjectPickerDialog;
import cfgrecognition.actions.CFGExceptionalUnitGraphAction;
import cfgrecognition.loader.SootClassLoader;
import cfgrecognition.model.CFG;

public class PQGramTestAction extends PQGramAction {
	@Override
	public void run() {
		ProjectPickerDialog projectPicker = new ProjectPickerDialog(Display
				.getDefault().getActiveShell());
		CFGExceptionalUnitGraphAction.instantiateProject();
		SootClassLoader loader = SootClassLoader.instance();
		FileWriter writer;

		HashSet<String> classLabels = new HashSet<String>();

		try {
			writer = new FileWriter(
					"C:\\Users\\thairp\\Desktop\\test-results.csv");
			writer.append("Name");
			writer.append(',');
			writer.append("Edit Type");
			writer.append(',');
			writer.append("AST distance");
			writer.append(',');
			writer.append("AST node count");
			writer.append(',');
			writer.append("CFG distance");
			writer.append(',');
			writer.append("CFG node count");
			writer.append('\n');
			IJavaProject javaProject = projectPicker.getProject();
			Collection<Tree> classes = ASTBuilder.getPackages(
					javaProject.getProject(), Settings.VISITOR_CLASS);
			for (Tree classTree : classes) {
				String className = classTree.getLabel();
				String[] splitClassName = className.split("\\.");
				// System.out.println("classname: " + className);
				// for (String s : splitClassName) {
				// System.out.println(s);
				// }

				if (className.contains(Settings.DUMMY_NAME))
					continue;
				className = className.substring(0, className.length() - 5);
				Tree typeDeclarationTree = classTree.getChildren()
						.get(classTree.getChildren().size() - 1)
						.makeLabelsUnique(new HashMap<String, Integer>());
				Tree sourceMethodTree = typeDeclarationTree
						.getChildren()
						.get(6)
						.getChildren()
						.get(typeDeclarationTree.getChildren().get(6)
								.getChildren().size() - 1);
				Tree targetMethodTree = typeDeclarationTree
						.getChildren()
						.get(7)
						.getChildren()
						.get(typeDeclarationTree.getChildren().get(7)
								.getChildren().size() - 1);
				double distance = PQGram.getDistance(sourceMethodTree,
						targetMethodTree, Settings.P, Settings.Q);

				List<ExceptionalUnitGraph> methodEUGs = CFGExceptionalUnitGraphAction
						.getMethodExceptionalUnitGraphs(className, loader);
				CFG sourceCFG = CFGExceptionalUnitGraphAction.getCFG(methodEUGs
						.get(1).getBody());
				CFG targetCFG = CFGExceptionalUnitGraphAction.getCFG(methodEUGs
						.get(2).getBody());
				double cfgDistance = PQGram.getDistance(sourceCFG, targetCFG,
						Settings.P, Settings.Q);

				if (!classLabels.contains(splitClassName[0])) {
					classLabels.add(splitClassName[0]);
					writer.append("" + splitClassName[0] + ",,,,,\n");
				}
				writer.append(',');
				writer.append(splitClassName[1]);
				writer.append(',');
				writer.append("" + distance);
				writer.append(',');
				writer.append("" + targetMethodTree.getTotalNodeCount());
				writer.append(',');
				writer.append("" + cfgDistance);
				writer.append(',');
				writer.append("" + targetCFG.getTotalNodeCount());
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
