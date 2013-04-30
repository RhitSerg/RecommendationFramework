package astrecognition.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import pqgram.PQGram;
import pqgram.PQGramRecommendation;
import pqgram.Profile;
import pqgram.edits.Edit;
import pqgram.edits.Relabeling;
import astrecognition.model.Graph;
import astrecognition.model.Tree;
/**
 * Finds recommended steps and shows them to the user
 */
public class PQGramRecommendationAction extends PQGramAction implements IPropertyListener {
	
	private static String RECOMMENDATION_TOOLTIP = "Show recommended edits";
	private List steps;
	
	public PQGramRecommendationAction(List steps) {
		this.steps = steps;
		this.setToolTipText(RECOMMENDATION_TOOLTIP);
		this.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}
	
	private Collection<Edit> getSourceTargetEdits() {
		Tree workspaceTree = this.getWorkspaceTree().makeLabelsUnique(new HashMap<String, Integer>());
		Tree sourceMethodBody = this.getSourceMethodBody(workspaceTree);
		Tree targetMethodBody = this.getFirstTargetMethodBody(workspaceTree);
		Profile sourceProfile = PQGram.getProfile(sourceMethodBody, 2, 3);
		Profile targetProfile = PQGram.getProfile(targetMethodBody, 2, 3);
		return PQGramRecommendation.getEdits(sourceProfile, targetProfile, sourceMethodBody, targetMethodBody);
	}
	
	private void setListElements(Collection<?> objects) {
		this.steps.removeAll();
		for (Object object : objects) {
			this.steps.add(object.toString());
		}
	}
	
	private static Map<String, Set<Graph>> getReferences(Tree tree) {
		Map<String, Set<Graph>> references = new HashMap<String, Set<Graph>>();
		Set<Graph> nodes = NodeSet.getNodeSet(tree);
		for (Graph node : nodes) {
			String label = node.getLabel();
			if (label.contains("IDENTIFIER")) {
				String varName = label.substring(label.indexOf('\'')+1, label.length());
				if (!references.containsKey(varName)) {
					references.put(varName, new HashSet<Graph>());
				}
				references.get(varName).add(node);
			}
		}
		return references;
	}
	
	private ArrayList<Relabeling> filterRelabelings(Collection<Edit> edits) {
		ArrayList<Relabeling> relabelings = new ArrayList<Relabeling>();
		for (Edit edit : edits) {
			if (edit instanceof Relabeling) {
				relabelings.add((Relabeling) edit);
			}
		}
		return relabelings;
	}

	@Override
	public void run() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().addPropertyListener(this);
		Tree workspaceTree = this.getWorkspaceTree().makeLabelsUnique(new HashMap<String, Integer>());
		Tree sourceMethodBody = this.getSourceMethodBody(workspaceTree);
		Tree targetMethodBody = this.getFirstTargetMethodBody(workspaceTree);
		Map<String, Set<Graph>> sourceReferences = getReferences(sourceMethodBody);
		Map<String, Set<Graph>> targetReferences = getReferences(targetMethodBody);
		Profile sourceProfile = PQGram.getProfile(sourceMethodBody, 2, 3);
		Profile targetProfile = PQGram.getProfile(targetMethodBody, 2, 3);
		Collection<Edit> edits = PQGramRecommendation.getEdits(sourceProfile, targetProfile, sourceMethodBody, targetMethodBody);
		ArrayList<Relabeling> relabelings = filterRelabelings(edits);
		Map<String, String> unlabeled = new HashMap<String, String>();
		for (String id : sourceReferences.keySet()) {
			for (Graph use : sourceReferences.get(id)) {
				boolean found = false;
				for (Relabeling relabeling : relabelings) {
					if (relabeling.getAG() == use) {
						found = true;
						break;
					}
				}
				if (!found) {
					break;
				}
				// Check if all are mapped to the same thing
				String relabelsTo = relabelings.get(0).getB();
				boolean allSame = true;
				for (int i = 1; i < relabelings.size(); i++) {
					if (relabelings.get(i).getAG() == use && !relabelings.get(i).getB().equals(relabelsTo)) {
						allSame = false;
						break;
					}
				}
				if (allSame) {
					// All are mapped to same id, and that id is not a source variable, so remove them
					unlabeled.put(relabelsTo, relabelings.get(0).getA());
					if (!sourceReferences.keySet().contains(relabelsTo)) {
						for (Relabeling relabeling : relabelings) {
							if (relabeling.getAG() == use) {
								edits.remove(relabeling);
							}
						}
					}
				}
				
				for (Relabeling relabeling : relabelings) {
					if (relabeling.getAG() == use) {
						if (targetReferences.keySet().contains(relabeling.getB()) && unlabeled.containsKey(relabeling.getB())) {
							relabeling.setB(unlabeled.get(relabeling.getB()));
						} else {
							if (!sourceReferences.keySet().contains(relabeling.getB())) {
								edits.remove(relabeling);
							}
						}
					}
				}
			}
		}
		IResource resource = (IResource) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput().getAdapter(IResource.class);
		try {
			resource.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
		Collection<String> translatedEdits = new ArrayList<String>();
		String previousEdit = "";
		int previousEditLine = -1;
		for (Edit edit : edits) {
			String translatedEdit = RecommendationTranslator.translate(edit);
			if (!translatedEdit.isEmpty() && (!translatedEdit.equals(previousEdit) || edit.getLineNumber() != previousEditLine)) {
				translatedEdits.add(translatedEdit);
				IMarker marker;
				try {
					marker = resource.createMarker(IMarker.PROBLEM);
					marker.setAttribute(IMarker.LINE_NUMBER, edit.getLineNumber());
					marker.setAttribute(IMarker.MESSAGE, edit.getLineNumber() + ":" + RecommendationTranslator.translate(edit));
					marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
					marker.setAttribute(IMarker.CHAR_START, edit.getStartPosition());
					marker.setAttribute(IMarker.CHAR_END, edit.getEndPosition());
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			previousEdit = translatedEdit;
			previousEditLine = edit.getLineNumber();
		}
		this.setListElements(translatedEdits);
	}

	@Override
	public void propertyChanged(Object source, int propId) {
//		if (propId == IEditorPart.PROP_DIRTY) {
//			this.run();
//		}
	}

}
