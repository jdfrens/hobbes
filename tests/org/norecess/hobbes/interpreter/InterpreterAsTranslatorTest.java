package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.hobbes.output.IHobbesOutput;

public class InterpreterAsTranslatorTest {

	private IMocksControl			myMocksControl;

	private IInterpreter			myInterpreter;
	private IHobbesOutput			myHobbesOutput;

	private InterpreterAsTranslator	myTranslator;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myInterpreter = myMocksControl.createMock(IInterpreter.class);
		myHobbesOutput = myMocksControl.createMock(IHobbesOutput.class);

		myTranslator = new InterpreterAsTranslator(myInterpreter,
				myHobbesOutput);
	}

	@Test
	public void shouldEvalAndPrint() throws IOException, RecognitionException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ExpressionTIR tir = myMocksControl.createMock(ExpressionTIR.class);
		DatumTIR datum = myMocksControl.createMock(DatumTIR.class);

		EasyMock.expect(myInterpreter.interpret(tir)).andReturn(datum);
		EasyMock.expect(myHobbesOutput.asHobbesOutput(datum)).andReturn(
				"the output");

		myMocksControl.replay();
		myTranslator.evalAndPrint(new PrintStream(out), tir);
		assertEquals("the output\n", out.toString());
		myMocksControl.verify();
	}
}
