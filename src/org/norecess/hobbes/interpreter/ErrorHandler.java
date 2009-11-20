package org.norecess.hobbes.interpreter;

import org.norecess.citkit.tir.expressions.IOperatorETIR;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.HobbesTypeException;

public class ErrorHandler implements IErrorHandler {

	public HobbesTypeException handleTypeError(IOperatorETIR expression,
			PrimitiveType leftType, PrimitiveType rightType) {
		return new HobbesTypeException(expression.getPosition(), //
				leftType.toShortString() + " "
						+ expression.getOperator().getPunctuation() + " "
						+ rightType.toShortString() + " is not defined");
	}

}
