/*
 * Equals Checker (EQ)
 * 
 * Copyright (C) 2010 Chandan Raj Rupakheti & Daqing Hou, Clarkson University
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either 
 * version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact Us:
 * Chandan Raj Rupakheti (rupakhcr@clarkson.edu)
 * Daqing Hou (dhou@clarkson.edu)
 * Clarkson University
 * PO Box 5722
 * Potsdam
 * NY 13699-5722
 * http://serl.clarkson.edu
 */

package astrecognition.views;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import astrecognition.ASTBuilder;
import astrecognition.Settings;
import astrecognition.model.Tree;

/**
 * 
 * 
 * @author Chandan Raj Rupakheti (rupakhcr@clarkson.edu)
 * 
 */
public class ClassPickerDialog {
	private Shell shell;
	private Shell dialog;
	private Label label;
	private Table table;
	private Button ok;
	private Button cancel;
	private IJavaProject javaProject;
	private Tree classTree;

	public ClassPickerDialog(Shell shell, IJavaProject javaProject) {
		this.shell = shell;
		this.javaProject = javaProject;
	}

	public synchronized Tree getClassTree() throws Exception {
		Collection<Tree> classTrees = ASTBuilder.getPackages(
				this.javaProject.getProject(), Settings.VISITOR_CLASS);
		if (classTrees.size() == 1) {
			this.classTree = classTrees.toArray(new Tree[1])[0];
			return this.classTree;
		}

		createDialog();
		addLabel();
		addTable();
		addButtons();
		addListeners();

		dialog.pack();
		dialog.open();
		while (!dialog.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch())
				shell.getDisplay().sleep();
		}
		return this.classTree;
	}

	public synchronized String getProjectName() throws Exception {
		if (javaProject != null)
			return javaProject.getElementName();
		return "";
	}

	private void createDialog() {
		Rectangle dim = shell.getClientArea();
		dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText("Class Selection Dialog");

		int x = shell.getLocation().x + dim.width / 3;
		int y = shell.getLocation().y + dim.height / 3;
		dialog.setLocation(x, y);

		dialog.setLocation(x, y);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		dialog.setLayout(gridLayout);
		// Handle Escape: Close the dialog
		dialog.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				switch (event.detail) {
				case SWT.TRAVERSE_ESCAPE:
					dialog.close();
					event.detail = SWT.TRAVERSE_NONE;
					event.doit = false;
					break;
				}
			}
		});
	}

	private void addLabel() {
		// Add label to the dialog
		label = new Label(dialog, SWT.PUSH);
		label.setText("Please select a class from the list below:");
		GridData labelGridData = new GridData();
		labelGridData.horizontalAlignment = GridData.FILL;
		labelGridData.horizontalSpan = 2;
		label.setLayoutData(labelGridData);
	}

	private void addTable() throws Exception {
		// Add table to the dialog
		table = new Table(dialog, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		// GridData data = new GridData (SWT.FILL, SWT.FILL, true, true);
		GridData data = new GridData();
		data.heightHint = 200;
		data.horizontalSpan = 2;
		data.horizontalAlignment = SWT.FILL;
		data.verticalAlignment = SWT.FILL;
		table.setLayoutData(data);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn column = new TableColumn(table, SWT.LEAD);
		column.setText("Java Class List");

		TreeSet<Tree> set = new TreeSet<Tree>(new Comparator<Tree>() {
			public int compare(Tree thisPackage, Tree thatPackage) {
				return thisPackage.getLabel().compareTo(thatPackage.getLabel());
			}
		});
		set.addAll(ASTBuilder.getPackages(this.javaProject.getProject(),
				Settings.VISITOR_CLASS));

		for (Tree packageTree : set) {
			TableItem item = new TableItem(table, SWT.NULL);
			item.setText(packageTree.getLabel());
			item.setData(packageTree);
		}
		column.pack();
		column.setWidth(300);
	}

	private void addButtons() {
		// Add ok button to the form
		ok = new Button(dialog, SWT.PUSH);
		ok.setText("OK");
		ok.setLayoutData(new GridData());

		// Add cancel button to the form
		cancel = new Button(dialog, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData());
	}

	private void addListeners() {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == ok || event.widget == table) {
					TableItem[] selection = table.getSelection();
					if (selection == null || selection.length == 0) {
						MessageDialog
								.openError(dialog, "Invalid Package Selection",
										"There was no selection of package. Please select again");
						return;
					}
					if (selection[0] == null) {
						MessageDialog
								.openError(dialog, "Invalid Package Selection",
										"There was no selection of package. Please select again");
						return;
					}
					classTree = (Tree) selection[0].getData();
				}
				dialog.close();
			}
		};
		ok.addListener(SWT.Selection, listener);
		cancel.addListener(SWT.Selection, listener);
		table.addListener(SWT.MouseDoubleClick, listener);
	}
}
