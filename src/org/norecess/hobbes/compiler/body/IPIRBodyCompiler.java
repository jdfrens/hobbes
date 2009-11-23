package org.norecess.hobbes.compiler.body;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.backend.ICode;

/**
 * @see PIRBodyCompiler
 * 
 */
public interface IPIRBodyCompiler {

	public ICode generate(HobbesType returnType, ExpressionTIR tir);

	public ICode generatePrint(HobbesType returnType, ExpressionTIR tir);

}
