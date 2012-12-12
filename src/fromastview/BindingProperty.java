/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package fromastview;

import org.eclipse.jdt.core.dom.IBinding;

public class BindingProperty extends ASTAttribute {

	private final String fName;
	private final Binding fParent;
	private final ASTAttribute[] fValues;

	public BindingProperty(Binding parent, String name, Object value, boolean isRelevant) {
		fParent = parent;
		if (value instanceof String) {
			if (((String) value).length() == 0) {
				fName = name + ": (empty string)";
			} else {
				fName = name + ": " + Binding.getEscapedStringLiteral((String) value);
			}
		} else if (value instanceof Character) {
			fName = name + ": " + Binding.getEscapedCharLiteral(((Character) value).charValue());
		} else {
			fName = name + ": " + String.valueOf(value);
		}
		fValues = null;
	}
	
	public BindingProperty(Binding parent, String name, boolean value, boolean isRelevant) {
		fParent = parent;
		fName = name + ": " + String.valueOf(value);
		fValues = null;
	}
	
	public BindingProperty(Binding parent, String name, int value, boolean isRelevant) {
		fParent = parent;
		fName = name + ": " + String.valueOf(value);
		fValues = null;
	}
	
	public BindingProperty(Binding parent, String name, IBinding[] bindings, boolean isRelevant) {
		fParent = parent;
		if (bindings == null || bindings.length == 0) {
			fName = name + " (0)";
			fValues = null;
		} else {
			fValues = createBindings(bindings, isRelevant);
			fName = name + " (" + fValues.length + ')';
		}
	}
	
	public BindingProperty(Binding parent, String name, ASTAttribute[] children, boolean isRelevant) {
		fParent = parent;
		if (children == null) {
			children = new ASTAttribute[0];
		}
		fValues = children;
		fName = name + " (" + fValues.length + ')';
	}
	
	public BindingProperty(Binding parent, StringBuffer label, boolean isRelevant) {
		fParent = parent;
		fName = label.toString();
		fValues = null;
	}
	
	private Binding[] createBindings(IBinding[] bindings, boolean isRelevant) {
		Binding[] res = new Binding[bindings.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = new Binding(this, String.valueOf(i), bindings[i], isRelevant);
		}
		return res;
	}

	public Object getParent() {
		return fParent;
	}
	
	public Object[] getChildren() {
		if (fValues != null) {
			return fValues;
		}
		return EMPTY;
	}
	
	public String getLabel() {
		return fName;
	}
	
	public String toString() {
		return getLabel();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}
		
		BindingProperty other= (BindingProperty) obj;
		if (fParent == null) {
			if (other.fParent != null)
				return false;
		} else if (! fParent.equals(other.fParent)) {
			return false;
		}
		
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (! fName.equals(other.fName)) {
			return false;
		}
		
		return true;
	}
	
	public int hashCode() {
		return (fParent != null ? fParent.hashCode() : 0)
				+ (fName != null ? fName.hashCode() : 0);
	}
}
