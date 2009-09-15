package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.hobbes.backend.Code;

public class PIRBodyCompilerTest {

	private IMocksControl		myMockControl;

	private IRegisterAllocator	myRegisterAllocator;
	private PIRBodyCompiler		myCompiler;

	@Before
	public void setUp() {
		myMockControl = EasyMock.createControl();

		myRegisterAllocator = myMockControl
				.createMock(IRegisterAllocator.class);
		myCompiler = new PIRBodyCompiler(myRegisterAllocator);
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
