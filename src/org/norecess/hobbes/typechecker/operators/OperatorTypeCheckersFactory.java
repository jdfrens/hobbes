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
		add(new ArithmeticTypeChecker(), //
		Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY,
				Operator.DIVIDE);
		add(new ModulusTypeChecker(), Operator.MODULUS);
		add(new ComparisonTypeChecker(), //
		Operator.LESS_THAN, Operator.LESS_EQUALS,
				Operator.GREATER_EQUALS, Operator.GREATER_THAN);
		add(new EqualityTypeChecker(), Operator.EQUALS, Operator.NOT_EQUALS);
		add(new LogicTypeChecker(), Operator.AND, Operator.OR);
		return myTypeCheckers;
	}

	private void add(OperatorTypeChecker typeChecker, Operator... operators) {
		for (Operator operator : operators) {
			add(operator, typeChecker);
		}
	}

	private void add(Operator operator, OperatorTypeChecker typeChecker) {
		myTypeCheckers.put(operator, polyd(typeChecker));
	}

	private OperatorTypeChecker polyd(OperatorTypeChecker typeChecker) {
		return PolyD.build(OperatorTypeChecker.class, typeChecker);
	}

}
