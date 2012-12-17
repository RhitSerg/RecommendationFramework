package astrecognition.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import astrecognition.ASTBuilder;
import astrecognition.model.Tree;

public class ASTView extends AbstractView {
	
	private TreeViewer viewer;

	@Override
	public void createPartControl(Composite parent) {
		this.viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
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
		manager.add(new Action() {
			public void run() {
				ASTView.this.viewer.setContentProvider(new ViewContentProvider());
			}
		});
	}
	
	private class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {
		private Tree root;
		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (this.root == null)
					root = ASTBuilder.getWorkspaceASTs();
				return getChildren(root);
			}
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof Tree) {
				return ((Tree) child).getParent();
			}
			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof Tree) {
				return ((Tree) parent).getChildren().toArray();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof Tree)
				return !((Tree) parent).isLeaf();
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
			if (obj instanceof Tree) {
				Tree tree = (Tree) obj;
				if (!tree.isLeaf()) {
					imageKey = ISharedImages.IMG_OBJ_FOLDER;
				}
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}
	
}
