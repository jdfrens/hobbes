package org.norecess.hobbes.typechecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.LValueTIR;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.LetETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.HobbesBoolean;

public class TypeCheckerTest {

	private IMocksControl	myMocksControl;

	private TypeChecker		myTypeChecker;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

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

	@Test
	public void shouldCheckVariables() {
		LValueTIR lvalue = myMocksControl.createMock(LValueTIR.class);

		myMocksControl.replay();
		assertEquals(IntegerType.INTEGER_TYPE, myTypeChecker
				.visitVariableETIR(new VariableETIR(lvalue)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCheckIf() {
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType type = myMocksControl.createMock(PrimitiveType.class);

		EasyMock.expect(consequence.accept(myTypeChecker)).andReturn(type);

		myMocksControl.replay();
		assertSame(type, myTypeChecker.visitIfETIR(new IfETIR(test,
				consequence, otherwise)));
		myMocksControl.verify();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldCheckLet() {
		PrimitiveType result = myMocksControl.createMock(PrimitiveType.class);
		List<DeclarationTIR> declarations = myMocksControl
				.createMock(List.class);
		ExpressionTIR body = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(body.accept(myTypeChecker)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myTypeChecker.visitLetETIR(new LetETIR(declarations,
				body)));
		myMocksControl.verify();
	}

	//
	// Helpers
	//
	private OperatorETIR comparisonExpression(Operator operator) {
		return new OperatorETIR(new IntegerETIR(55), operator, new IntegerETIR(
				3));
	}

	private OperatorETIR arithmeticExpression(Operator operator) {
		return comparisonExpression(operator);
	}

}
