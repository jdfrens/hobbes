package org.norecess.hobbes.interpreter.operators;

import java.util.HashMap;
import java.util.Map;

import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;

import ovm.polyd.PolyD;

public class AppliableFactory {

	private final Map<IOperator, Appliable>	myAppliables;

	public AppliableFactory() {
		myAppliables = new HashMap<IOperator, Appliable>();
	}

	public Map<IOperator, Appliable> createAppliables() {
		addArithmeticOperators();
		addComparisonOperators();
		addLogicOperators();
		return myAppliables;
	}

	private void addLogicOperators() {
		add(Operator.AND, new AndAppliable());
		add(Operator.OR, new OrAppliable());
	}

	private void addComparisonOperators() {
		add(Operator.LESS_THAN, new LessThanAppliable());
		add(Operator.LESS_EQUALS, new LessThanEqualsAppliable());
		add(Operator.EQUALS, new EqualsAppliable());
		add(Operator.NOT_EQUALS, new NotEqualsAppliable());
		add(Operator.GREATER_EQUALS, new GreaterThanEqualsAppliable());
		add(Operator.GREATER_THAN, new GreaterThanAppliable());
	}

	private void addArithmeticOperators() {
		add(Operator.ADD, new AdditionAppliable());
		add(Operator.SUBTRACT, new SubtractionAppliable());
		add(Operator.MULTIPLY, new MultiplicationAppliable());
		add(Operator.DIVIDE, new DivisionAppliable());
		add(Operator.MODULUS, new ModulusAppliable());
	}

	private void add(Operator operator, Appliable appliable) {
		myAppliables.put(operator, polyd(appliable));
	}

	private Appliable polyd(Appliable appliable) {
		return PolyD.build(Appliable.class, appliable);
	}

}
