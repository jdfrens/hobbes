package org.norecess.hobbes.typechecker.operators;

import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class ModulusTypeChecker implements OperatorTypeChecker {

	public PrimitiveType check(HobbesType leftType, HobbesType rightType) {
		throw new OperatorTypeException();
	}

	public PrimitiveType check(IntegerType left, IntegerType right) {
		return IntegerType.TYPE;
	}

}
