package cfgrecognition.actions;

import org.eclipse.jface.action.Action;

import pqgram.PQGram;
import astrecognition.ASTBuilder;
import astrecognition.model.Tree;

/**
 * Base class for actions requiring ASTs and pq-Gram functionality extracts CFG's from code
 */
public abstract class CFGAction extends Action {
	
	public abstract void run();

}