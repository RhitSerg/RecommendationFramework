package astrecognition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import astrecognition.model.Tree;
import astrecognition.visitors.GeneralVisitor;

/**
 * Builds abstract syntax trees from the given Java workspace
 *
 */
public class ASTBuilder {
	
	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
	private static String PROJECT_LABEL = "Project";
	
	public static Tree getWorkspaceASTs(Class<? extends GeneralVisitor> visitorClass) {
		Tree tree = new Tree(PROJECT_LABEL);
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				if (project.isNatureEnabled(JDT_NATURE)) {
					for (Tree child : getPackages(project, visitorClass)) {
						tree.addChild(child);
					}
				}
			} catch (CoreException e) {
				// Currently not doing anything if this exception occurs. Oh noooooo...
			}
		}
		return tree;
	}
	
	public static Collection<Tree> getPackages(IProject project, Class<? extends GeneralVisitor> visitorClass) throws JavaModelException {
		Collection<Tree> packagesTrees = new ArrayList<Tree>();
		for (IPackageFragment packageFragment : JavaCore.create(project).getPackageFragments()) {
			if (packageFragment.getKind() == IPackageFragmentRoot.K_SOURCE) {
				packagesTrees.addAll(getPackageAST(packageFragment, visitorClass));
			}
		}
		return packagesTrees;
	}
	
	private static Collection<Tree> getPackageAST(IPackageFragment packageFragment, Class<? extends GeneralVisitor> visitorClass) throws JavaModelException {
		Collection<Tree> compilationUnitTrees = new ArrayList<Tree>();
		for (ICompilationUnit unit : packageFragment.getCompilationUnits()) {
			try {
				Constructor<? extends GeneralVisitor> constructor = visitorClass.getDeclaredConstructor(CompilationUnit.class);
				CompilationUnit parse = parse(unit);
				GeneralVisitor visitor = constructor.newInstance(parse);
				parse.accept(visitor);
				compilationUnitTrees.add(visitor.getRoot());
			} catch (NoSuchMethodException | SecurityException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			}
		}
		return compilationUnitTrees;
	}
	
	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		return compilationUnit;
	}
}