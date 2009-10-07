package org.norecess.hobbes.compiler.body;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.ICode;

/**
 * @see PIRBodyCompiler
 * 
 */
public interface IPIRBodyCompiler {

	public ICode generate(ExpressionTIR tir);

	public ICode generatePrint(ExpressionTIR tir);

}
