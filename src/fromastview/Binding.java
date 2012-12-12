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

import java.util.ArrayList;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.StringLiteral;

public class Binding extends ASTAttribute {
	
	private final IBinding fBinding;
	private final String fLabel;
	private final Object fParent;
	
	public Binding(Object parent, String label, IBinding binding, boolean isRelevant) {
		fParent = parent;
		fBinding = binding;
		fLabel = label;
	}
	
	public Object getParent() {
		return fParent;
	}	
	
	private static boolean isType(int typeKinds, int kind) {
		return (typeKinds & kind) != 0;
	}
	
	public Object[] getChildren() {
		if (fBinding != null) {
			ArrayList<ASTAttribute> res = new ArrayList<ASTAttribute>();
			res.add(new BindingProperty(this, "NAME", fBinding.getName(), true)); 
			res.add(new BindingProperty(this, "KEY", fBinding.getKey(), true)); 
			res.add(new BindingProperty(this, "IS RECOVERED", fBinding.isRecovered(), true)); 
			switch (fBinding.getKind()) {
				case IBinding.VARIABLE:
					IVariableBinding variableBinding = (IVariableBinding) fBinding;
					res.add(new BindingProperty(this, "IS FIELD", variableBinding.isField(), true)); 
					res.add(new BindingProperty(this, "IS ENUM CONSTANT", variableBinding.isEnumConstant(), true)); 
					res.add(new BindingProperty(this, "IS PARAMETER", variableBinding.isParameter(), true)); 
					res.add(new BindingProperty(this, "VARIABLE ID", variableBinding.getVariableId(), true)); 
					res.add(new BindingProperty(this, "MODIFIERS", Flags.toString(fBinding.getModifiers()), true)); 
					res.add(new Binding(this, "TYPE", variableBinding.getType(), true)); 
					res.add(new Binding(this, "DECLARING CLASS", variableBinding.getDeclaringClass(), true)); 
					res.add(new Binding(this, "DECLARING METHOD", variableBinding.getDeclaringMethod(), true)); 
					res.add(new Binding(this, "VARIABLE DECLARATION", variableBinding.getVariableDeclaration(), true)); 
					res.add(new BindingProperty(this, "IS SYNTHETIC", fBinding.isSynthetic(), true)); 
					res.add(new BindingProperty(this, "IS DEPRECATED", fBinding.isDeprecated(), true)); 
					res.add(new BindingProperty(this, "CONSTANT VALUE", variableBinding.getConstantValue(), true));  //$NON-NLS-2$
					break;
				case IBinding.PACKAGE:
					IPackageBinding packageBinding = (IPackageBinding) fBinding;
					res.add(new BindingProperty(this, "IS UNNAMED", packageBinding.isUnnamed(), true)); 
					res.add(new BindingProperty(this, "IS SYNTHETIC", fBinding.isSynthetic(), true)); 
					res.add(new BindingProperty(this, "IS DEPRECATED", fBinding.isDeprecated(), true)); 
					break;
				case IBinding.TYPE:
					ITypeBinding typeBinding = (ITypeBinding) fBinding;
					res.add(new BindingProperty(this, "QUALIFIED NAME", typeBinding.getQualifiedName(), true)); 
					int typeKind = getTypeKind(typeBinding);
					boolean isRefType = isType(typeKind, REF_TYPE);
					final boolean isNonPrimitive = !isType(typeKind, PRIMITIVE_TYPE);
					
					StringBuffer kinds = new StringBuffer("KIND:"); 
					if (typeBinding.isArray()) kinds.append(" isArray"); 
					if (typeBinding.isCapture()) kinds.append(" isCapture"); 
					if (typeBinding.isNullType()) kinds.append(" isNullType"); 
					if (typeBinding.isPrimitive()) kinds.append(" isPrimitive"); 
					if (typeBinding.isTypeVariable()) kinds.append(" isTypeVariable"); 
					if (typeBinding.isWildcardType()) kinds.append(" isWildcardType"); 
					// ref types
					if (typeBinding.isAnnotation()) kinds.append(" isAnnotation"); 
					if (typeBinding.isClass()) kinds.append(" isClass"); 
					if (typeBinding.isInterface()) kinds.append(" isInterface"); 
					if (typeBinding.isEnum()) kinds.append(" isEnum"); 
					res.add(new BindingProperty(this, kinds, true)); 
					
					StringBuffer generics = new StringBuffer("GENERICS:"); 
					if (typeBinding.isRawType()) generics.append(" isRawType"); 
					if (typeBinding.isGenericType()) generics.append(" isGenericType"); 
					if (typeBinding.isParameterizedType()) generics.append(" isParameterizedType"); 
					if (!isType(typeKind, GENERIC | PARAMETRIZED)) {
						generics.append(" (non-generic, non-parameterized)");
					}
					res.add(new BindingProperty(this, generics, isRefType)); 

					res.add(new Binding(this, "ELEMENT TYPE", typeBinding.getElementType(), isType(typeKind, ARRAY_TYPE))); 
					res.add(new Binding(this, "COMPONENT TYPE", typeBinding.getComponentType(), isType(typeKind, ARRAY_TYPE))); 
					res.add(new BindingProperty(this, "DIMENSIONS", typeBinding.getDimensions(), isType(typeKind, ARRAY_TYPE))); 
					final String createArrayTypeLabel = "CREATE ARRAY TYPE (+1)";
					try {
						ITypeBinding arrayType = typeBinding.createArrayType(1);
						res.add(new Binding(this, createArrayTypeLabel, arrayType, true));
					} catch (RuntimeException e) {
						String msg = e.getClass().getName() + ": " + e.getLocalizedMessage();
						boolean isRelevant = ! typeBinding.getName().equals(PrimitiveType.VOID.toString()) && ! typeBinding.isRecovered();
						if (isRelevant) {
							res.add(new Error(this, createArrayTypeLabel + ": " + msg, e));
						} else {
							res.add(new BindingProperty(this, createArrayTypeLabel, msg, false));
						}
					}
					
					res.add(new BindingProperty(this, "TYPE BOUNDS", typeBinding.getTypeBounds(), isType(typeKind, VARIABLE_TYPE | CAPTURE_TYPE))); 
					
					StringBuffer origin = new StringBuffer("ORIGIN:"); 
					if (typeBinding.isTopLevel()) origin.append(" isTopLevel"); 
					if (typeBinding.isNested()) origin.append(" isNested"); 
					if (typeBinding.isLocal()) origin.append(" isLocal"); 
					if (typeBinding.isMember()) origin.append(" isMember"); 
					if (typeBinding.isAnonymous()) origin.append(" isAnonymous"); 
					res.add(new BindingProperty(this, origin, isRefType));
					
					res.add(new BindingProperty(this, "IS FROM SOURCE", typeBinding.isFromSource(), isType(typeKind, REF_TYPE | VARIABLE_TYPE | CAPTURE_TYPE))); 

					res.add(new Binding(this, "PACKAGE", typeBinding.getPackage(), isRefType)); 
					res.add(new Binding(this, "DECLARING CLASS", typeBinding.getDeclaringClass(), isType(typeKind, REF_TYPE | VARIABLE_TYPE | CAPTURE_TYPE))); 
					res.add(new Binding(this, "DECLARING METHOD", typeBinding.getDeclaringMethod(), isType(typeKind, REF_TYPE | VARIABLE_TYPE | CAPTURE_TYPE))); 
					res.add(new BindingProperty(this, "MODIFIERS", Flags.toString(fBinding.getModifiers()), isRefType)); 
					res.add(new BindingProperty(this, "BINARY NAME", typeBinding.getBinaryName(), true)); 
					
					res.add(new Binding(this, "TYPE DECLARATION", typeBinding.getTypeDeclaration(), isNonPrimitive)); 
					res.add(new Binding(this, "ERASURE", typeBinding.getErasure(), isNonPrimitive)); 
					res.add(new BindingProperty(this, "TYPE PARAMETERS", typeBinding.getTypeParameters(), isType(typeKind, GENERIC))); 
					res.add(new BindingProperty(this, "TYPE ARGUMENTS", typeBinding.getTypeArguments(), isType(typeKind, PARAMETRIZED))); 
					res.add(new Binding(this, "BOUND", typeBinding.getBound(), isType(typeKind, WILDCARD_TYPE))); 
					res.add(new BindingProperty(this, "IS UPPERBOUND", typeBinding.isUpperbound(), isType(typeKind, WILDCARD_TYPE))); 
					res.add(new Binding(this, "GENERIC TYPE OF WILDCARD TYPE", typeBinding.getGenericTypeOfWildcardType(), isType(typeKind, WILDCARD_TYPE))); 
					res.add(new BindingProperty(this, "RANK", typeBinding.getRank(), isType(typeKind, WILDCARD_TYPE))); 
					res.add(new Binding(this, "WILDCARD", typeBinding.getWildcard(), isType(typeKind, CAPTURE_TYPE))); 

					res.add(new Binding(this, "SUPERCLASS", typeBinding.getSuperclass(), isRefType)); 
					res.add(new BindingProperty(this, "INTERFACES", typeBinding.getInterfaces(), isRefType)); 			
					res.add(new BindingProperty(this, "DECLARED TYPES", typeBinding.getDeclaredTypes(), isRefType)); 			
					res.add(new BindingProperty(this, "DECLARED FIELDS", typeBinding.getDeclaredFields(), isRefType)); 			
					res.add(new BindingProperty(this, "DECLARED METHODS", typeBinding.getDeclaredMethods(), isRefType)); 			
					res.add(new BindingProperty(this, "IS SYNTHETIC", fBinding.isSynthetic(), isNonPrimitive)); 
					res.add(new BindingProperty(this, "IS DEPRECATED", fBinding.isDeprecated(), isRefType)); 
					break;
				case IBinding.METHOD:
					IMethodBinding methodBinding = (IMethodBinding) fBinding;
					res.add(new BindingProperty(this, "IS CONSTRUCTOR", methodBinding.isConstructor(), true)); 
					res.add(new BindingProperty(this, "IS DEFAULT CONSTRUCTOR", methodBinding.isDefaultConstructor(), true)); 
					res.add(new Binding(this, "DECLARING CLASS", methodBinding.getDeclaringClass(), true)); 
					res.add(new Binding(this, "RETURN TYPE", methodBinding.getReturnType(), true)); 
					res.add(new BindingProperty(this, "MODIFIERS", Flags.toString(fBinding.getModifiers()), true)); 
					res.add(new BindingProperty(this, "PARAMETER TYPES", methodBinding.getParameterTypes(), true)); 
					res.add(new BindingProperty(this, "IS VARARGS", methodBinding.isVarargs(), true)); 
					res.add(new BindingProperty(this, "EXCEPTION TYPES", methodBinding.getExceptionTypes(), true)); 
					
					StringBuffer genericsM = new StringBuffer("GENERICS:"); 
					if (methodBinding.isRawMethod()) genericsM.append(" isRawMethod"); 
					if (methodBinding.isGenericMethod()) genericsM.append(" isGenericMethod"); 
					if (methodBinding.isParameterizedMethod()) genericsM.append(" isParameterizedMethod"); 
					res.add(new BindingProperty(this, genericsM, true));
					
					res.add(new Binding(this, "METHOD DECLARATION", methodBinding.getMethodDeclaration(), true)); 
					res.add(new BindingProperty(this, "TYPE PARAMETERS", methodBinding.getTypeParameters(), true)); 
					res.add(new BindingProperty(this, "TYPE ARGUMENTS", methodBinding.getTypeArguments(), true)); 			
					res.add(new BindingProperty(this, "IS SYNTHETIC", fBinding.isSynthetic(), true)); 
					res.add(new BindingProperty(this, "IS DEPRECATED", fBinding.isDeprecated(), true)); 
					
					res.add(new BindingProperty(this, "IS ANNOTATION MEMBER", methodBinding.isAnnotationMember(), true)); 
					res.add(Binding.createValueAttribute(this, "DEFAULT VALUE", methodBinding.getDefaultValue()));
					
					int parameterCount = methodBinding.getParameterTypes().length;
					BindingProperty[] parametersAnnotations= new BindingProperty[parameterCount];
					for (int i = 0; i < parameterCount; i++) {
						parametersAnnotations[i] = new BindingProperty(this, "Parameter " + String.valueOf(i), methodBinding.getParameterAnnotations(i), true);
					}
					res.add(new BindingProperty(this, "PARAMETER ANNOTATIONS", parametersAnnotations, true));
					break;
				case IBinding.ANNOTATION:
					IAnnotationBinding annotationBinding = (IAnnotationBinding) fBinding;
					res.add(new Binding(this, "ANNOTATION TYPE", annotationBinding.getAnnotationType(), true));
					res.add(new BindingProperty(this, "DECLARED MEMBER VALUE PAIRS", annotationBinding.getDeclaredMemberValuePairs(), true));
					res.add(new BindingProperty(this, "ALL MEMBER VALUE PAIRS", annotationBinding.getAllMemberValuePairs(), true));
					break;
				case IBinding.MEMBER_VALUE_PAIR:
					IMemberValuePairBinding memberValuePairBinding = (IMemberValuePairBinding) fBinding;
					res.add(new Binding(this, "METHOD BINDING", memberValuePairBinding.getMethodBinding(), true));
					res.add(new BindingProperty(this, "IS DEFAULT", memberValuePairBinding.isDefault(), true));
					res.add(Binding.createValueAttribute(this, "VALUE", memberValuePairBinding.getValue()));
					break;
			}
			try {
				IAnnotationBinding[] annotations = fBinding.getAnnotations();
				res.add(new BindingProperty(this, "ANNOTATIONS", annotations, true)); 
			} catch (RuntimeException e) {
				String label = "Error in IBinding#getAnnotations() for \"" + fBinding.getKey() + "\"";
				res.add(new Error(this, label, e));
			}
			try {
				IJavaElement javaElement = fBinding.getJavaElement();
				res.add(new JavaElement(this, javaElement));
			} catch (RuntimeException e) {
				String label = ">java element: " + e.getClass().getName() + " for \"" + fBinding.getKey() + "\"";
				res.add(new Error(this, label, e));
			}
			return res.toArray();
		}
		return EMPTY;
	}

