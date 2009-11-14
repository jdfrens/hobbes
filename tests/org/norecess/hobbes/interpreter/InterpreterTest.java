package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.ISymbol;
import org.norecess.citkit.Symbol;
import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.IPosition;
import org.norecess.citkit.tir.LValueTIR;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.BooleanETIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.LetETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.interpreter.operators.Appliable;

public class InterpreterTest {

	private IMocksControl				myMocksControl;

	private IInterpreter				myRecursion;
	private IIntegerETIR[]				myArgv;
	private IEnvironment<DatumTIR>		myEnvironment;
	private Map<IOperator, Appliable>	myAppliables;

	private Interpreter					myInterpreter;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myRecursion = myMocksControl.createMock(IInterpreter.class);
		myArgv = new IIntegerETIR[10];
		myEnvironment = myMocksControl.createMock(IEnvironment.class);
		myAppliables = myMocksControl.createMock(Map.class);

		myInterpreter = new Interpreter(myRecursion, myArgv, myEnvironment,
				myAppliables);
	}

	@Test
	public void shouldInterpret() {
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);
		DatumTIR result = myMocksControl.createMock(DatumTIR.class);

		EasyMock.expect(expression.accept(myInterpreter)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myInterpreter.interpret(expression));
		myMocksControl.verify();
	}

	@Test
	public void shouldInterpretWithTypeException() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(expression.accept(myInterpreter)).andThrow(
				new HobbesTypeException("bad types"));
		EasyMock.expect(expression.getPosition()).andReturn(position);

		myMocksControl.replay();
		try {
			myInterpreter.interpret(expression);
			fail("should throw type exception");
		} catch (HobbesTypeException e) {
			assertSame(position, e.getPosition());
		}
		myMocksControl.verify();
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
	public void shouldInterpretVariableETIR() {
		LValueTIR lvalue = myMocksControl.createMock(LValueTIR.class);
		DatumTIR result = myMocksControl.createMock(DatumTIR.class);

		EasyMock.expect(lvalue.accept(myInterpreter)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myInterpreter.visitVariableETIR(new VariableETIR(
				lvalue)));
		myMocksControl.verify();
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
		assertSame(result, myInterpreter.visitOperatorETIR(new OperatorETIR(
				left, operator, right)));
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

	@SuppressWarnings("unchecked")
	@Test
	public void shouldInterpretLetExpression() {
		List<DeclarationTIR> declarations = myMocksControl
				.createMock(List.class);
		ExpressionTIR body = myMocksControl.createMock(ExpressionTIR.class);
		IDeclarationBinder binder = myMocksControl
				.createMock(IDeclarationBinder.class);
		IEnvironment<DatumTIR> newEnvironment = myMocksControl
				.createMock(IEnvironment.class);
		DatumTIR result = myMocksControl.createMock(DatumTIR.class);

		EasyMock.expect(myRecursion.createBinder()).andReturn(binder);
		EasyMock.expect(binder.bind(declarations)).andReturn(newEnvironment);
		EasyMock.expect(myRecursion.interpret(newEnvironment, body)).andReturn(
				result);

		myMocksControl.replay();
		assertSame(result, myInterpreter.visitLetETIR(new LetETIR(declarations,
				body)));
		myMocksControl.verify();
	}

	@Test
	public void shouldInterpretSimpleLValue() {
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		DatumTIR result = myMocksControl.createMock(DatumTIR.class);

		EasyMock.expect(myEnvironment.get(symbol)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myInterpreter.visitSimpleLValue(new SimpleLValueTIR(
				symbol)));
		myMocksControl.verify();
	}

	@Test
	public void shouldInterpretSubscriptLValue() {
		myArgv[8] = new IntegerETIR(7);
		assertEquals(new IntegerETIR(7), myInterpreter
				.visitSubscriptLValue(createArgvTree(8)));
		myArgv[3] = new IntegerETIR(55);
		assertEquals(new IntegerETIR(55), myInterpreter
				.visitSubscriptLValue(createArgvTree(3)));
	}

	//
	// Helpers
	//
	private SubscriptLValueTIR createArgvTree(int i) {
		return new SubscriptLValueTIR(new SimpleLValueTIR(Symbol
				.createSymbol("ARGV")), new IntegerETIR(i));
	}

}
