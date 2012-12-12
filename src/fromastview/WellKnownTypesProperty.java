/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package fromastview;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class WellKnownTypesProperty extends ASTAttribute {
	
	public static final String[] WELL_KNOWN_TYPES = {
		"boolean",
		"byte",
		"char",
		"double",
		"float",
		"int",
		"long",
		"short",
		"void",
		"java.lang.Boolean",
		"java.lang.Byte",
		"java.lang.Character",
		"java.lang.Class",
		"java.lang.Cloneable",
		"java.lang.Double",
		"java.lang.Error",
		"java.lang.Exception",
		"java.lang.Float",
		"java.lang.Integer",
		"java.lang.Long",
		"java.lang.Object",
		"java.lang.RuntimeException",
		"java.lang.Short",
		"java.lang.String",
		"java.lang.StringBuffer",
		"java.lang.Throwable",
		"java.lang.Void",
		"java.io.Serializable",

		"_.$UnknownType$"
	};
	
	private final CompilationUnit fRoot;

	public WellKnownTypesProperty(CompilationUnit root) {
		fRoot= root;
	}

	public Object getParent() {
		return fRoot;
	}

	public Object[] getChildren() {
		AST ast = fRoot.getAST();
		
		Binding[] res = new Binding[WELL_KNOWN_TYPES.length];
		for (int i = 0; i < WELL_KNOWN_TYPES.length; i++) {
			String type = WELL_KNOWN_TYPES[i];
			res[i] = new Binding(this, type, ast.resolveWellKnownType(type), true);
		}
		return res;
	}

	public String getLabel() {
		return "";
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
		return 57;
	}
}