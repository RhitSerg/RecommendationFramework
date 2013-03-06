package cfgrecognition.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import pqgram.PQGram;
import pqgram.PQGramDifferenceFinder;
import soot.Body;
import soot.SootClass;
import soot.SootMethod;
import soot.toolkits.graph.ExceptionalUnitGraph;
import astrecognition.Activator;
import astrecognition.Settings;
import astrecognition.views.AbstractView;
import cfgrecognition.loader.SootClassLoader;
import cfgrecognition.model.CFG;
import cfgrecognition.util.Util;
import cfgrecognition.views.ProjectNameDialog;

/**
 * Displays the pq-Gram distance
 */
public class CFGExceptionalUnitGraphAction extends CFGAction {
	private static String DISTANCE_TOOLTIP = "Create an exceptional unit graph for all methods";

	private AbstractView parentView;

	public CFGExceptionalUnitGraphAction(AbstractView parentView) {
		this.parentView = parentView;
		this.setToolTipText(DISTANCE_TOOLTIP);
		this.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}

	private static void instantiateProject() {
		IJavaProject project = null;
		try {
			ProjectNameDialog dialog = new ProjectNameDialog(Display
					.getDefault().getActiveShell());
			project = dialog.getProject();
		} catch (Exception e) {
			Activator.log(Status.ERROR, e.getMessage(), e);
		}

		if (project == null) {
			// this.parentView.showMessage(Activator.PLUGIN_ID,
			// "JavaProject cannot be selected.");
			return;
		}

		SootClassLoader.reset();
		Activator.setIJavaProject(project);
	}

	private static SootClass getExceptionalUnitGraph(String className) {
		instantiateProject();
		SootClassLoader loader = SootClassLoader.instance();
		try {
			loader.process();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loader.getSootClass(className);
	}

	public static List<ExceptionalUnitGraph> getMethodExceptionalUnitGraphs(
			String className) {
		SootClass sootClass = getExceptionalUnitGraph(className);
		List<ExceptionalUnitGraph> methodExceptionalUnitGraphs = new ArrayList<ExceptionalUnitGraph>();
		for (SootMethod sootMethod : sootClass.getMethods()) {
			methodExceptionalUnitGraphs.add(Util.getUnitGraph(sootMethod));
		}
		return methodExceptionalUnitGraphs;
	}

	@Override
	public void run() {
		instantiateProject();

		// TODO: This is where zdummy gets defined and used.
		List<ExceptionalUnitGraph> graphs = getExceptionalUnitGraphs();

		String message = "Amount of graphs = " + graphs.size() + "\n";

		/*
		 * Target Code
		 */
		int i = 1;
		Body b1 = graphs.get(i).getBody();
		CFG cfg1 = getCFG(b1);
		// System.out.println("body 1 =\n" + b1.toString() + "\n");

		/*
		 * User's Code
		 */
		i++;
		Body b2 = graphs.get(i).getBody();
		CFG cfg2 = getCFG(b2);

		// System.out.println(message += "body 2 =\n" + b2.toString() + "\n");

		double distance = PQGram
				.getDistance(cfg1, cfg2, Settings.P, Settings.Q);
		message += "PQGram distance = " + distance + "\n";

		message += "Line Number Differences: ";

		Set<Integer> diffs = PQGramDifferenceFinder.getDifferencesLineNumbers(
				cfg1, cfg2);
		for (Integer d : diffs) {
			message += d + ",";
		}

		this.parentView.showMessage(Activator.PLUGIN_ID, message);
	}
}
