package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;

/**
 * @see HobbesPIRBodyCompiler
 * 
 */
public interface IHobbesPIRBodyCompiler {

	public ICode generate(ExpressionTIR tir);

}
