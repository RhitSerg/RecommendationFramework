package astrecognition.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.eclipse.swt.widgets.List;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import pqgram.PQGram;
import pqgram.PQGramRecommendation;
import pqgram.Profile;
import pqgram.edits.Edit;
import astrecognition.model.Tree;
import astrecognition.views.AbstractView;
/**
 * Finds recommended steps and shows them to the user
 */
public class PQGramBestRecommendationAction extends PQGramAction {
	
	private static String RECOMMENDATION_TOOLTIP = "Show recommended edits for best matching method";
	private List steps;
	private AbstractView parentView;
	
	public PQGramBestRecommendationAction(List steps, AbstractView parentView) {
		this.steps = steps;
		this.parentView = parentView;
		this.setToolTipText(RECOMMENDATION_TOOLTIP);
		this.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}
	
	private Collection<Edit> getSourceTargetEdits() {
		Tree workspaceTree = this.getWorkspaceTree().makeLabelsUnique(new HashMap<String, Integer>());
		final Tree sourceMethodBody = this.getSourceMethodBody(workspaceTree);
		Collection<Tree> targetMethodBodies = this.getTargetMethods(workspaceTree);
		Tree bestTargetMethodBody = Collections.max(targetMethodBodies, new Comparator<Tree>() {
			public int compare(Tree targetTree1, Tree targetTree2) {
				return (int) (PQGram.getDistance(sourceMethodBody, targetTree1.getChildren().get(6), PQGramAction.P, PQGramAction.Q) - PQGram.getDistance(sourceMethodBody, targetTree2.getChildren().get(6), PQGramAction.P, PQGramAction.Q));
			}
		});
		this.parentView.showMessage("Matched method", "Your source code best matched " + bestTargetMethodBody.getChildren().get(5).getChildren().get(0).getOriginalLabel());
		Profile sourceProfile = PQGram.getProfile(sourceMethodBody, 2, 3);
		Profile targetProfile = PQGram.getProfile(bestTargetMethodBody.getChildren().get(6), 2, 3);
		return PQGramRecommendation.getEdits(sourceProfile, targetProfile, sourceMethodBody, bestTargetMethodBody);
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
