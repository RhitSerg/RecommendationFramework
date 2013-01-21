package cfgrecognition.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import pqgram.PQGram;
import soot.Body;
import soot.PatchingChain;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.UnitBox;
import soot.jimple.AssignStmt;
import soot.jimple.BreakpointStmt;
import soot.jimple.DefinitionStmt;
import soot.jimple.EnterMonitorStmt;
import soot.jimple.ExitMonitorStmt;
import soot.jimple.GotoStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.IfStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.LookupSwitchStmt;
import soot.jimple.MonitorStmt;
import soot.jimple.NopStmt;
import soot.jimple.RetStmt;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.jimple.Stmt;
import soot.jimple.TableSwitchStmt;
import soot.jimple.ThrowStmt;
import soot.toolkits.graph.ExceptionalUnitGraph;
import astrecognition.Activator;
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

	@Override
	public void run() {
		IJavaProject project = null;
		try {
			ProjectNameDialog dialog = new ProjectNameDialog(Display
					.getDefault().getActiveShell());
			project = dialog.getProject();
		} catch (Exception e) {
			Activator.log(Status.ERROR, e.getMessage(), e);
		}

		if (project == null) {
			this.parentView.showMessage(Activator.PLUGIN_ID,
					"JavaProject cannot be selected.");
			return;
		}

		Activator.setIJavaProject(project);

		List<ExceptionalUnitGraph> graphs = getExceptionalUnitGraphs();

		String message = "Amount of graphs = " + graphs.size() + "\n";

		/*
		 * Target Code
		 */
		int i = 1;
		Body b1 = graphs.get(i).getBody();
		CFG cfg1 = getCFG(b1);

		message += "body 1 =\n" + b1.toString() + "\n";

		/*
		 * User's Code
		 */
		i++;
		Body b2 = graphs.get(i).getBody();
		CFG cfg2 = getCFG(b2);

		message += "body 2 =\n" + b2.toString() + "\n";

		double distance = PQGram.getDistance(cfg1, cfg2, 2, 3);
		message += "PQGram distance = " + distance;

		this.parentView.showMessage(Activator.PLUGIN_ID, message);
	}

	public static CFG getCFG(Body b) {
		PatchingChain<Unit> pc = b.getUnits();
		Iterator<Unit> it = pc.snapshotIterator();
		Map<Unit, CFG> unitToCFG = new HashMap<Unit, CFG>();
		int j = 0;
		CFG previous = null;

		while (it.hasNext()) {
			Stmt s = (Stmt) it.next();
			String label;

			if (s instanceof AssignStmt) {
				label = "assign";
			} else if (s instanceof BreakpointStmt) {
				label = "breakpoint";
			} else if (s instanceof DefinitionStmt) {
				label = "definition";
			} else if (s instanceof EnterMonitorStmt) {
				label = "enter monitor";
			} else if (s instanceof ExitMonitorStmt) {
				label = "exit monitor";
			} else if (s instanceof GotoStmt) {
				label = "goto";
			} else if (s instanceof IdentityStmt) {
				label = "identity";
			} else if (s instanceof IfStmt) {
				label = "if";
			} else if (s instanceof InvokeStmt) {
				label = "invoke";
			} else if (s instanceof LookupSwitchStmt) {
				label = "lookup switch";
			} else if (s instanceof MonitorStmt) {
				label = "monitor";
			} else if (s instanceof NopStmt) {
				label = "nop";
			} else if (s instanceof RetStmt) {
				label = "ret";
			} else if (s instanceof ReturnStmt) {
				label = "return";
			} else if (s instanceof ReturnVoidStmt) {
				label = "return void";
			} else if (s instanceof TableSwitchStmt) {
				label = "table switch";
			} else if (s instanceof ThrowStmt) {
				label = "throw";
			} else {
				label = "unknown";
			}
			CFG c = new CFG(label, j);
			System.out.println(label + ": " + s.toString());
			unitToCFG.put(s, c);

			if (null != previous) {
				previous.addConnection(c);
			}
			previous = c;

			j++;
		}

		it = pc.snapshotIterator();
		while (it.hasNext()) {
			Stmt s = (Stmt) it.next();
			List<UnitBox> boxesPointingToThis = s.getBoxesPointingToThis();
			CFG current = unitToCFG.get(s);
			for (UnitBox ub : boxesPointingToThis) {
				Unit unit = ub.getUnit();
				CFG parent = unitToCFG.get(unit);
				parent.addConnection(current);
			}
		}
		
		it = pc.snapshotIterator();
		System.out.println("children count");
		while (it.hasNext()) {
			Stmt s = (Stmt) it.next();
			CFG current = unitToCFG.get(s);
			System.out.println(s.toString() + " = " + current.getConnections().size());
		}

		return unitToCFG.get(pc.getFirst());
	}

	public static List<ExceptionalUnitGraph> getExceptionalUnitGraphs() {
		SootClassLoader loader = SootClassLoader.instance();
		List<ExceptionalUnitGraph> graphs = new ArrayList<ExceptionalUnitGraph>();

		try {
			loader.process();
			Collection<SootClass> sootclasses = loader.getAllSootClasses();
			for (SootClass c : sootclasses) {
				List<SootMethod> methods = c.getMethods();

				for (SootMethod m : methods) {
					graphs.add(Util.getUnitGraph(m));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graphs;
	}
}
