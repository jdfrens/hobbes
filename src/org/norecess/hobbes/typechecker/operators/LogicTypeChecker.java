package org.norecess.hobbes.typechecker.operators;

import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class LogicTypeChecker implements OperatorTypeChecker {

	public PrimitiveType check(HobbesType leftType, HobbesType rightType) {
		throw new OperatorTypeException();
	}

	public PrimitiveType check(BooleanType leftType, BooleanType rightType) {
		return BooleanType.TYPE;
	}

}
