package fromastview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class NodeLabel {
	
	private static boolean SHOW_POSITIONS = false;
	
	private static void appendPosition(ASTNode node, StringBuffer buf) {
		if (SHOW_POSITIONS) {
			buf.append(" [");
			buf.append(node.getStartPosition());
			buf.append(", ");
			buf.append(node.getLength());
			buf.append(']');
		}
	}

	private static void getNodeType(ASTNode node, StringBuffer buf) {
		buf.append(Signature.getSimpleName(node.getClass().getName()));
		appendPosition(node, buf);
		if ((node.getFlags() & ASTNode.MALFORMED) != 0) {
			buf.append(" (malformed)");
		}
		if ((node.getFlags() & ASTNode.RECOVERED) != 0) {
			buf.append(" (recovered)");
		}
	}

	public static String getLabel(ASTNode node) {
		StringBuffer strBuffer = new StringBuffer();
		getNodeType(node, strBuffer);
		return strBuffer.toString();
	}

	public static Object[] getChildren(Object parent) {
		if (parent instanceof ASTAttribute) {
			return ((ASTAttribute) parent).getChildren();
		} else if (parent instanceof ASTNode) {
			return getNodeChildren((ASTNode) parent);
		}
		return new Object[0];
	}

	public static String getText(Object obj) {
		StringBuffer buf = new StringBuffer();
		if (obj instanceof ASTAttribute) {
			buf.append(((ASTAttribute) obj).getLabel());
		}
		return buf.toString(); 
	}

	private static Object[] getNodeChildren(ASTNode node) {
		ArrayList<Object> res = new ArrayList<Object>();

		if (node instanceof Expression) {
			Expression expression = (Expression) node;
			ITypeBinding expressionTypeBinding = expression.resolveTypeBinding();
			res.add(createExpressionTypeBinding(node, expressionTypeBinding));

			// expressions:
			if (expression instanceof Name) {
				IBinding binding= ((Name) expression).resolveBinding();
				if (binding != expressionTypeBinding)
					res.add(createBinding(expression, binding));
			} else if (expression instanceof MethodInvocation) {
				MethodInvocation methodInvocation = (MethodInvocation) expression;
				IMethodBinding binding = methodInvocation.resolveMethodBinding();
				res.add(createBinding(expression, binding));
				String inferred= String.valueOf(methodInvocation.isResolvedTypeInferredFromExpectedType());
				res.add(new GeneralAttribute(expression, "ResolvedTypeInferredFromExpectedType", inferred));
			} else if (expression instanceof SuperMethodInvocation) {
				SuperMethodInvocation superMethodInvocation = (SuperMethodInvocation) expression;
				IMethodBinding binding = superMethodInvocation.resolveMethodBinding();
				res.add(createBinding(expression, binding));
				String inferred = String.valueOf(superMethodInvocation.isResolvedTypeInferredFromExpectedType());
				res.add(new GeneralAttribute(expression, "ResolvedTypeInferredFromExpectedType", inferred));
			} else if (expression instanceof ClassInstanceCreation) {
				ClassInstanceCreation classInstanceCreation = (ClassInstanceCreation) expression;
				IMethodBinding binding = classInstanceCreation.resolveConstructorBinding();
				res.add(createBinding(expression, binding));
				String inferred = String.valueOf(classInstanceCreation.isResolvedTypeInferredFromExpectedType());
				res.add(new GeneralAttribute(expression, "ResolvedTypeInferredFromExpectedType", inferred));
			} else if (expression instanceof FieldAccess) {
				IVariableBinding binding = ((FieldAccess) expression).resolveFieldBinding();
				res.add(createBinding(expression, binding));
			} else if (expression instanceof SuperFieldAccess) {
				IVariableBinding binding = ((SuperFieldAccess) expression).resolveFieldBinding();
				res.add(createBinding(expression, binding));
			} else if (expression instanceof Annotation) {
				IAnnotationBinding binding = ((Annotation) expression).resolveAnnotationBinding();
				res.add(createBinding(expression, binding));
			}
			// Expression attributes:
			res.add(new GeneralAttribute(expression, "Boxing: " + expression.resolveBoxing() + "; Unboxing: " + expression.resolveUnboxing()));
			res.add(new GeneralAttribute(expression, "ConstantExpressionValue", expression.resolveConstantExpressionValue()));

		// references:
		} else if (node instanceof ConstructorInvocation) {
			IMethodBinding binding = ((ConstructorInvocation) node).resolveConstructorBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof SuperConstructorInvocation) {
			IMethodBinding binding = ((SuperConstructorInvocation) node).resolveConstructorBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof MethodRef) {
			IBinding binding = ((MethodRef) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof MemberRef) {
			IBinding binding = ((MemberRef) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof Type) {
			IBinding binding = ((Type) node).resolveBinding();
			res.add(createBinding(node, binding));

		// declarations:
		} else if (node instanceof AbstractTypeDeclaration) {
			IBinding binding = ((AbstractTypeDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof AnnotationTypeMemberDeclaration) {
			IBinding binding = ((AnnotationTypeMemberDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof EnumConstantDeclaration) {
			IBinding binding = ((EnumConstantDeclaration) node).resolveVariable();
			res.add(createBinding(node, binding));
			IBinding binding2 = ((EnumConstantDeclaration) node).resolveConstructorBinding();
			res.add(createBinding(node, binding2));
		} else if (node instanceof MethodDeclaration) {
			IBinding binding = ((MethodDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof VariableDeclaration) {
			IBinding binding = ((VariableDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof AnonymousClassDeclaration) {
			IBinding binding = ((AnonymousClassDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof ImportDeclaration) {
			IBinding binding = ((ImportDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof PackageDeclaration) {
			IBinding binding = ((PackageDeclaration) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof TypeParameter) {
			IBinding binding = ((TypeParameter) node).resolveBinding();
			res.add(createBinding(node, binding));
		} else if (node instanceof MemberValuePair) {
			IBinding binding = ((MemberValuePair) node).resolveMemberValuePairBinding();
			res.add(createBinding(node, binding));
		}		

		List<?> list= node.structuralPropertiesForType();
		for (int i= 0; i < list.size(); i++) {
			StructuralPropertyDescriptor curr = (StructuralPropertyDescriptor) list.get(i);
			res.add(new NodeProperty(node, curr));
		}

		if (node instanceof CompilationUnit) {
			CompilationUnit root = (CompilationUnit) node;
			res.add(new JavaElement(root, root.getJavaElement()));
			res.add(new ProblemsProperty(root));
			res.add(new WellKnownTypesProperty(root));
		}

		return res.toArray();
	}

	private static Binding createBinding(ASTNode parent, IBinding binding) {
		String label = Binding.getBindingLabel(binding);
		return new Binding(parent, label, binding, true);
	}

	private static Object createExpressionTypeBinding(ASTNode parent, ITypeBinding binding) {
		String label= "> (Expression) type binding";
		return new Binding(parent, label, binding, true);
	}

}