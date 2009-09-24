package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.expressions.BooleanETIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.interpreter.operators.Appliable;

public class InterpreterTest {

	private IMocksControl				myMocksControl;

	private IIntegerETIR[]				myArgv;
	private Map<Operator, Appliable>	myAppliables;

	private Interpreter					myInterpreter;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myArgv = new IIntegerETIR[10];
		myAppliables = myMocksControl.createMock(Map.class);

		myInterpreter = new Interpreter(myArgv, myAppliables);
	}

	@Test
	public void shouldInterpretInteger() {
		assertEquals(new IntegerETIR(5), myInterpreter
				.interpret(new IntegerETIR(5)));
		assertEquals(new IntegerETIR(555), myInterpreter
				.interpret(new IntegerETIR(555)));
	}

	@Test
	public void shouldInterpretBooleans() {
		assertEquals(BooleanETIR.TRUE, myInterpreter
				.interpret(BooleanETIR.TRUE));
		assertEquals(BooleanETIR.FALSE, myInterpreter
				.interpret(BooleanETIR.FALSE));
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
		IOperator operator = myMocksControl.createMock(IOperator.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);
		Appliable appliable = myMocksControl.createMock(Appliable.class);
		IIntegerETIR leftResult = myMocksControl.createMock(IIntegerETIR.class);
		IIntegerETIR rightResult = myMocksControl
				.createMock(IIntegerETIR.class);
		IIntegerETIR result = myMocksControl.createMock(IIntegerETIR.class);

		EasyMock.expect(myAppliables.get(operator)).andReturn(appliable)
				.atLeastOnce();
		EasyMock.expect(left.accept(myInterpreter)).andReturn(leftResult);
		EasyMock.expect(right.accept(myInterpreter)).andReturn(rightResult);
		EasyMock.expect(appliable.apply(leftResult, rightResult)).andReturn(
				result);

		myMocksControl.replay();
		assertSame(result, myInterpreter.interpret(new OperatorETIR(left,
				operator, right)));
		myMocksControl.verify();
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
				HobbesBoolean.TRUE);
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
				HobbesBoolean.FALSE);
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
