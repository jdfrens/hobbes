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
import org.norecess.citkit.tir.declarations.IVariableDTIR;
import org.norecess.citkit.tir.declarations.VariableDTIR;
import org.norecess.citkit.tir.expressions.FloatingPointETIR;
import org.norecess.citkit.tir.expressions.IIfETIR;
import org.norecess.citkit.tir.expressions.ILetETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.expressions.IVariableETIR;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.LetETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.factories.IExpressionTIRFactory;
import org.norecess.citkit.tir.lvalues.ISimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.ISubscriptLValueTIR;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.interpreter.IErrorHandler;
import org.norecess.hobbes.support.HobbesEasyMock;
import org.norecess.hobbes.support.IHobbesMocksControl;
import org.norecess.hobbes.typechecker.operators.OperatorTypeChecker;

public class TypeCheckerTest {

	private IHobbesMocksControl					myMocksControl;

	private IEnvironment<HobbesType>			myEnvironment;
	private IExpressionTIRFactory				myExpressionFactory;
	private IErrorHandler						myErrorHandler;
	private Map<IOperator, OperatorTypeChecker>	myOperatorTypeCheckers;
	private ITypeChecker						myRecurser;

	private TypeChecker							myTypeChecker;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMocksControl = HobbesEasyMock.createControl();

		myEnvironment = myMocksControl.createMock(IEnvironment.class);
		myExpressionFactory = myMocksControl
				.createMock(IExpressionTIRFactory.class);
		myErrorHandler = myMocksControl.createMock(IErrorHandler.class);
		myOperatorTypeCheckers = myMocksControl.createMock(Map.class);
		myRecurser = myMocksControl.createMock(ITypeChecker.class);