	private final static int ARRAY_TYPE = 1 << 0;
	private final static int NULL_TYPE = 1 << 1;
	private final static int VARIABLE_TYPE = 1 << 2;
	private final static int WILDCARD_TYPE = 1 << 3;
	private final static int CAPTURE_TYPE = 1 << 4;
	private final static int PRIMITIVE_TYPE = 1 << 5;

	private final static int REF_TYPE = 1 << 6;

	private final static int GENERIC = 1 << 8;
	private final static int PARAMETRIZED = 1 << 9;
	
	private int getTypeKind(ITypeBinding typeBinding) {
		if (typeBinding.isArray()) return ARRAY_TYPE;
		if (typeBinding.isCapture()) return CAPTURE_TYPE;
		if (typeBinding.isNullType()) return NULL_TYPE;
		if (typeBinding.isPrimitive()) return PRIMITIVE_TYPE;
		if (typeBinding.isTypeVariable()) return VARIABLE_TYPE;
		if (typeBinding.isWildcardType()) return WILDCARD_TYPE;
		if (typeBinding.isGenericType())  return REF_TYPE | GENERIC;
		if (typeBinding.isParameterizedType() || typeBinding.isRawType()) return REF_TYPE | PARAMETRIZED;
		
		return REF_TYPE;
	}

