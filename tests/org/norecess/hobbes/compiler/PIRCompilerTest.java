package org.norecess.hobbes.compiler;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.Code;

public class PIRCompilerTest {

	private IMocksControl		myControl;

	private IPIRPrologCompiler	myPrologCompiler;
	private IPIRBodyCompiler	myBodyCompiler;

	private PIRCompiler			myCompiler;

	@Before
	public void setUp() {
		myControl = EasyMock.createControl();

		myPrologCompiler = myControl.createMock(IPIRPrologCompiler.class);
		myBodyCompiler = myControl.createMock(IPIRBodyCompiler.class);

		myCompiler = new PIRCompiler(myPrologCompiler, myBodyCompiler);
	}

	@Test
	public void shouldCompile() throws RecognitionException, IOException {
		ExpressionTIR tir = myControl.createMock(ExpressionTIR.class);

		expect(myPrologCompiler.generateProlog(tir)).andReturn(
				new Code("prolog"));
		expect(myBodyCompiler.generate(tir)).andReturn(new Code("body"));

		myControl.replay();
		assertEquals(new Code("prolog", "body"), myCompiler.compile(tir));
		myControl.verify();
	}

}
