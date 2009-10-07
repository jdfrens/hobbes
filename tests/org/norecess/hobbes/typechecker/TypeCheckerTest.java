package org.norecess.hobbes.typechecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.ISymbol;
import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.LValueTIR;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.LetETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.support.HobbesEasyMock;
import org.norecess.hobbes.support.IHobbesMocksControl;

public class TypeCheckerTest {

	private IHobbesMocksControl			myMocksControl;

	private ITypeChecker				myRecurser;
	private IEnvironment<PrimitiveType>	myEnvironment;

	private TypeChecker					myTypeChecker;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMocksControl = HobbesEasyMock.createControl();

		myRecurser = myMocksControl.createMock(ITypeChecker.class);
		myEnvironment = myMocksControl.createMock(IEnvironment.class);

		myTypeChecker = new TypeChecker(myEnvironment, myRecurser);
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
		PrimitiveType type = myMocksControl.createMock(PrimitiveType.class);

		EasyMock.expect(lvalue.accept(myTypeChecker)).andReturn(type);

		myMocksControl.replay();
		assertSame(type, myTypeChecker.visitVariableETIR(new VariableETIR(
				lvalue)));
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
		IEnvironment<PrimitiveType> newEnvironment = myMocksControl
				.createMock(IEnvironment.class);

		EasyMock.expect(myEnvironment.create()).andReturn(newEnvironment);
		EasyMock.expect(myRecurser.bind(newEnvironment, declarations))
				.andReturn(newEnvironment);
		EasyMock.expect(myRecurser.recurse(newEnvironment, body)).andReturn(
				result);

		myMocksControl.replay();
		assertSame(result, myTypeChecker.visitLetETIR(new LetETIR(declarations,
				body)));
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
		assertSame(newEnvironment, myTypeChecker.bind(newEnvironment, Arrays
				.asList(declarations)));
		myMocksControl.verify();
	}

	@Test
	public void shouldTypeCheckSimpleLValue() {
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		PrimitiveType type = myMocksControl.createMock(PrimitiveType.class);

		EasyMock.expect(myEnvironment.get(symbol)).andReturn(type);

		myMocksControl.replay();
		assertSame(type, myTypeChecker.visitSimpleLValue(new SimpleLValueTIR(
				symbol)));
		myMocksControl.verify();
	}

	@Test
	public void shouldTypeCheckSubscriptLValue() {
		LValueTIR variable = myMocksControl.createMock(LValueTIR.class);
		ExpressionTIR index = myMocksControl.createMock(ExpressionTIR.class);

		myMocksControl.replay();
		assertSame(IntegerType.INTEGER_TYPE, myTypeChecker
				.visitSubscriptLValue(new SubscriptLValueTIR(variable, index)));
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
