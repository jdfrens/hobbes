package org.norecess.hobbes.compiler;

import org.antlr.runtime.tree.Tree;

/**
 * @see HobbesPIRBodyCompiler
 * 
 */
public interface IHobbesPIRBodyCompiler {

	public ICode generateEpilog(ICode code);

	public ICode generate(ICode code, Tree ast);

}
