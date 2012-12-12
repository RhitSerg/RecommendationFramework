package astrecognition.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.part.ViewPart;

/**
 * Base view for showing dialogs
 */
public abstract class AbstractView extends ViewPart {	
	public void showMessage(String title, String message) {
		MessageDialog.openInformation(this.getViewSite().getShell(), title, message);
	}
}
