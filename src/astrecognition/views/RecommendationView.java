package astrecognition.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IActionBars;

import astrecognition.actions.PQGramDistanceAction;
import astrecognition.actions.PQGramRecommendationAction;

public class RecommendationView extends AbstractView {
	
	private Action pqGramDistanceAction;
	private Action pqGramRecommendationAction;
	private List recommendedEdits;

	@Override
	public void createPartControl(Composite parent) {
		this.recommendedEdits = new List(parent, 0);
		this.makeActions();
		this.contributeToActionBars();
	}

	@Override
	public void setFocus() {
		this.recommendedEdits.setFocus();
	}
	
	private void contributeToActionBars() {
		IActionBars bars = this.getViewSite().getActionBars();
		this.fillLocalToolBar(bars.getToolBarManager());
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(this.pqGramDistanceAction);
		manager.add(new Separator());
		manager.add(this.pqGramRecommendationAction);
	}

	private void makeActions() {
		this.pqGramDistanceAction = new PQGramDistanceAction(this);
		this.pqGramRecommendationAction = new PQGramRecommendationAction(this.recommendedEdits);
	}

}