package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.body.IPIRBodyCompiler;
import org.norecess.hobbes.compiler.epilog.IPIREpilogCompiler;
import org.norecess.hobbes.compiler.prolog.IPIRPrologCompiler;

public class PIRCompilerTest {

	private IMocksControl		myControl;

	private IPIRPrologCompiler	myPrologCompiler;
	private IPIRBodyCompiler	myBodyCompiler;
	private IPIREpilogCompiler	myEpilogCompiler;

	private IPIRCompiler		myCompiler;

	@Before
	public void setUp() {
		myControl = EasyMock.createControl();

		myPrologCompiler = myControl.createMock(IPIRPrologCompiler.class);
		myBodyCompiler = myControl.createMock(IPIRBodyCompiler.class);
		myEpilogCompiler = myControl.createMock(IPIREpilogCompiler.class);

		myCompiler = new PIRCompiler(myPrologCompiler, myBodyCompiler,
				myEpilogCompiler);
	}

	@Test
	public void shouldCompile() throws RecognitionException, IOException {
		ExpressionTIR tir = myControl.createMock(ExpressionTIR.class);
		PrimitiveType returnType = myControl.createMock(PrimitiveType.class);

		EasyMock.expect(myPrologCompiler.generateProlog(tir)).andReturn(
				new Code("prolog"));
		EasyMock.expect(myBodyCompiler.generate(returnType, tir)).andReturn(
				new Code("body"));
		EasyMock.expect(myBodyCompiler.generatePrint(returnType, tir))
				.andReturn(new Code("print"));
		EasyMock.expect(myEpilogCompiler.generate()).andReturn(
				new Code("epilog"));

		myControl.replay();
		assertEquals(new Code("prolog", "body", "print", "epilog"),
				myCompiler.compile(returnType, tir));
		myControl.verify();
	}
}
