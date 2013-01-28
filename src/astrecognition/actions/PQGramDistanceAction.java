package astrecognition.actions;

import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import astrecognition.Activator;
import astrecognition.Settings;
import astrecognition.views.AbstractView;

/**
 * Displays the pq-Gram distance
 */
public class PQGramDistanceAction extends PQGramAction {
	private static String DISTANCE_MESSAGE = "%d,%d-Gram distance %f";
	private static String DISTANCE_TOOLTIP = "Calculate 2,3-Gram distance";

	private AbstractView parentView;

	public PQGramDistanceAction(AbstractView parentView) {
		this.parentView = parentView;
		this.setToolTipText(DISTANCE_TOOLTIP);
		this.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}

	@Override
	public void run() {
		this.parentView.showMessage(Activator.PLUGIN_ID, String.format(DISTANCE_MESSAGE,
				Settings.P, Settings.Q, this.getSourceTargetDistance()));
	}

}
