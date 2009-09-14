package org.norecess.hobbes.compiler;

import org.antlr.runtime.tree.Tree;

/**
 * @see HobbesPIRComponentCompiler
 * 
 */
public interface IHobbesPIRComponentCompiler {

	public ICode generateEpilog(ICode code);

	public ICode generateCode(ICode code, Tree ast);

}
