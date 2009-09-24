package org.norecess.hobbes.typechecker;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.hobbes.HobbesBoolean;

public class TypeCheckerTest {

	private TypeChecker	myTypeChecker;

	@Before
	public void setUp() {
		myTypeChecker = new TypeChecker();
	}

	@Test
	public void shouldCheckIntegers() {
		assertEquals(IntegerType.INTEGER_TYPE, myTypeChecker
				.visitIntegerETIR(new IntegerETIR(3)));
		assertEquals(IntegerType.INTEGER_TYPE, myTypeChecker
				.visitIntegerETIR(new IntegerETIR(-234)));
	}

	@Test
	public void shouldCheckBooleans() {
		assertEquals(BooleanType.BOOLEAN_TYPE, myTypeChecker
				.visitBooleanETIR(HobbesBoolean.TRUE));
		assertEquals(BooleanType.BOOLEAN_TYPE, myTypeChecker
				.visitBooleanETIR(HobbesBoolean.FALSE));
	}

	@Test
	public void shouldCheckArithmeticOperators() {
		assertEquals(IntegerType.INTEGER_TYPE, myTypeChecker
				.visitOperatorETIR(arithmeticExpression(Operator.ADD)));
		assertEquals(IntegerType.INTEGER_TYPE, myTypeChecker
				.visitOperatorETIR(arithmeticExpression(Operator.SUBTRACT)));
		assertEquals(IntegerType.INTEGER_TYPE, myTypeChecker
				.visitOperatorETIR(arithmeticExpression(Operator.MULTIPLY)));
		assertEquals(IntegerType.INTEGER_TYPE, myTypeChecker
				.visitOperatorETIR(arithmeticExpression(Operator.DIVIDE)));
		assertEquals(IntegerType.INTEGER_TYPE, myTypeChecker
				.visitOperatorETIR(arithmeticExpression(Operator.MODULUS)));
	}

	@Test
	public void shouldCheckComparisonOperators() {
		assertEquals(BooleanType.BOOLEAN_TYPE, myTypeChecker
				.visitOperatorETIR(comparisonExpression(Operator.LESS_THAN)));
		assertEquals(BooleanType.BOOLEAN_TYPE, myTypeChecker
				.visitOperatorETIR(comparisonExpression(Operator.LESS_EQUALS)));
		assertEquals(BooleanType.BOOLEAN_TYPE, myTypeChecker
				.visitOperatorETIR(comparisonExpression(Operator.EQUALS)));
		assertEquals(BooleanType.BOOLEAN_TYPE, myTypeChecker
				.visitOperatorETIR(comparisonExpression(Operator.NOT_EQUALS)));
		assertEquals(
				BooleanType.BOOLEAN_TYPE,
				myTypeChecker
						.visitOperatorETIR(comparisonExpression(Operator.GREATER_EQUALS)));
		assertEquals(BooleanType.BOOLEAN_TYPE, myTypeChecker
				.visitOperatorETIR(comparisonExpression(Operator.GREATER_THAN)));
	}

	private OperatorETIR comparisonExpression(Operator operator) {
		return new OperatorETIR(new IntegerETIR(55), operator, new IntegerETIR(
				3));
	}

	private OperatorETIR arithmeticExpression(Operator operator) {
		return comparisonExpression(operator);
	}

}
