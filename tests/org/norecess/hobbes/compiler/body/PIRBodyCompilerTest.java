package org.norecess.hobbes.compiler.body;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.ICompilerFactory;

public class PIRBodyCompilerTest {

	private IMocksControl		myMocksControl;

	private ICompilerFactory	myCompilerFactory;

	private PIRBodyCompiler		myCompiler;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myCompilerFactory = myMocksControl.createMock(ICompilerFactory.class);

		myCompiler = new PIRBodyCompiler(myCompilerFactory);
	}

	@Test
	public void shouldCompileExpression() {
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);
		IPIRBodySubcompiler bodySubcompiler = myMocksControl
				.createMock(IPIRBodySubcompiler.class);

		EasyMock.expect(expression.getType()).andReturn(IntegerType.TYPE);
		EasyMock.expect(
				myCompilerFactory
						.createBodySubcompiler(PIRBodyCompiler.INT_ACC))
				.andReturn(bodySubcompiler);
		EasyMock.expect(expression.accept(bodySubcompiler)).andReturn(
				new Code("expression computation"));

		myMocksControl.replay();
		assertEquals(new Code("expression computation"),
				myCompiler.generate(expression));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileFloatingPointExpression() {
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);
		IPIRBodySubcompiler bodySubcompiler = myMocksControl
				.createMock(IPIRBodySubcompiler.class);

		EasyMock.expect(expression.getType()).andReturn(FloatingPointType.TYPE);
		EasyMock.expect(
				myCompilerFactory
						.createBodySubcompiler(PIRBodyCompiler.FLOAT_ACC))
				.andReturn(bodySubcompiler);
		EasyMock.expect(expression.accept(bodySubcompiler)).andReturn(
				new Code("expression computation"));

		myMocksControl.replay();
		assertEquals(new Code("expression computation"),
				myCompiler.generate(expression));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompilePrint() {
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(expression.getType()).andReturn(IntegerType.TYPE)
				.anyTimes();

		myMocksControl.replay();
		assertEquals(new Code("print $I0", //
				"print \"\\n\""), myCompiler.generatePrint(expression));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileBooleanPrint() {
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(expression.getType()).andReturn(BooleanType.TYPE)
				.anyTimes();

		myMocksControl.replay();
		assertEquals(new Code("print_bool($I0)", //
				"print \"\\n\""), myCompiler.generatePrint(expression));
		myMocksControl.verify();
	}

}
