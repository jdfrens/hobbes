package org.norecess.hobbes.drivers;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.ITranslatorSystem;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class InterpreterCLITest {

	private IMocksControl		myMocksControl;

	private IExternalSystem		myExternalSystem;
	private IHobbesFrontEnd		myFrontend;
	private ITranslatorSystem	myInterpreterSystem;

	private InterpreterCLI		myInterpreterCLI;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myExternalSystem = myMocksControl.createMock(IExternalSystem.class);
		myFrontend = myMocksControl.createMock(IHobbesFrontEnd.class);
		myInterpreterSystem = myMocksControl
				.createMock(ITranslatorSystem.class);

		myInterpreterCLI = new InterpreterCLI(myExternalSystem, myFrontend,
				myInterpreterSystem);
	}

	@Test
	public void shouldDoIt() throws IOException, RecognitionException {
		PrintStream out = new PrintStream(new ByteOutputStream());
		PrintStream err = new PrintStream(new ByteOutputStream());
		HobbesType returnType = myMocksControl.createMock(HobbesType.class);
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(myFrontend.process()).andReturn(tir);
		EasyMock.expect(myInterpreterSystem.typeCheck(err, tir)).andReturn(
				returnType);
		myInterpreterSystem.evalAndPrint(out, returnType, tir);
		myExternalSystem.exit(CLIStatusCodes.STATUS_OK);

		myMocksControl.replay();
		myInterpreterCLI.doit(new String[0], out, err);
		myMocksControl.verify();
	}

	@Test
	public void shouldDoItWithException() throws IOException,
			RecognitionException {
		PrintStream out = new PrintStream(new ByteOutputStream());
		PrintStream err = new PrintStream(new ByteOutputStream());
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);
		int exitStatus = 6665;

		EasyMock.expect(myFrontend.process()).andReturn(tir);
		EasyMock.expect(myInterpreterSystem.typeCheck(err, tir)).andThrow(
				new AbortInterpretationException(exitStatus));
		myExternalSystem.exit(exitStatus);

		myMocksControl.replay();
		myInterpreterCLI.doit(new String[0], out, err);
		myMocksControl.verify();
	}
}
