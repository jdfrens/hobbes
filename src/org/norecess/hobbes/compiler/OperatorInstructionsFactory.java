package org.norecess.hobbes.compiler;

import java.util.HashMap;
import java.util.Map;

import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;

public class OperatorInstructionsFactory {

	private final Map<Operator, OperatorInstruction>	myOperatorInstructions;

	public OperatorInstructionsFactory() {
		myOperatorInstructions = new HashMap<Operator, OperatorInstruction>();
	}

	public Map<Operator, OperatorInstruction> create() {
		addArithmeticOperators();
		addComparisonOperators();
		return myOperatorInstructions;
	}

	private void addComparisonOperators() {
		myOperatorInstructions.put(Operator.LESS_THAN, new ComparisonOperator(
				"<"));
		myOperatorInstructions.put(Operator.LESS_EQUALS,
				new ComparisonOperator("<="));
		myOperatorInstructions.put(Operator.EQUALS,
				new ComparisonOperator("=="));
		myOperatorInstructions.put(Operator.NOT_EQUALS, new ComparisonOperator(
				"!="));
		myOperatorInstructions.put(Operator.GREATER_EQUALS,
				new ComparisonOperator(">="));
		myOperatorInstructions.put(Operator.GREATER_THAN,
				new ComparisonOperator(">"));
	}

	private void addArithmeticOperators() {
		myOperatorInstructions.put(Operator.ADD, new ArithmeticOperator("+"));
		myOperatorInstructions.put(Operator.SUBTRACT, new ArithmeticOperator(
				"-"));
		myOperatorInstructions.put(Operator.MULTIPLY, new ArithmeticOperator(
				"*"));
		myOperatorInstructions
				.put(Operator.DIVIDE, new ArithmeticOperator("/"));
		myOperatorInstructions.put(Operator.MODULUS,
				new ArithmeticOperator("%"));
	}

}
