package org.norecess.hobbes.typechecker.operators;

import org.norecess.citkit.types.PrimitiveType;

import ovm.polyd.policy.MultiDisp;
import ovm.polyd.tags.DispatchingPolicy;
import ovm.polyd.tags.PolyD;

@PolyD
@DispatchingPolicy(MultiDisp.class)
public interface OperatorTypeChecker {

	PrimitiveType check(PrimitiveType leftType, PrimitiveType rightType);

}
