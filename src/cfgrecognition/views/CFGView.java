package cfgrecognition.views;

import java.util.List;

import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import soot.Body;
import soot.toolkits.graph.ExceptionalUnitGraph;
import astrecognition.Activator;
import astrecognition.model.Graph;
import astrecognition.views.AbstractView;
import cfgrecognition.actions.CFGExceptionalUnitGraphAction;
import cfgrecognition.model.CFG;

public class CFGView extends AbstractView {

	private TreeViewer viewer;

	@Override
	public void createPartControl(Composite parent) {
		this.viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		ViewContentProvider viewContentProvider = new ViewContentProvider();

		this.viewer.setContentProvider(viewContentProvider);
		this.viewer.setLabelProvider(new ViewLabelProvider());
		this.viewer.setInput(getViewSite());
		this.contributeToActionBars();
	}

	@Override
	public void setFocus() {
		this.viewer.getControl().setFocus();
	}

	private void contributeToActionBars() {
		IActionBars bars = this.getViewSite().getActionBars();
		this.fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(new CFGExceptionalUnitGraphAction(this));
		manager.add(new Separator());
		manager.add(new Action() {
			public void run() {
				CFGView.this.viewer
						.setContentProvider(new ViewContentProvider());
			}
		});
	}

	private class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {
		private CFG root;

		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (this.root == null) {
					IJavaProject project = null;
					try {
						ProjectNameDialog dialog = new ProjectNameDialog(
								Display.getDefault().getActiveShell());

						project = dialog.getProject();
					} catch (Exception e) {
						Activator.log(Status.ERROR, e.getMessage(), e);
					}

					Activator.setIJavaProject(project);

					List<ExceptionalUnitGraph> graphs = CFGExceptionalUnitGraphAction
							.getExceptionalUnitGraphs();
					Body b1 = graphs.get(1).getBody();
					this.root = CFGExceptionalUnitGraphAction.getCFG(b1);
					return new Object[] { this.root };
				}

				return getChildren(root);
			}
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof Graph) {
				return ((CFG) child).getParent();
			}
			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof Graph) {
				return ((CFG) parent).getConnections().toArray();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof Graph) {
				return !((CFG) parent).isSink();
			}
			return false;
		}

		public void dispose() {

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}
	}

	private class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof Graph) {
				CFG cfg = (CFG) obj;
				if (!cfg.isSink()) {
					imageKey = ISharedImages.IMG_OBJ_FOLDER;
				}
			}
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(imageKey);
		}
	}

}
