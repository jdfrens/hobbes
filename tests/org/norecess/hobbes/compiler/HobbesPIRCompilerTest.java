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

public class HobbesPIRCompilerTest {

	private IMocksControl				myControl;

	private IHobbesPIRPrologCompiler	myPrologCompiler;
	private IHobbesPIRBodyCompiler		myBodyCompiler;

	private HobbesPIRCompiler			myCompiler;

	@Before
	public void setUp() {
		myControl = EasyMock.createControl();

		myPrologCompiler = myControl.createMock(IHobbesPIRPrologCompiler.class);
		myBodyCompiler = myControl.createMock(IHobbesPIRBodyCompiler.class);

		myCompiler = new HobbesPIRCompiler(myPrologCompiler, myBodyCompiler);
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