	public String getLabel() {
		StringBuffer buf = new StringBuffer(fLabel);
		buf.append(": "); 
		if (fBinding != null) {
			switch (fBinding.getKind()) {
				case IBinding.VARIABLE:
					IVariableBinding variableBinding = (IVariableBinding) fBinding;
					if (!variableBinding.isField()) {
						buf.append(variableBinding.getName());
					} else if (variableBinding.getDeclaringClass() == null) {
						buf.append("array type"); 
					} else {
						buf.append(variableBinding.getDeclaringClass().getName());
						buf.append('.');
						buf.append(variableBinding.getName());				
					}
					break;
				case IBinding.PACKAGE:
					IPackageBinding packageBinding = (IPackageBinding) fBinding;
					buf.append(packageBinding.getName());
					break;
				case IBinding.TYPE:
					ITypeBinding typeBinding = (ITypeBinding) fBinding;
					buf.append(typeBinding.getQualifiedName());
					break;
				case IBinding.METHOD:
					IMethodBinding methodBinding = (IMethodBinding) fBinding;
					buf.append(methodBinding.getDeclaringClass().getName());
					buf.append('.');
					buf.append(methodBinding.getName());
					buf.append('(');
					ITypeBinding[] parameters = methodBinding.getParameterTypes();
					for (int i = 0; i < parameters.length; i++) {
						if (i > 0) {
							buf.append(", "); 
						}
						ITypeBinding parameter = parameters[i];
						buf.append(parameter.getName());
					}
					buf.append(')');
					break;
				case IBinding.ANNOTATION:
				case IBinding.MEMBER_VALUE_PAIR:
					buf.append(fBinding.toString());
					break;
			}
			
		} else {
			buf.append("null"); 
		}
		return buf.toString();
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
		
		Binding other = (Binding) obj;
		if (fParent == null) {
			if (other.fParent != null)
				return false;
		} else if (! fParent.equals(other.fParent)) {
			return false;
		}
		
		if (fBinding == null) {
			if (other.fBinding != null)
				return false;
		} else if (! fBinding.equals(other.fBinding)) {
			return false;
		}
		
		if (fLabel == null) {
			if (other.fLabel != null)
				return false;
		} else if (! fLabel.equals(other.fLabel)) {
			return false;
		}
		
		return true;
	}
	
