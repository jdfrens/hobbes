package org.norecess.hobbes.interpreter;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR;
import org.norecess.hobbes.typechecker.HobbesTypeException;

public class ErrorHandler implements IErrorHandler {

	public HobbesTypeException handleTypeError(IOperatorETIR expression,
			DatumTIR leftResult, DatumTIR rightResult) {
		return new HobbesTypeException(expression.getPosition(), //
				leftResult.getType().toShortString() + " "
						+ expression.getOperator().getPunctuation() + " "
						+ rightResult.getType().toShortString());
	}

}
