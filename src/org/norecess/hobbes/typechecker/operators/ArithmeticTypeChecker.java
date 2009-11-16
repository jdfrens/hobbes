package org.norecess.hobbes.typechecker.operators;

import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class ArithmeticTypeChecker implements OperatorTypeChecker {

	public PrimitiveType check(PrimitiveType leftType, PrimitiveType rightType) {
		throw new OperatorTypeException();
	}

}
