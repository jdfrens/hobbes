package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.IPosition;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.typechecker.HobbesTypeException;

public class ErrorHandlerTest {

	private ErrorHandler	myErrorHandler;
	private IMocksControl	myMocksControl;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myErrorHandler = new ErrorHandler();
	}

	@Test
	public void shouldHandleOperatorTypeError() {
		IPosition position = myMocksControl.createMock(IPosition.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		IOperator operator = myMocksControl.createMock(IOperator.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);

		expectToShortString(left, "typeA");
		EasyMock.expect(operator.getPunctuation()).andReturn("op");
		expectToShortString(right, "typeQ");

		myMocksControl.replay();
		assertHobbesTypeException(position, "typeA op typeQ", myErrorHandler
				.handleTypeError(new OperatorETIR(position, left, operator,
						right)));
		myMocksControl.verify();
	}

	private void assertHobbesTypeException(IPosition position, String message,
			HobbesTypeException actual) {
		assertSame(position, actual.getPosition());
		assertEquals(message, actual.getMessage());
	}

	private void expectToShortString(ExpressionTIR expression,
			String shortString) {
		HobbesType type = myMocksControl.createMock(HobbesType.class);
		EasyMock.expect(expression.getType()).andReturn(type);
		EasyMock.expect(type.toShortString()).andReturn(shortString);
	}
}