		myTypeChecker = new TypeChecker(myEnvironment, myExpressionFactory,
				myErrorHandler, myOperatorTypeCheckers, myRecurser);
	}

	@Test
	public void shouldCheckIntegers() {
		assertEqualsType(new IntegerETIR(3), IntegerType.TYPE,
				myTypeChecker.visitIntegerETIR(new IntegerETIR(3)));
		assertEqualsType(new IntegerETIR(-234), IntegerType.TYPE,
				myTypeChecker.visitIntegerETIR(new IntegerETIR(-234)));
	}

	@Test
	public void shouldCheckFloats() {
		assertEqualsType(new FloatingPointETIR(5.0), FloatingPointType.TYPE,
				myTypeChecker
						.visitFloatingPointETIR(new FloatingPointETIR(5.0)));
		assertEqualsType(new FloatingPointETIR(-25.666),
				FloatingPointType.TYPE,
				myTypeChecker.visitFloatingPointETIR(new FloatingPointETIR(
						-25.666)));
	}

	@Test
	public void shouldCheckBooleans() {
		assertEqualsType(HobbesBoolean.TRUE, BooleanType.TYPE,
				myTypeChecker.visitBooleanETIR(HobbesBoolean.TRUE));
		assertEqualsType(HobbesBoolean.FALSE, BooleanType.TYPE,
				myTypeChecker.visitBooleanETIR(HobbesBoolean.FALSE));
	}

	@Test
	public void shouldCheckArithmeticOperator() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		IOperator op = myMocksControl.createMock(IOperator.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);

		ExpressionTIR typedLeft = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR typedRight = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType leftType = myMocksControl.createMock(PrimitiveType.class);
		PrimitiveType rightType = myMocksControl
				.createMock(PrimitiveType.class);
		OperatorTypeChecker operatorTypeChecker = myMocksControl
				.createMock(OperatorTypeChecker.class);
		PrimitiveType type = myMocksControl.createMock(PrimitiveType.class);
		IOperatorETIR result = myMocksControl.createMock(IOperatorETIR.class);

		expectRecurse(left, typedLeft, leftType);
		expectRecurse(right, typedRight, rightType);
		EasyMock.expect(myOperatorTypeCheckers.get(op)).andReturn(
				operatorTypeChecker);
		EasyMock.expect(operatorTypeChecker.check(leftType, rightType))
				.andReturn(type);
		EasyMock.expect(
				myExpressionFactory.buildOperatorETIR(position, type,
						typedLeft, op, typedRight)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myTypeChecker.visitOperatorETIR(new OperatorETIR(
				position, left, op, right)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCheckArithmeticOperatorWithTypeError() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		IOperator op = myMocksControl.createMock(IOperator.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);
		OperatorETIR expression = new OperatorETIR(position, left, op, right);

		ExpressionTIR typedLeft = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR typedRight = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType leftType = myMocksControl.createMock(PrimitiveType.class);
		PrimitiveType rightType = myMocksControl
				.createMock(PrimitiveType.class);
		OperatorTypeChecker operatorTypeChecker = myMocksControl
				.createMock(OperatorTypeChecker.class);
		HobbesTypeException expected = new HobbesTypeException(position,
				"something");

		expectRecurse(left, typedLeft, leftType);
		expectRecurse(right, typedRight, rightType);
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
		IPosition position = myMocksControl.createMock(IPosition.class);
		LValueTIR lvalue = myMocksControl.createMock(LValueTIR.class);
		LValueTIR typedLValue = myMocksControl.createMock(LValueTIR.class);
		IVariableETIR result = myMocksControl.createMock(IVariableETIR.class);

		EasyMock.expect(lvalue.accept(myTypeChecker)).andReturn(typedLValue);
		EasyMock.expect(
				myExpressionFactory.buildVariableETIR(position, typedLValue))
				.andReturn(result);

		myMocksControl.replay();
		assertSame(result, myTypeChecker.visitVariableETIR(new VariableETIR(
				position, lvalue)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCheckIf() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR typedTest = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR typedConsequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR typedOtherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType returnType = myMocksControl
				.createMock(PrimitiveType.class);
		IIfETIR result = myMocksControl.createMock(IIfETIR.class);

		expectRecurse(test, typedTest, BooleanType.TYPE);
		expectRecurse(consequence, typedConsequence, returnType);
		expectRecurse(otherwise, typedOtherwise, returnType);
		EasyMock.expect(
				myExpressionFactory.buildIfETIR(position, typedTest,
						typedConsequence, typedOtherwise)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myTypeChecker.visitIfETIR(new IfETIR(position, test,
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
		ExpressionTIR typedTest = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType testType = myMocksControl.createMock(PrimitiveType.class);

		expectRecurse(test, typedTest, testType);
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
		ExpressionTIR typedTest = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR typedConsequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR typedOtherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType consequenceType = myMocksControl
				.createMock(PrimitiveType.class);
		PrimitiveType otherwiseType = myMocksControl
				.createMock(PrimitiveType.class);

		expectRecurse(test, typedTest, BooleanType.TYPE);
		expectRecurse(consequence, typedConsequence, consequenceType);
		expectRecurse(otherwise, typedOtherwise, otherwiseType);
		EasyMock.expect(consequenceType.toShortString()).andReturn("ASD");
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
		IPosition position = myMocksControl.createMock(IPosition.class);
		List<DeclarationTIR> declarations = myMocksControl
				.createMock(List.class);
		ExpressionTIR body = myMocksControl.createMock(ExpressionTIR.class);
		List<DeclarationTIR> typedDeclarations = myMocksControl
				.createMock(List.class);
		ExpressionTIR typedBody = myMocksControl
				.createMock(ExpressionTIR.class);
		IEnvironment<HobbesType> newEnvironment = myMocksControl
				.createMock(IEnvironment.class);
		ILetETIR result = myMocksControl.createMock(ILetETIR.class);

		EasyMock.expect(myRecurser.recurse(declarations)).andReturn(
				typedDeclarations);
		EasyMock.expect(myRecurser.bind(typedDeclarations)).andReturn(
				newEnvironment);
		EasyMock.expect(myRecurser.recurse(newEnvironment, body)).andReturn(
				typedBody);
		EasyMock.expect(
				myExpressionFactory.buildLetETIR(position, typedDeclarations,
						typedBody)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myTypeChecker.visitLetETIR(new LetETIR(position,
				declarations, body)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCheckEmptyListOfDeclarations() {
		List<DeclarationTIR> declarations = new ArrayList<DeclarationTIR>();
		List<DeclarationTIR> result = new ArrayList<DeclarationTIR>();

		myMocksControl.replay();
		assertEquals(result, myTypeChecker.recurse(declarations));
		myMocksControl.verify();
	}

	@Test
	public void shouldCheckListOfDeclarations() {
		List<DeclarationTIR> declarations = Arrays.asList(
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class));
		List<DeclarationTIR> result = Arrays.asList(
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class));

		EasyMock.expect(myRecurser.recurse(declarations.get(0))).andReturn(
				result.get(0));
		EasyMock.expect(myRecurser.recurse(declarations.get(1))).andReturn(
				result.get(1));
		EasyMock.expect(myRecurser.recurse(declarations.get(2))).andReturn(
				result.get(2));

		myMocksControl.replay();
		assertEquals(result, myTypeChecker.recurse(declarations));
		myMocksControl.verify();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBindEmptyListOfDeclarations() {
		IEnvironment<HobbesType> newEnvironment = myMocksControl
				.createMock(IEnvironment.class);
		ITypeBinder binder = myMocksControl.createMock(ITypeBinder.class);

		EasyMock.expect(myEnvironment.create()).andReturn(newEnvironment);
		EasyMock.expect(myRecurser.createBinder(newEnvironment)).andReturn(
				binder);

		myMocksControl.replay();
		assertSame(newEnvironment,
				myTypeChecker.bind(new ArrayList<DeclarationTIR>()));
		myMocksControl.verify();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBindManyDeclarations() {
		IEnvironment<HobbesType> newEnvironment = myMocksControl
				.createMock(IEnvironment.class);
		ITypeBinder binder = myMocksControl.createMock(ITypeBinder.class);
		DeclarationTIR[] declarations = new DeclarationTIR[] {
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class) };

		EasyMock.expect(myEnvironment.create()).andReturn(newEnvironment);
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
				myTypeChecker.bind(Arrays.asList(declarations)));
		myMocksControl.verify();
	}

	@Test
	public void shouldTypeCheckVariableDeclaration() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		ExpressionTIR initialization = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR typedInitialization = myMocksControl
				.createMock(ExpressionTIR.class);
		IVariableDTIR result = myMocksControl.createMock(IVariableDTIR.class);

		EasyMock.expect(myRecurser.recurse(initialization)).andReturn(
				typedInitialization);
		EasyMock.expect(
				myExpressionFactory.buildVariableDTIR(position, symbol,
						typedInitialization)).andReturn(result);

		myMocksControl.replay();
		assertSame(result, myTypeChecker.visitVariableDTIR(new VariableDTIR(
				position, symbol, initialization)));
		myMocksControl.verify();
	}

	@Test
	public void shouldTypeCheckSimpleLValue() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		PrimitiveType type = myMocksControl.createMock(PrimitiveType.class);
		ISimpleLValueTIR result = myMocksControl
				.createMock(ISimpleLValueTIR.class);

		EasyMock.expect(myEnvironment.get(symbol)).andReturn(type);
		EasyMock.expect(
				myExpressionFactory
						.buildSimpleLValueTIR(position, type, symbol))
				.andReturn(result);

		myMocksControl.replay();
		assertSame(result, myTypeChecker.visitSimpleLValue(new SimpleLValueTIR(
				position, symbol)));
		myMocksControl.verify();
	}

	@Test
	public void shouldTypeCheckSubscriptLValue() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		LValueTIR variable = myMocksControl.createMock(LValueTIR.class);
		ExpressionTIR index = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR typedIndex = myMocksControl
				.createMock(ExpressionTIR.class);
		ISubscriptLValueTIR result = myMocksControl
				.createMock(ISubscriptLValueTIR.class);

		EasyMock.expect(myRecurser.recurse(index)).andReturn(typedIndex);
		EasyMock.expect(typedIndex.getType()).andReturn(IntegerType.TYPE);
		EasyMock.expect(
				myExpressionFactory.buildSubscriptLValueTIR(position,
						IntegerType.TYPE, variable, typedIndex)).andReturn(
				result);

		myMocksControl.replay();
		assertSame(result,
				myTypeChecker.visitSubscriptLValue(new SubscriptLValueTIR(
						position, variable, index)));
		myMocksControl.verify();
	}

	@Test
	public void shouldFailWhenSubscriptIsNotInt() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		LValueTIR variable = myMocksControl.createMock(LValueTIR.class);
		ExpressionTIR index = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR typedIndex = myMocksControl
				.createMock(ExpressionTIR.class);
		HobbesType notIntType = myMocksControl.createMock(HobbesType.class);

		EasyMock.expect(myRecurser.recurse(index)).andReturn(typedIndex);
		EasyMock.expect(typedIndex.getType()).andReturn(notIntType);

		myMocksControl.replay();
		try {
			myTypeChecker.visitSubscriptLValue(new SubscriptLValueTIR(position,
					variable, index));
			fail("should not accept non-int array index");
		} catch (HobbesTypeException e) {
			assertEquals("array index must be an int.", e.getMessage());
			myMocksControl.verify();
		}
	}

	private void assertEqualsType(ExpressionTIR expectedExpression,
			HobbesType expectedType, ExpressionTIR typedResult) {
		assertEquals(expectedType, typedResult.getType());
		assertEquals(expectedExpression, typedResult);
	}

	private void expectRecurse(ExpressionTIR expression,
			ExpressionTIR typedExpression, HobbesType type) {
		EasyMock.expect(myRecurser.recurse(expression)).andReturn(
				typedExpression);
		EasyMock.expect(typedExpression.getType()).andReturn(type).anyTimes();
	}

}
