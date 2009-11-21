package org.norecess.hobbes.typechecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.IPosition;
import org.norecess.citkit.tir.Position;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.drivers.AbortTranslatorException;
import org.norecess.hobbes.drivers.StatusCodes;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class TopLevelTypeCheckerTest {

	private IMocksControl		myMocksControl;

	private ITypeChecker		myTypeChecker;

	private TopLevelTypeChecker	myTopLevelTypeChecker;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myTypeChecker = myMocksControl.createMock(ITypeChecker.class);

		myTopLevelTypeChecker = new TopLevelTypeChecker(myTypeChecker);
	}

	@Test
	public void shouldDeferTypeChecking() {
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType returnType = myMocksControl
				.createMock(PrimitiveType.class);

		EasyMock.expect(expression.accept(myTypeChecker)).andReturn(returnType);

		myMocksControl.replay();
		assertSame(returnType, myTopLevelTypeChecker.typeCheck(new PrintStream(
				err), expression));
		myMocksControl.verify();
	}

	@Test
	public void shouldTypeCheckWithError() throws RecognitionException,
			IOException {
		ByteOutputStream err = new ByteOutputStream();
		IPosition position = new Position(55);
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(expression.accept(myTypeChecker)).andThrow(
				new HobbesTypeException(position, "<type error message>"));

		myMocksControl.replay();
		try {
			myTopLevelTypeChecker.typeCheck(new PrintStream(err), expression);
			fail("should fail to type check");
		} catch (AbortTranslatorException e) {
			assertEquals(StatusCodes.STATUS_TYPE_ERROR, e.getStatus());
			assertEquals("Error on line 55: <type error message>\n", err
					.toString());
		}
		myMocksControl.verify();
	}

}
