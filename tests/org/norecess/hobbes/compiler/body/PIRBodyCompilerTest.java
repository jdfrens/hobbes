package org.norecess.hobbes.compiler.body;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.ICompilerFactory;
import org.norecess.hobbes.compiler.body.IPIRBodyVisitor;
import org.norecess.hobbes.compiler.body.PIRBodyCompiler;
import org.norecess.hobbes.typechecker.ITypeChecker;

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
		IPIRBodyVisitor bodyVisitor = myMocksControl
				.createMock(IPIRBodyVisitor.class);

		EasyMock.expect(myCompilerFactory.createBodyVisitor()).andReturn(
				bodyVisitor);
		EasyMock.expect(expression.accept(bodyVisitor)).andReturn(
				new Code("expression computation"));

		myMocksControl.replay();
		assertEquals(new Code("expression computation"), myCompiler
				.generate(expression));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompilePrint() {
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);
		ITypeChecker typeChecker = myMocksControl
				.createMock(ITypeChecker.class);

		EasyMock.expect(myCompilerFactory.createTypeChecker()).andReturn(
				typeChecker);
		EasyMock.expect(expression.accept(typeChecker)).andReturn(
				IntegerType.INTEGER_TYPE);

		myMocksControl.replay();
		assertEquals(new Code("print $I0", //
				"print \"\\n\""), myCompiler.generatePrint(expression));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileBooleanPrint() {
		ExpressionTIR expression = myMocksControl
				.createMock(ExpressionTIR.class);
		ITypeChecker typeChecker = myMocksControl
				.createMock(ITypeChecker.class);

		EasyMock.expect(myCompilerFactory.createTypeChecker()).andReturn(
				typeChecker);
		EasyMock.expect(expression.accept(typeChecker)).andReturn(
				BooleanType.BOOLEAN_TYPE);

		myMocksControl.replay();
		assertEquals(new Code("print_bool($I0)", //
				"print \"\\n\""), myCompiler.generatePrint(expression));
		myMocksControl.verify();
	}

}