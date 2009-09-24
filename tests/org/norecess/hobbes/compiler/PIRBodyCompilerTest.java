package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;
import org.norecess.hobbes.compiler.resources.Register;

public class PIRBodyCompilerTest {

	private IMocksControl						myMockControl;

	private IResourceAllocator					myResourceAllocator;
	private Map<Operator, OperatorInstruction>	myOperatorInstructions;

	private PIRBodyCompiler						myCompiler;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMockControl = EasyMock.createControl();

		myResourceAllocator = myMockControl
				.createMock(IResourceAllocator.class);
		myOperatorInstructions = myMockControl.createMock(Map.class);

		myCompiler = new PIRBodyCompiler(myResourceAllocator,
				myOperatorInstructions);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldCompileExpression() {
		ExpressionTIR expression = myMockControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(myResourceAllocator.nextRegister()).andReturn(
				new Register(4));
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
