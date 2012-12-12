/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package fromastview;

import org.eclipse.jdt.core.compiler.IProblem;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class ProblemsProperty extends ASTAttribute {
	
	private final CompilationUnit fRoot;

	public ProblemsProperty(CompilationUnit root) {
		fRoot= root;
	}

	public Object getParent() {
		return fRoot;
	}

	public Object[] getChildren() {
		IProblem[] problems = fRoot.getProblems();
		Object[] res = new Object[problems.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = new ProblemNode(this, problems[i]);
		}
		return res;
	}

	public String getLabel() {
		return "> compiler problems (" +  fRoot.getProblems().length + ")";
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}
		return true;
	}
	
	public int hashCode() {
		return 18;
	}
}