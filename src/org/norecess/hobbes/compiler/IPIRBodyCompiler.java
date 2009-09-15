package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.ICode;

/**
 * @see PIRBodyCompiler
 * 
 */
public interface IPIRBodyCompiler {

	public ICode generate(ExpressionTIR tir);

}
