package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.hobbes.HobbesConstants;

public class InterpreterTest {

	private IMocksControl	myMocksControl;

	private IIntegerETIR[]	myArgv;

	private Interpreter		myInterpreter;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();
		myArgv = new IIntegerETIR[10];
		myInterpreter = new Interpreter(myArgv);
	}

	@Test
	public void shouldInterpretInteger() {
		assertEquals(new IntegerETIR(5), myInterpreter
				.interpret(new IntegerETIR(5)));
		assertEquals(new IntegerETIR(555), myInterpreter
				.interpret(new IntegerETIR(555)));
	}

	@Test
	public void shouldInterpretCommandLineArguments() {
		myArgv[8] = new IntegerETIR(7);
		assertEquals(new IntegerETIR(7), myInterpreter
				.interpret(createArgvTree(8)));
		myArgv[3] = new IntegerETIR(55);
		assertEquals(new IntegerETIR(55), myInterpreter
				.interpret(createArgvTree(3)));
	}

	@Test
	public void shouldInterpretOperatorExpression() {
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);
		IIntegerETIR leftResult = myMocksControl.createMock(IIntegerETIR.class);
		IIntegerETIR rightResult = myMocksControl
				.createMock(IIntegerETIR.class);

		EasyMock.expect(left.accept(myInterpreter)).andReturn(leftResult);
		EasyMock.expect(right.accept(myInterpreter)).andReturn(rightResult);
		EasyMock.expect(leftResult.getValue()).andReturn(5);
		EasyMock.expect(rightResult.getValue()).andReturn(8);

		myMocksControl.replay();
		assertEquals(new IntegerETIR(13), myInterpreter
				.interpret(new OperatorETIR(left, Operator.ADD, right)));
		myMocksControl.verify();
	}

	@Test
	public void shouldInterpretMultiplication() {
		assertEquals(new IntegerETIR(16), myInterpreter.apply(
				Operator.MULTIPLY, 2, 8));
		assertEquals(new IntegerETIR(-150), myInterpreter.apply(
				Operator.MULTIPLY, 15, -10));
	}

	@Test
	public void shouldInterpretSubtraction() {
		assertEquals(new IntegerETIR(-6), myInterpreter.apply(
				Operator.SUBTRACT, 2, 8));
		assertEquals(new IntegerETIR(5), myInterpreter.apply(Operator.SUBTRACT,
				15, 10));
	}

	@Test
	public void shouldInterpretDivision() {
		assertEquals(new IntegerETIR(4), myInterpreter.apply(Operator.DIVIDE,
				8, 2));
		assertEquals(new IntegerETIR(2), myInterpreter.apply(Operator.DIVIDE,
				15, 6));
	}

	@Test
	public void shouldInterpretModulus() {
		assertEquals(new IntegerETIR(0), myInterpreter.apply(Operator.MODULUS,
				8, 2));
		assertEquals(new IntegerETIR(3), myInterpreter.apply(Operator.MODULUS,
				15, 4));
	}

	@Test
	public void shouldInterpretIfTrueThen() {
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		IIntegerETIR result = myMocksControl.createMock(IIntegerETIR.class);

		EasyMock.expect(test.accept(myInterpreter)).andReturn(
				HobbesConstants.TRUE);
		EasyMock.expect(consequence.accept(myInterpreter)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myInterpreter.visitIfETIR(new IfETIR(test,
				consequence, otherwise)));
		myMocksControl.verify();
	}

	@Test
	public void shouldInterpretIfFalseElse() {
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		IIntegerETIR result = myMocksControl.createMock(IIntegerETIR.class);

		EasyMock.expect(test.accept(myInterpreter)).andReturn(
				HobbesConstants.FALSE);
		EasyMock.expect(otherwise.accept(myInterpreter)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myInterpreter.visitIfETIR(new IfETIR(test,
				consequence, otherwise)));
		myMocksControl.verify();
	}

	//
	// Helpers
	//
	private ExpressionTIR createArgvTree(int i) {
		return new VariableETIR(new SubscriptLValueTIR(new SimpleLValueTIR(
				"ARGV"), new IntegerETIR(i)));
	}

}
