package astrecognition.visitors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;

/**
 * Base class for visiting all AST node types
 */
public abstract class GeneralVisitor extends ASTVisitor {
	
	protected abstract void generalVisit(ASTNode node);
	protected abstract void skipVisit(ASTNode node);
	protected abstract void generalEndVisit(ASTNode node);
	
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayAccess node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayCreation node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayInitializer node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayType node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(AssertStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(Assignment node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(Block node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(BlockComment node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(BooleanLiteral node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(BreakStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(CastExpression node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(CatchClause node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(CharacterLiteral node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(CompilationUnit node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ConditionalExpression node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ConstructorInvocation node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ContinueStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(DoStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(EmptyStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(FieldAccess node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(InfixExpression node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(InstanceofExpression node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(Initializer node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(Javadoc node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(LabeledStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(LineComment node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MarkerAnnotation node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MemberRef node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MemberValuePair node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodRef node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodRefParameter node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(Modifier node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(NormalAnnotation node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(NullLiteral node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(NumberLiteral node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ParameterizedType node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ParenthesizedExpression node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(PostfixExpression node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(PrefixExpression node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(PrimitiveType node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(QualifiedName node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(QualifiedType node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ReturnStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SimpleName node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SimpleType node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SingleMemberAnnotation node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(StringLiteral node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SuperConstructorInvocation node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SuperFieldAccess node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SuperMethodInvocation node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchCase node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(TagElement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(TextElement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ThisExpression node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ThrowStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(TryStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclarationStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeLiteral node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeParameter node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(UnionType node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationExpression node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		this.generalVisit(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(WildcardType node) {
		this.generalVisit(node);
		return super.visit(node);
	}
	@Override
	public void endVisit(AnnotationTypeDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(AnnotationTypeMemberDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ArrayAccess node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ArrayCreation node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ArrayInitializer node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ArrayType node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(AssertStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(Assignment node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(Block node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(BlockComment node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(BooleanLiteral node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(BreakStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(CastExpression node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(CatchClause node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(CharacterLiteral node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ClassInstanceCreation node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(CompilationUnit node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ConditionalExpression node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ConstructorInvocation node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ContinueStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(DoStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(EmptyStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(EnhancedForStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(EnumConstantDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(EnumDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ExpressionStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(FieldAccess node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(FieldDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ForStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(IfStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ImportDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(InfixExpression node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(InstanceofExpression node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(Initializer node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(Javadoc node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(LabeledStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(LineComment node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(MarkerAnnotation node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(MemberRef node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(MemberValuePair node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(MethodRef node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(MethodRefParameter node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(MethodDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(MethodInvocation node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(Modifier node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(NormalAnnotation node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(NullLiteral node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(NumberLiteral node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(PackageDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ParameterizedType node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ParenthesizedExpression node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(PostfixExpression node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(PrefixExpression node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(PrimitiveType node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(QualifiedName node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(QualifiedType node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ReturnStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SimpleName node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SimpleType node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SingleMemberAnnotation node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SingleVariableDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(StringLiteral node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SuperConstructorInvocation node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SuperFieldAccess node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SuperMethodInvocation node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SwitchCase node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SwitchStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(SynchronizedStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(TagElement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(TextElement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ThisExpression node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(ThrowStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(TryStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(TypeDeclaration node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(TypeDeclarationStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(TypeLiteral node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(TypeParameter node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(UnionType node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(VariableDeclarationExpression node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(VariableDeclarationStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(VariableDeclarationFragment node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(WhileStatement node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}
	@Override
	public void endVisit(WildcardType node) {
		this.generalEndVisit(node);
		super.endVisit(node);
	}

}