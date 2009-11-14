package org.norecess.hobbes.drivers;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.HobbesTypeException;
import org.norecess.hobbes.interpreter.IInterpreter;
import org.norecess.hobbes.output.IHobbesOutput;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class InterpreterCLITest {

	private IMocksControl	myMocksControl;

	private IHobbesOutput	myOutput;
	private IHobbesFrontEnd	myFrontend;
	private IInterpreter	myInterpreter;

	private InterpreterCLI	myInterpreterCLI;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myOutput = myMocksControl.createMock(IHobbesOutput.class);
		myFrontend = myMocksControl.createMock(IHobbesFrontEnd.class);
		myInterpreter = myMocksControl.createMock(IInterpreter.class);

		myInterpreterCLI = new InterpreterCLI(myOutput, myFrontend,
				myInterpreter);
	}

	@Test
	public void shouldSimplyInterpretAndPrint() throws IOException,
			RecognitionException {
		String[] args = new String[0];
		ByteOutputStream out = new ByteOutputStream();
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);
		DatumTIR datum = myMocksControl.createMock(DatumTIR.class);

		EasyMock.expect(myFrontend.process()).andReturn(tir);
		EasyMock.expect(myInterpreter.interpret(tir)).andReturn(datum);
		EasyMock.expect(myOutput.asHobbesOutput(datum)).andReturn("the output");

		myMocksControl.replay();
		assertEquals(InterpreterCLI.STATUS_OK, myInterpreterCLI.doit(
				new PrintStream(out), null, args));
		assertEquals("the output\n", out.toString());
		myMocksControl.verify();
	}

	@Test
	public void shouldHandleTypeErrors() throws RecognitionException,
			IOException {
		String[] args = new String[0];
		ByteOutputStream err = new ByteOutputStream();
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(myFrontend.process()).andReturn(tir);
		EasyMock.expect(myInterpreter.interpret(tir)).andThrow(
				new HobbesTypeException("<this operation>"));

		myMocksControl.replay();
		assertEquals(InterpreterCLI.STATUS_TYPE_ERROR, myInterpreterCLI.doit(
				null, new PrintStream(err), args));
		assertEquals("Error on line 1: <this operation> is not defined\n", err
				.toString());
		myMocksControl.verify();
	}
}
