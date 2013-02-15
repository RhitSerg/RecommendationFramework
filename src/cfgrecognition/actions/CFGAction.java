package cfgrecognition.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;

import soot.Body;
import soot.PatchingChain;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
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
import cfgrecognition.loader.SootClassLoader;
import cfgrecognition.model.CFG;
import cfgrecognition.util.Util;

/**
 * Base class for actions requiring ASTs and pq-Gram functionality extracts
 * CFG's from code
 */
public abstract class CFGAction extends Action {

	public abstract void run();

	public static CFG getCFG(Body b) {
		ExceptionalUnitGraph eug = new ExceptionalUnitGraph(b);

		PatchingChain<Unit> pc = b.getUnits();
		Iterator<Unit> it = pc.snapshotIterator();
		Map<Unit, CFG> unitToCFG = new HashMap<Unit, CFG>();

		int j = 0;

		while (it.hasNext()) {
			Stmt s = (Stmt) it.next();
			String label;

			if (s instanceof AssignStmt) {
				label = "assign"; // condition, local, value
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
				label = "if"; // condition, local, value
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
			CFG c;

			if (null == unitToCFG.get(s)) {
				c = new CFG(s.toString(), j, new Integer(s.getTag(
						"LineNumberTag").toString()));
				c.setExactLabel(s.toString());
				unitToCFG.put(s, c);
				j++;
			} else {
				c = unitToCFG.get(s);
			}

			for (Unit succs : eug.getSuccsOf(s)) {
				if (null == unitToCFG.get(succs)) {
					CFG c2 = new CFG(succs.toString(), j, new Integer(succs
							.getTag("LineNumberTag").toString()));
					j++;
					c2.setExactLabel(succs.toString());
					unitToCFG.put(succs, c2);
					c.addConnection(c2);
				} else {
					c.addConnection(unitToCFG.get(succs));
				}
			}
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