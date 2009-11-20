package org.norecess.hobbes.interpreter;

import org.norecess.citkit.tir.expressions.IOperatorETIR;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.HobbesTypeException;

public interface IErrorHandler {

	HobbesTypeException handleTypeError(IOperatorETIR expression,
			PrimitiveType leftType, PrimitiveType rightType);

}
