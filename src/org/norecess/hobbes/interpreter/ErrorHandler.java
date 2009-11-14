package org.norecess.hobbes.interpreter;

import org.norecess.citkit.tir.expressions.IOperatorETIR;
import org.norecess.hobbes.typechecker.HobbesTypeException;

public class ErrorHandler implements IErrorHandler {

	public HobbesTypeException handleTypeError(IOperatorETIR expression) {
		return new HobbesTypeException(expression.getPosition(), //
				expression.getLeft().getType().toShortString() + " "
						+ expression.getOperator().getPunctuation() + " "
						+ expression.getRight().getType().toShortString());
	}

}
