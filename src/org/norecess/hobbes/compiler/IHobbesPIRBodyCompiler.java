package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.ICode;

/**
 * @see HobbesPIRBodyCompiler
 * 
 */
public interface IHobbesPIRBodyCompiler {

	public ICode generate(ExpressionTIR tir);

}
