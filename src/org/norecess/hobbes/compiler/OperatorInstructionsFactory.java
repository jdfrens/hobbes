package org.norecess.hobbes.compiler;

import java.util.HashMap;
import java.util.Map;

import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class OperatorInstructionsFactory {

	private final Map<Operator, OperatorInstruction>	myOperatorInstructions;
	private final IResourceAllocator					myResourceAllocator;

	public OperatorInstructionsFactory(IResourceAllocator resourceAllocator) {
		myOperatorInstructions = new HashMap<Operator, OperatorInstruction>();
		myResourceAllocator = resourceAllocator;
	}

	public Map<Operator, OperatorInstruction> create() {
		addArithmeticOperators();
		addComparisonOperators();
		return myOperatorInstructions;
	}

	private void addComparisonOperators() {
		myOperatorInstructions.put(Operator.LESS_THAN, new ComparisonOperator(
				myResourceAllocator, "<"));
		myOperatorInstructions.put(Operator.LESS_EQUALS,
				new ComparisonOperator(myResourceAllocator, "<="));
		myOperatorInstructions.put(Operator.EQUALS, new ComparisonOperator(
				myResourceAllocator, "=="));
		myOperatorInstructions.put(Operator.NOT_EQUALS, new ComparisonOperator(
				myResourceAllocator, "!="));
		myOperatorInstructions.put(Operator.GREATER_EQUALS,
				new ComparisonOperator(myResourceAllocator, ">="));
		myOperatorInstructions.put(Operator.GREATER_THAN,
				new ComparisonOperator(myResourceAllocator, ">"));
	}

	private void addArithmeticOperators() {
		myOperatorInstructions.put(Operator.ADD, new ArithmeticOperator(
				myResourceAllocator, "+"));
		myOperatorInstructions.put(Operator.SUBTRACT, new ArithmeticOperator(
				myResourceAllocator, "-"));
		myOperatorInstructions.put(Operator.MULTIPLY, new ArithmeticOperator(
				myResourceAllocator, "*"));
		myOperatorInstructions.put(Operator.DIVIDE, new ArithmeticOperator(
				myResourceAllocator, "/"));
		myOperatorInstructions.put(Operator.MODULUS, new ArithmeticOperator(
				myResourceAllocator, "%"));
	}

}
