package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.IPosition;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.HobbesTypeException;

public class ErrorHandlerTest {

	private IMocksControl	myMocksControl;

	private ErrorHandler	myErrorHandler;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myErrorHandler = new ErrorHandler();
	}

	@Test
	public void shouldHandleOperatorTypeErrorDeprecated() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		IOperator operator = myMocksControl.createMock(IOperator.class);
		IOperatorETIR expression = myMocksControl
				.createMock(IOperatorETIR.class);
		DatumTIR leftResult = myMocksControl.createMock(DatumTIR.class);
		DatumTIR rightResult = myMocksControl.createMock(DatumTIR.class);

		EasyMock.expect(expression.getPosition()).andReturn(position);
		expectToShortString(leftResult, "typeA");
		EasyMock.expect(expression.getOperator()).andReturn(operator);
		EasyMock.expect(operator.getPunctuation()).andReturn("op");
		expectToShortString(rightResult, "typeQ");

		myMocksControl.replay();
		assertHobbesTypeException(position, "typeA op typeQ", myErrorHandler
				.handleTypeError(expression, leftResult, rightResult));
		myMocksControl.verify();
	}

	@Test
	public void shouldHandleOperatorTypeError() {
		IOperatorETIR expression = myMocksControl
				.createMock(IOperatorETIR.class);
		PrimitiveType leftType = myMocksControl.createMock(PrimitiveType.class);
		PrimitiveType rightType = myMocksControl
				.createMock(PrimitiveType.class);
		IPosition position = myMocksControl.createMock(IPosition.class);
		IOperator operator = myMocksControl.createMock(IOperator.class);

		EasyMock.expect(expression.getPosition()).andReturn(position);
		EasyMock.expect(expression.getOperator()).andReturn(operator);
		EasyMock.expect(leftType.toShortString()).andReturn("foobar");
		EasyMock.expect(rightType.toShortString()).andReturn("barfoo");
		EasyMock.expect(operator.getPunctuation()).andReturn("op");

		myMocksControl.replay();
		assertHobbesTypeException(position, "foobar op barfoo is not defined",
				myErrorHandler.handleTypeError(expression, leftType, rightType));
		myMocksControl.verify();
	}

	private void assertHobbesTypeException(IPosition position, String message,
			HobbesTypeException actual) {
		assertSame(position, actual.getPosition());
		assertEquals(message, actual.getMessage());
	}

	private void expectToShortString(DatumTIR expression, String shortString) {
		HobbesType type = myMocksControl.createMock(HobbesType.class);
		EasyMock.expect(expression.getType()).andReturn(type);
		EasyMock.expect(type.toShortString()).andReturn(shortString);
	}
}
