package org.norecess.hobbes.typechecker.operators;

import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class EqualityTypeChecker implements OperatorTypeChecker {

	public PrimitiveType check(HobbesType leftType, HobbesType rightType) {
		throw new OperatorTypeException();
	}

	public PrimitiveType check(IntegerType a, IntegerType b) {
		return BooleanType.TYPE;
	}

	public PrimitiveType check(FloatingPointType a, FloatingPointType b) {
		return BooleanType.TYPE;
	}

	public PrimitiveType check(BooleanType a, BooleanType b) {
		return BooleanType.TYPE;
	}

}
