package org.norecess.hobbes.interpreter.operators;

import java.util.HashMap;
import java.util.Map;

import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;

import ovm.polyd.PolyD;

public class OperatorInterpretersFactory {

	private final Map<Operator, ApplyingOperator>	myOperatorInterpreters;

	public OperatorInterpretersFactory() {
		myOperatorInterpreters = new HashMap<Operator, ApplyingOperator>();
	}

	public Map<Operator, ApplyingOperator> createOperatorInterpreters() {
		add(Operator.ADD, new AdditionOperator());
		add(Operator.SUBTRACT, new SubtractionOperator());
		add(Operator.MULTIPLY, new MultiplicationOperator());
		add(Operator.DIVIDE, new DivisionOperator());
		add(Operator.MODULUS, new ModulusOperator());
		add(Operator.LESS_THAN, new LessThanOperator());
		add(Operator.LESS_EQUALS, new LessThanEqualsOperator());
		add(Operator.EQUALS, new EqualsOperator());
		add(Operator.NOT_EQUALS, new NotEqualsOperator());
		add(Operator.GREATER_EQUALS, new GreaterThanEqualsOperator());
		add(Operator.GREATER_THAN, new GreaterThanOperator());
		return myOperatorInterpreters;
	}

	private void add(Operator operator, ApplyingOperator appliable) {
		myOperatorInterpreters.put(operator, polyd(appliable));
	}

	private ApplyingOperator polyd(ApplyingOperator appliable) {
		return PolyD.build(ApplyingOperator.class, appliable);
	}

}
