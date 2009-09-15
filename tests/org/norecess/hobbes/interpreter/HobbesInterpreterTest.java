package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;

public class HobbesInterpreterTest {

	private IIntegerETIR[]		myArgv;

	private HobbesInterpreter	myInterpreter;

	@Before
	public void setUp() {
		myArgv = new IIntegerETIR[10];
		myInterpreter = new HobbesInterpreter(myArgv);
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

	private ExpressionTIR createArgvTree(int i) {
		return new VariableETIR(new SubscriptLValueTIR(new SimpleLValueTIR(
				"ARGV"), new IntegerETIR(i)));
	}

	@Test
	public void shouldInterpretOperatorExpression() {
		IMocksControl control = EasyMock.createControl();
		ExpressionTIR left = control.createMock(ExpressionTIR.class);
		ExpressionTIR right = control.createMock(ExpressionTIR.class);
		IIntegerETIR leftResult = control.createMock(IIntegerETIR.class);
		IIntegerETIR rightResult = control.createMock(IIntegerETIR.class);

		EasyMock.expect(left.accept(myInterpreter)).andReturn(leftResult);
		EasyMock.expect(right.accept(myInterpreter)).andReturn(rightResult);
		EasyMock.expect(leftResult.getValue()).andReturn(5);
		EasyMock.expect(rightResult.getValue()).andReturn(8);

		control.replay();
		assertEquals(new IntegerETIR(13), myInterpreter
				.interpret(new OperatorETIR(left, Operator.ADD, right)));
		control.verify();
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

}
