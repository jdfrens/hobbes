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
import org.norecess.hobbes.drivers.system.IExternalSystem;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.translator.ITranslator;
import org.norecess.hobbes.typechecker.ITopLevelTypeChecker;

import com.google.inject.Guice;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class InterpreterCLITest {

	private IMocksControl			myMocksControl;

	private IExternalSystem			myExternalSystem;
	private IHobbesFrontEnd			myFrontend;
	private ITopLevelTypeChecker	myTypeChecker;
	private ITranslator				myTranslator;

	private InterpreterCLI			myInterpreterCLI;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myExternalSystem = myMocksControl.createMock(IExternalSystem.class);
		myFrontend = myMocksControl.createMock(IHobbesFrontEnd.class);
		myTypeChecker = myMocksControl.createMock(ITopLevelTypeChecker.class);
		myTranslator = myMocksControl.createMock(ITranslator.class);

		myInterpreterCLI = new InterpreterCLI(myExternalSystem, myFrontend,
				myTypeChecker, myTranslator);
	}

	@Test
	public void shouldGenerateInstanceOfCLI() {
		String[] args = new String[] { "foo", "bar" };
		Guice.createInjector(InterpreterCLI.createInjectorModules(args))
				.getInstance(InterpreterCLI.class);
	}

	@Test
	public void shouldDoIt() throws IOException, RecognitionException {
		PrintStream out = new PrintStream(new ByteOutputStream());
		PrintStream err = new PrintStream(new ByteOutputStream());
		HobbesType returnType = myMocksControl.createMock(HobbesType.class);
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(myFrontend.process()).andReturn(tir);
		EasyMock.expect(myTypeChecker.typeCheck(err, tir))
				.andReturn(returnType);
		myTranslator.evalAndPrint(out, returnType, tir);
		myExternalSystem.exit(StatusCodes.STATUS_OK);

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
		EasyMock.expect(myTypeChecker.typeCheck(err, tir)).andThrow(
				new AbortTranslatorException(exitStatus));
		myExternalSystem.exit(exitStatus);

		myMocksControl.replay();
		myInterpreterCLI.doit(new String[0], out, err);
		myMocksControl.verify();
	}
}
