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

public class HobbesPIRPrologCompilerTest {

	private IMocksControl			myMockControl;

	private HobbesPIRPrologCompiler	myPrologCompiler;

	@Before
	public void setUp() {
		myMockControl = EasyMock.createControl();

		myPrologCompiler = new HobbesPIRPrologCompiler();
	}

	@Test
	public void shouldGenerateProlog() {
		myMockControl.replay();
		assertEquals(new Code(".sub main"), myPrologCompiler.generateProlog(
				new Code(), new IntegerETIR(5)));
		myMockControl.verify();
	}

	@Test
	public void shouldGeneratePrologForArgv() {
		LValueTIR lvalue = myMockControl.createMock(LValueTIR.class);

		myMockControl.replay();
		assertEquals(new Code(".sub main", ".param pmc argv"), myPrologCompiler
				.generateProlog(new Code(), new VariableETIR(lvalue)));
		myMockControl.verify();
	}

	@Test
	public void shouldGeneratePrologForOp() {
		IOperator operator = myMockControl.createMock(IOperator.class);
		ExpressionTIR left = myMockControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMockControl.createMock(ExpressionTIR.class);

		EasyMock.expect(left.accept(myPrologCompiler)).andReturn(null);
		EasyMock.expect(right.accept(myPrologCompiler)).andReturn(null);

		myMockControl.replay();
		assertEquals(new Code(".sub main"), myPrologCompiler.generateProlog(
				new Code(), new OperatorETIR(left, operator, right)));
		myMockControl.verify();
	}

}