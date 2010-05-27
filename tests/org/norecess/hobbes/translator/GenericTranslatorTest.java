package org.norecess.hobbes.translator;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.drivers.AbortTranslatorException;
import org.norecess.hobbes.drivers.InterpreterCLI;
import org.norecess.hobbes.drivers.StatusCodes;
import org.norecess.hobbes.drivers.system.IExternalSystem;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.typechecker.ITopLevelTypeChecker;

import com.google.inject.Guice;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class GenericTranslatorTest {

	private IMocksControl			myMocksControl;

	private IExternalSystem			myExternalSystem;
	private IHobbesFrontEnd			myFrontend;
	private ITopLevelTypeChecker	myTypeChecker;
	private ITranslator				myTranslator;

	private GenericTranslator		myInterpreterCLI;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myExternalSystem = myMocksControl.createMock(IExternalSystem.class);
		myFrontend = myMocksControl.createMock(IHobbesFrontEnd.class);
		myTypeChecker = myMocksControl.createMock(ITopLevelTypeChecker.class);
		myTranslator = myMocksControl.createMock(ITranslator.class);

		myInterpreterCLI = new GenericTranslator(myExternalSystem, myFrontend,
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
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR typedTir = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(myFrontend.process()).andReturn(tir);
		EasyMock.expect(myTypeChecker.typeCheck(err, tir)).andReturn(typedTir);
		myTranslator.evalAndPrint(out, typedTir);
		myExternalSystem.exit(StatusCodes.STATUS_OK);

		myMocksControl.replay();
		myInterpreterCLI.translate(new String[0], out, err);
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
		myInterpreterCLI.translate(new String[0], out, err);
		myMocksControl.verify();
	}
}
