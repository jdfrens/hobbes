package org.norecess.hobbes.typechecker.operators;

import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class ComparisonTypeChecker implements OperatorTypeChecker {

	public PrimitiveType check(PrimitiveType leftType, PrimitiveType rightType) {
		throw new OperatorTypeException();
	}

	public PrimitiveType check(IntegerType left, IntegerType right) {
		return BooleanType.BOOLEAN_TYPE;
	}

}
