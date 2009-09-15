package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.LValueTIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.hobbes.backend.Code;

public class PIRPrologCompilerTest {

	private IMocksControl		myMockControl;

	private PIRPrologCompiler	myPrologCompiler;

	@Before
	public void setUp() {
		myMockControl = EasyMock.createControl();

		myPrologCompiler = new PIRPrologCompiler();
	}

	@Test
	public void shouldGenerateProlog() {
		myMockControl.replay();
		assertEquals(new Code(".sub main"), myPrologCompiler
				.generateProlog(new IntegerETIR(5)));
		myMockControl.verify();
	}

	@Test
	public void shouldGeneratePrologForArgv() {
		LValueTIR lvalue = myMockControl.createMock(LValueTIR.class);

		myMockControl.replay();
		assertEquals(new Code(".sub main", ".param pmc argv"), myPrologCompiler
				.generateProlog(new VariableETIR(lvalue)));
		myMockControl.verify();
	}

	@Test
	public void shouldGeneratePrologForOp() {
		IOperator operator = myMockControl.createMock(IOperator.class);
		ExpressionTIR left = myMockControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMockControl.createMock(ExpressionTIR.class);

		EasyMock.expect(left.accept(myPrologCompiler)).andReturn(
				new Code("left prolog"));
		EasyMock.expect(right.accept(myPrologCompiler)).andReturn(
				new Code("right prolog"));

		myMockControl.replay();
		assertEquals(new Code(".sub main", "left prolog", "right prolog"),
				myPrologCompiler.generateProlog(new OperatorETIR(left,
						operator, right)));
		myMockControl.verify();
	}
}
