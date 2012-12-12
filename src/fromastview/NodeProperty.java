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

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;

public class NodeProperty extends ASTAttribute {
	
	private ASTNode fParent;
	private StructuralPropertyDescriptor fProperty;
	
	public NodeProperty(ASTNode parent, StructuralPropertyDescriptor property) {
		fParent = parent;
		fProperty = property;
	}
	
	public Object getParent() {
		return fParent;
	}
	
	public Object[] getChildren() {
		Object child = getNode();
		if (child instanceof List) {
			return ((List<?>) child).toArray();
		} else if (child instanceof ASTNode) {
			return new Object[] { child };
		}
		return EMPTY;
	}

	public String getLabel() {
		StringBuffer buf = new StringBuffer();
		buf.append(getPropertyName());
		
		if (fProperty.isSimpleProperty()) {
			buf.append(": ");
			Object node= getNode();
			if (node != null) {
				buf.append('\'');
				buf.append(getNode().toString());
				buf.append('\'');
			} else {
				buf.append("null");
			}
		} else if (fProperty.isChildListProperty()) {
			List<?> node = (List<?>) getNode();
			buf.append(" (").append(node.size()).append(')');
			return "";
		} else { // child property
			if (getNode() == null) {
				buf.append(": null");
			} else {
				return "";
			}
		}
		return buf.toString();
	}
	
	private Object getNode() {
		return fParent.getStructuralProperty(fProperty);
	}
	
	private String getPropertyName() {
		return toConstantName(fProperty.getId());
	}
	
	private static String toConstantName(String string) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			if (i != 0 && Character.isUpperCase(ch)) {
				buf.append('_');
			}
			buf.append(Character.toUpperCase(ch));
		}
		return buf.toString();
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !o.getClass().equals(getClass())) {
			return false;
		}
		NodeProperty castedObj = (NodeProperty) o;
		return  fParent.equals(castedObj.fParent) && (fProperty == castedObj.fProperty);
	}

	public int hashCode() {
		return fParent.hashCode() * 31 + fProperty.hashCode();
	}
	
	public String toString() {
		return getLabel();

	}
}
