package org.norecess.hobbes.typechecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.ISymbol;
import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.IPosition;
import org.norecess.citkit.tir.LValueTIR;
import org.norecess.citkit.tir.expressions.FloatingPointETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.LetETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.interpreter.IErrorHandler;
import org.norecess.hobbes.support.HobbesEasyMock;
import org.norecess.hobbes.support.IHobbesMocksControl;
import org.norecess.hobbes.typechecker.operators.OperatorTypeChecker;

public class TypeCheckerTest {

	private IHobbesMocksControl					myMocksControl;

	private IEnvironment<PrimitiveType>			myEnvironment;
	private IErrorHandler						myErrorHandler;
	private Map<IOperator, OperatorTypeChecker>	myOperatorTypeCheckers;
	private ITypeChecker						myRecurser;

	private TypeChecker							myTypeChecker;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMocksControl = HobbesEasyMock.createControl();

		myEnvironment = myMocksControl.createMock(IEnvironment.class);
		myErrorHandler = myMocksControl.createMock(IErrorHandler.class);
		myOperatorTypeCheckers = myMocksControl.createMock(Map.class);
		myRecurser = myMocksControl.createMock(ITypeChecker.class);

		myTypeChecker = new TypeChecker(myEnvironment, myErrorHandler,
				myOperatorTypeCheckers, myRecurser);
	}

	@Test
	public void shouldCheckIntegers() {
		assertEquals(IntegerType.INTEGER_TYPE,
				myTypeChecker.visitIntegerETIR(new IntegerETIR(3)));
		assertEquals(IntegerType.INTEGER_TYPE,
				myTypeChecker.visitIntegerETIR(new IntegerETIR(-234)));
	}

	@Test
	public void shouldCheckFloats() {
		assertEquals(FloatingPointType.FLOATING_POINT_TYPE,
				myTypeChecker
						.visitFloatingPointETIR(new FloatingPointETIR(5.0)));
		assertEquals(FloatingPointType.FLOATING_POINT_TYPE,
				myTypeChecker
						.visitFloatingPointETIR(new FloatingPointETIR(5.0)));
	}

	@Test
	public void shouldCheckBooleans() {
		assertEquals(BooleanType.BOOLEAN_TYPE,
				myTypeChecker.visitBooleanETIR(HobbesBoolean.TRUE));
		assertEquals(BooleanType.BOOLEAN_TYPE,
				myTypeChecker.visitBooleanETIR(HobbesBoolean.FALSE));
	}

	@Test
	public void shouldCheckArithmeticOperator() {
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		IOperator op = myMocksControl.createMock(IOperator.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);
		PrimitiveType leftType = myMocksControl.createMock(PrimitiveType.class);
		PrimitiveType rightType = myMocksControl
				.createMock(PrimitiveType.class);
		OperatorTypeChecker operatorTypeChecker = myMocksControl
				.createMock(OperatorTypeChecker.class);
		PrimitiveType result = myMocksControl.createMock(PrimitiveType.class);

		EasyMock.expect(myRecurser.recurse(left)).andReturn(leftType);
		EasyMock.expect(myRecurser.recurse(right)).andReturn(rightType);
		EasyMock.expect(myOperatorTypeCheckers.get(op)).andReturn(
				operatorTypeChecker);
		EasyMock.expect(operatorTypeChecker.check(leftType, rightType))
				.andReturn(result);

		myMocksControl.replay();
		assertSame(result, myTypeChecker.visitOperatorETIR(new OperatorETIR(
				left, op, right)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCheckArithmeticOperatorWithTypeError() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		IOperator op = myMocksControl.createMock(IOperator.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);
		OperatorETIR expression = new OperatorETIR(position, left, op, right);
		PrimitiveType leftType = myMocksControl.createMock(PrimitiveType.class);
		PrimitiveType rightType = myMocksControl
				.createMock(PrimitiveType.class);
		OperatorTypeChecker operatorTypeChecker = myMocksControl
				.createMock(OperatorTypeChecker.class);
		HobbesTypeException expected = new HobbesTypeException(position,
				"something");

		EasyMock.expect(myRecurser.recurse(left)).andReturn(leftType);
		EasyMock.expect(myRecurser.recurse(right)).andReturn(rightType);
		EasyMock.expect(myOperatorTypeCheckers.get(op)).andReturn(
				operatorTypeChecker);
		EasyMock.expect(operatorTypeChecker.check(leftType, rightType))
				.andThrow(new OperatorTypeException());
		EasyMock.expect(
				myErrorHandler.handleTypeError(expression, leftType, rightType))
				.andReturn(expected);

		myMocksControl.replay();
		try {
			myTypeChecker.visitOperatorETIR(expression);
			fail("should throw type exception");
		} catch (HobbesTypeException actual) {
			assertSame(expected, actual);
		} finally {
			myMocksControl.verify();
		}
	}

	@Test
	public void shouldCheckVariables() {
		LValueTIR lvalue = myMocksControl.createMock(LValueTIR.class);
		PrimitiveType type = myMocksControl.createMock(PrimitiveType.class);

		EasyMock.expect(lvalue.accept(myTypeChecker)).andReturn(type);

		myMocksControl.replay();
		assertSame(type,
				myTypeChecker.visitVariableETIR(new VariableETIR(lvalue)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCheckIf() {
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType returnType = myMocksControl
				.createMock(PrimitiveType.class);

		EasyMock.expect(myRecurser.recurse(test)).andReturn(
				BooleanType.BOOLEAN_TYPE);
		EasyMock.expect(myRecurser.recurse(consequence)).andReturn(returnType);
		EasyMock.expect(myRecurser.recurse(otherwise)).andReturn(returnType);

		myMocksControl.replay();
		assertSame(returnType, myTypeChecker.visitIfETIR(new IfETIR(test,
				consequence, otherwise)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCheckIfWithErrorInTest() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		HobbesTypeException exception = new HobbesTypeException(position,
				"<message>");

		EasyMock.expect(myRecurser.recurse(test)).andThrow(exception);

		myMocksControl.replay();
		try {
			myTypeChecker.visitIfETIR(new IfETIR(position, test, consequence,
					otherwise));
			fail("should have throw a type-checking exception");
		} catch (HobbesTypeException actual) {
			assertSame(exception, actual);
			myMocksControl.verify();
		}
	}

	@Test
	public void shouldCheckIfWithNonBooleanTest() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType testType = myMocksControl.createMock(PrimitiveType.class);

		EasyMock.expect(myRecurser.recurse(test)).andReturn(testType);
		EasyMock.expect(testType.toShortString()).andReturn("OTHER");

		myMocksControl.replay();
		try {
			myTypeChecker.visitIfETIR(new IfETIR(position, test, consequence,
					otherwise));
			fail("should have throw a type-checking exception");
		} catch (HobbesTypeException actual) {
			assertEquals("if test must be bool, not OTHER", actual.getMessage());
			myMocksControl.verify();
		}
	}

	@Test
	public void shouldCheckIfWithMismatchingThenAndElse() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType consequenceType = myMocksControl
				.createMock(PrimitiveType.class);
		PrimitiveType otherwiseType = myMocksControl
				.createMock(PrimitiveType.class);

		EasyMock.expect(myRecurser.recurse(test)).andReturn(
				BooleanType.BOOLEAN_TYPE);
		EasyMock.expect(myRecurser.recurse(consequence)).andReturn(
				consequenceType);
		EasyMock.expect(consequenceType.toShortString()).andReturn("ASD");
		EasyMock.expect(myRecurser.recurse(otherwise)).andReturn(otherwiseType);
		EasyMock.expect(otherwiseType.toShortString()).andReturn("QWE");

		myMocksControl.replay();
		try {
			myTypeChecker.visitIfETIR(new IfETIR(position, test, consequence,
					otherwise));
			fail("should have throw a type-checking exception");
		} catch (HobbesTypeException actual) {
			assertEquals("then clause is ASD, else clause is QWE",
					actual.getMessage());
			myMocksControl.verify();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldCheckLet() {
		PrimitiveType result = myMocksControl.createMock(PrimitiveType.class);
		List<DeclarationTIR> declarations = myMocksControl
				.createMock(List.class);
		ExpressionTIR body = myMocksControl.createMock(ExpressionTIR.class);
		IEnvironment<PrimitiveType> newEnvironment = myMocksControl
				.createMock(IEnvironment.class);

		EasyMock.expect(myEnvironment.create()).andReturn(newEnvironment);
		EasyMock.expect(myRecurser.bind(newEnvironment, declarations))
				.andReturn(newEnvironment);
		EasyMock.expect(myRecurser.recurse(newEnvironment, body)).andReturn(
				result);

		myMocksControl.replay();
		assertSame(result,
				myTypeChecker.visitLetETIR(new LetETIR(declarations, body)));
		myMocksControl.verify();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBindAndCheckNoDeclarations() {
		IEnvironment<PrimitiveType> newEnvironment = myMocksControl
				.createMock(IEnvironment.class);
		ITypeBinder binder = myMocksControl.createMock(ITypeBinder.class);

		EasyMock.expect(myRecurser.createBinder(newEnvironment)).andReturn(
				binder);

		myMocksControl.replay();
		assertSame(newEnvironment, myTypeChecker.bind(newEnvironment,
				new ArrayList<DeclarationTIR>()));
		myMocksControl.verify();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBindAndCheckManyDeclarations() {
		IEnvironment<PrimitiveType> newEnvironment = myMocksControl
				.createMock(IEnvironment.class);
		ITypeBinder binder = myMocksControl.createMock(ITypeBinder.class);
		DeclarationTIR[] declarations = new DeclarationTIR[] {
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class) };

		EasyMock.expect(myRecurser.createBinder(newEnvironment)).andReturn(
				binder);
		EasyMock.expect(declarations[0].accept(binder)).andReturn(
				newEnvironment);
		EasyMock.expect(declarations[1].accept(binder)).andReturn(
				newEnvironment);
		EasyMock.expect(declarations[2].accept(binder)).andReturn(
				newEnvironment);

		myMocksControl.replay();
		assertSame(newEnvironment,
				myTypeChecker.bind(newEnvironment, Arrays.asList(declarations)));
		myMocksControl.verify();
	}

	@Test
	public void shouldTypeCheckSimpleLValue() {
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		PrimitiveType type = myMocksControl.createMock(PrimitiveType.class);

		EasyMock.expect(myEnvironment.get(symbol)).andReturn(type);

		myMocksControl.replay();
		assertSame(type,
				myTypeChecker.visitSimpleLValue(new SimpleLValueTIR(symbol)));
		myMocksControl.verify();
	}

	@Test
	public void shouldTypeCheckSubscriptLValue() {
		LValueTIR variable = myMocksControl.createMock(LValueTIR.class);
		ExpressionTIR index = myMocksControl.createMock(ExpressionTIR.class);

		myMocksControl.replay();
		assertSame(IntegerType.INTEGER_TYPE,
				myTypeChecker.visitSubscriptLValue(new SubscriptLValueTIR(
						variable, index)));
		myMocksControl.verify();
	}

}