	public int hashCode() {
		int result = fParent != null ? fParent.hashCode() : 0;
		result += (fBinding != null && fBinding.getKey() != null) ? fBinding.getKey().hashCode() : 0;
		result += fLabel != null ? fLabel.hashCode() : 0;
		return result;
	}

	public static String getBindingLabel(IBinding binding) {
		String label;
		if (binding == null) {
			label = ">binding"; 
		} else {
			switch (binding.getKind()) {
				case IBinding.VARIABLE:
					label = "> variable binding"; 
					break;
				case IBinding.TYPE:
					label = "> type binding"; 
					break;
				case IBinding.METHOD:
					label = "> method binding"; 
					break;
				case IBinding.PACKAGE:
					label = "> package binding"; 
					break;
				case IBinding.ANNOTATION:
					label = "> annotation binding"; 
					break;
				case IBinding.MEMBER_VALUE_PAIR:
					label = "> member value pair binding"; 
					break;
				default:
					label = "> unknown binding"; 
			}
		}
		return label;
	}

	/**
	 * Creates an {@link ASTAttribute} for a value from
	 * {@link IMemberValuePairBinding#getValue()} or from
	 * {@link IMethodBinding#getDefaultValue()}.
	 */
	public static ASTAttribute createValueAttribute(ASTAttribute parent, String name, Object value) {
		ASTAttribute res;
		if (value instanceof IBinding) {
			IBinding binding = (IBinding) value;
			res = new Binding(parent, name + ": " + getBindingLabel(binding), binding, true);
		} else if (value instanceof String) {
			res = new GeneralAttribute(parent, name, getEscapedStringLiteral((String) value));
			
		} else if (value instanceof Object[]) {
			res = new GeneralAttribute(parent, name, (Object[]) value);
			
		} else if (value instanceof ASTAttribute) {
			res = (ASTAttribute) value;
		} else {
			res = new GeneralAttribute(parent, name, value);
		}
		return res;
	}
	
	public static String getEscapedStringLiteral(String stringValue) {
		StringLiteral stringLiteral= AST.newAST(AST.JLS4).newStringLiteral();
		stringLiteral.setLiteralValue(stringValue);
		return stringLiteral.getEscapedValue();
	}
	
	public static String getEscapedCharLiteral(char charValue) {
		CharacterLiteral charLiteral= AST.newAST(AST.JLS4).newCharacterLiteral();
		charLiteral.setCharValue(charValue);
		return charLiteral.getEscapedValue();
	}
}
