package org.norecess.hobbes.interpreter;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.HobbesTypeException;

public interface IErrorHandler {

	@Deprecated
	HobbesTypeException handleTypeError(IOperatorETIR expression,
			DatumTIR leftResult, DatumTIR rightResult);

	HobbesTypeException handleTypeError(IOperatorETIR expression,
			PrimitiveType leftType, PrimitiveType rightType);

}
