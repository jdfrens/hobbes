package org.norecess.hobbes.drivers;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.IInterpreterSystem;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class InterpreterCLITest {

	private IMocksControl		myMocksControl;

	private IHobbesFrontEnd		myFrontend;
	private IInterpreterSystem	myInterpreterSystem;

	private InterpreterCLI		myInterpreterCLI;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myFrontend = myMocksControl.createMock(IHobbesFrontEnd.class);
		myInterpreterSystem = myMocksControl
				.createMock(IInterpreterSystem.class);

		myInterpreterCLI = new InterpreterCLI(myFrontend, myInterpreterSystem);
	}

	@Test
	public void shouldDoIt() throws IOException, RecognitionException {
		PrintStream out = new PrintStream(new ByteOutputStream());
		PrintStream err = new PrintStream(new ByteOutputStream());
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(myFrontend.process()).andReturn(tir);
		myInterpreterSystem.typeCheck(err, tir);
		myInterpreterSystem.evalAndPrint(out, tir);

		myMocksControl.replay();
		myInterpreterCLI.doit(out, err, new String[0]);
		myMocksControl.verify();
	}

}
