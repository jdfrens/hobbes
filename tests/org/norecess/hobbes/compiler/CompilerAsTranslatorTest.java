package org.norecess.hobbes.compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.backend.ICodeWriter;
import org.norecess.hobbes.backend.IPIRCleaner;

public class CompilerAsTranslatorTest {

	private IMocksControl			myMocksControl;

	private IPIRCompiler			myCompiler;
	private IPIRCleaner				myPirCleaner;
	private ICodeWriter				myCodeWriter;

	private CompilerAsTranslator	myCompilerAsTranslator;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myCompiler = myMocksControl.createMock(IPIRCompiler.class);
		myPirCleaner = myMocksControl.createMock(IPIRCleaner.class);
		myCodeWriter = myMocksControl.createMock(ICodeWriter.class);

		myCompilerAsTranslator = new CompilerAsTranslator(myCompiler,
				myPirCleaner, myCodeWriter);
	}

	@Test
	public void shouldEvalAndPrint() throws IOException {
		PrintStream out = new PrintStream(new ByteArrayOutputStream());
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);
		ICode compiledCode = myMocksControl.createMock(ICode.class);
		ICode cleanedCode = myMocksControl.createMock(ICode.class);

		EasyMock.expect(myCompiler.compile(expression)).andReturn(compiledCode);
		EasyMock.expect(myPirCleaner.process(compiledCode)).andReturn(
				cleanedCode);
		myCodeWriter.writeCode(out, cleanedCode);

		myMocksControl.replay();
		myCompilerAsTranslator.evalAndPrint(out, expression);
		myMocksControl.verify();
	}

}
