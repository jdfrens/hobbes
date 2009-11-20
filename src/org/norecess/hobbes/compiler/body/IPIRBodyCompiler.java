package org.norecess.hobbes.compiler.body;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.backend.ICode;

/**
 * @see PIRBodyCompiler
 * 
 */
public interface IPIRBodyCompiler {

	public ICode generate(ExpressionTIR tir);

	public ICode generatePrint(PrimitiveType returnType, ExpressionTIR tir);

	@Deprecated
	public PrimitiveType typeCheck(ExpressionTIR tir);

}
