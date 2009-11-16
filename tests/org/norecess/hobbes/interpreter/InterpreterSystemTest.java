package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.hobbes.drivers.AbortInterpretationException;
import org.norecess.hobbes.drivers.CLIStatusCodes;
import org.norecess.hobbes.output.IHobbesOutput;
import org.norecess.hobbes.typechecker.HobbesTypeException;
import org.norecess.hobbes.typechecker.ITypeChecker;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class InterpreterSystemTest {

	private IMocksControl		myMocksControl;

	private ITypeChecker		myTypeChecker;
	private IInterpreter		myInterpreter;
	private IHobbesOutput		myHobbesOutput;

	private InterpreterSystem	mySystem;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myTypeChecker = myMocksControl.createMock(ITypeChecker.class);
		myInterpreter = myMocksControl.createMock(IInterpreter.class);
		myHobbesOutput = myMocksControl.createMock(IHobbesOutput.class);

		mySystem = new InterpreterSystem(myTypeChecker, myInterpreter,
				myHobbesOutput);
	}

	@Test
	public void shouldEvalAndPrint() throws IOException, RecognitionException {
		ByteOutputStream out = new ByteOutputStream();
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);
		DatumTIR datum = myMocksControl.createMock(DatumTIR.class);

		EasyMock.expect(myInterpreter.interpret(tir)).andReturn(datum);
		EasyMock.expect(myHobbesOutput.asHobbesOutput(datum)).andReturn(
				"the output");

		myMocksControl.replay();
		mySystem.evalAndPrint(new PrintStream(out), tir);
		assertEquals("the output\n", out.toString());
		myMocksControl.verify();
	}

	@Test
	public void shouldTypeCheckWithError() throws RecognitionException,
			IOException {
		ByteOutputStream err = new ByteOutputStream();
		IPosition position = new Position(55);
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(tir.accept(myTypeChecker)).andThrow(
				new HobbesTypeException(position, "<this operation>"));

		myMocksControl.replay();
		try {
			mySystem.typeCheck(new PrintStream(err), tir);
			fail("should fail to type check");
		} catch (AbortInterpretationException e) {
			assertEquals(CLIStatusCodes.STATUS_TYPE_ERROR, e.getStatus());
			assertEquals("Error on line 55: <this operation> is not defined\n",
					err.toString());
		}
		myMocksControl.verify();
	}
}
