package astrecognition;

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
import astrecognition.visitors.SimplifierVisitor;
import astrecognition.visitors.TreeVisitor;

/**
 * Builds abstract syntax trees from the given Java workspace
 *
 */
public class ASTBuilder {
	
	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
	private static String PROJECT_LABEL = "Project";
	
	public static Tree getWorkspaceASTs() {
		Tree tree = new Tree(PROJECT_LABEL);
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				if (project.isNatureEnabled(JDT_NATURE)) {
					for (Tree child : getPackages(project)) {
						tree.addChild(child);
					}
				}
			} catch (CoreException e) {
				// Currently not doing anything if this exception occurs. Oh noooooo...
			}
		}
		return tree;
	}
	
	private static Collection<Tree> getPackages(IProject project) throws JavaModelException {
		Collection<Tree> packagesTrees = new ArrayList<Tree>();
		for (IPackageFragment packageFragment : JavaCore.create(project).getPackageFragments()) {
			if (packageFragment.getKind() == IPackageFragmentRoot.K_SOURCE) {
				packagesTrees.addAll(getPackageAST(packageFragment));
			}
		}
		return packagesTrees;
	}
	
	private static Collection<Tree> getPackageAST(IPackageFragment packageFragment) throws JavaModelException {
		Collection<Tree> compilationUnitTrees = new ArrayList<Tree>();
		for (ICompilationUnit unit : packageFragment.getCompilationUnits()) {
			CompilationUnit parse = parse(unit);
			TreeVisitor visitor = new SimplifierVisitor(parse); // Working on converting loops and other equivalent forms
			//TreeVisitor visitor = new TreeVisitor(); // This just produces the straightforward ASTs
			parse.accept(visitor);

			compilationUnitTrees.add(visitor.getRoot());
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