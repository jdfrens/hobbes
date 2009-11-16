package org.norecess.hobbes.typechecker.operators;

import java.util.HashMap;
import java.util.Map;

import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;

import ovm.polyd.PolyD;

public class OperatorTypeCheckersFactory {

	private final HashMap<IOperator, OperatorTypeChecker>	myTypeCheckers;

	public OperatorTypeCheckersFactory() {
		myTypeCheckers = new HashMap<IOperator, OperatorTypeChecker>();
	}

	public Map<IOperator, OperatorTypeChecker> createOperatorTypeCheckers() {
		add(Operator.ADD, new AdditionTypeChecker());
		return myTypeCheckers;
	}

	private void add(Operator operator, OperatorTypeChecker typeChecker) {
		myTypeCheckers.put(operator, polyd(typeChecker));
	}

	private OperatorTypeChecker polyd(OperatorTypeChecker typeChecker) {
		return PolyD.build(OperatorTypeChecker.class, typeChecker);
	}

}
