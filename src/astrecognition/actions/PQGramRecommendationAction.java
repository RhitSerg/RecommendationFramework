package astrecognition.actions;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import pqgram.PQGram;
import pqgram.PQGramRecommendation;
import pqgram.Profile;
import pqgram.edits.Edit;
import astrecognition.model.Tree;
/**
 * Finds recommended steps and shows them to the user
 */
public class PQGramRecommendationAction extends PQGramAction {
	
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

	@Override
	public void run() {
		Collection<Edit> edits = this.getSourceTargetEdits();
		IResource resource = (IResource) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput().getAdapter(IResource.class);
		try {
			resource.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
		for (Edit edit : edits) {
			IMarker marker;
			try {
				marker = resource.createMarker(IMarker.PROBLEM);
				marker.setAttribute(IMarker.LINE_NUMBER, edit.getLineNumber());
				marker.setAttribute(IMarker.MESSAGE, edit.toString());
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				marker.setAttribute(IMarker.CHAR_START, edit.getStartPosition());
				marker.setAttribute(IMarker.CHAR_END, edit.getEndPosition());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		this.setListElements(this.getSourceTargetEdits());
	}

}
