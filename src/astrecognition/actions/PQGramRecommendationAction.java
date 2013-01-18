package astrecognition.actions;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.swt.widgets.List;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import pqgram.PQGramRecommendation;
import pqgram.PQGramUniqueLabels;
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
		Profile sourceProfile = PQGramUniqueLabels.getProfile(sourceMethodBody, 2, 3);
		Profile targetProfile = PQGramUniqueLabels.getProfile(targetMethodBody, 2, 3);
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
		this.setListElements(this.getSourceTargetEdits());
	}

}
