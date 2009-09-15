package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.antlr.runtime.CommonToken;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.hobbes.frontend.HobbesLexer;

public class HobbesPIRBodyCompilerTest {

	public static final CommonToken	PLUS_TOKEN		= new CommonToken(
															HobbesLexer.PLUS,
															"+");
	public static final CommonToken	MINUS_TOKEN		= new CommonToken(
															HobbesLexer.MINUS,
															"-");
	public static final CommonToken	MULTIPLY_TOKEN	= new CommonToken(
															HobbesLexer.MULTIPLY,
															"*");

	private IMocksControl			myMockControl;

	private IRegisterAllocator		myRegisterAllocator;
	private HobbesPIRBodyCompiler	myCompiler;

	@Before
	public void setUp() {
		myMockControl = EasyMock.createControl();

		myRegisterAllocator = myMockControl
				.createMock(IRegisterAllocator.class);
		myCompiler = new HobbesPIRBodyCompiler(myRegisterAllocator);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldCompileExpression() {
		ExpressionTIR expression = myMockControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(myRegisterAllocator.next()).andReturn(new Register(4));
		EasyMock.expect(
				expression.accept(EasyMock.isA(ExpressionTIRVisitor.class)))
				.andReturn(new Code("body code"));

		myMockControl.replay();
		assertEquals(new Code("body code", //
				"print $I4", //
				"print \"\\n\"", //
				".end"), myCompiler.generate(expression));
		myMockControl.verify();
	}

}
