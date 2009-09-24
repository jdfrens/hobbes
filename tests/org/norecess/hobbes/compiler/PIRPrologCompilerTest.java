package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.LValueTIR;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.backend.Code;

public class PIRPrologCompilerTest {

	private IMocksControl		myMocksControl;

	private PIRPrologCompiler	myPrologCompiler;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myPrologCompiler = new PIRPrologCompiler();
	}

	@Test
	public void shouldGeneratePrologForInteger() {
		myMocksControl.replay();
		assertEquals(new Code(".sub main", "load_bytecode \"print.pbc\""),
				myPrologCompiler.generateProlog(new IntegerETIR(5)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForBoolean() {
		myMocksControl.replay();
		assertEquals(new Code(".sub main", "load_bytecode \"print.pbc\""),
				myPrologCompiler.generateProlog(HobbesBoolean.TRUE));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForArgv() {
		LValueTIR lvalue = myMocksControl.createMock(LValueTIR.class);

		myMocksControl.replay();
		assertEquals(new Code(".sub main", ".param pmc argv",
				"load_bytecode \"print.pbc\""), myPrologCompiler
				.generateProlog(new VariableETIR(lvalue)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForOp() {
		IOperator operator = myMocksControl.createMock(IOperator.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(left.accept(myPrologCompiler)).andReturn(
				new Code("left prolog"));
		EasyMock.expect(right.accept(myPrologCompiler)).andReturn(
				new Code("right prolog"));

		myMocksControl.replay();
		assertEquals(new Code(".sub main", "left prolog", "right prolog",
				"load_bytecode \"print.pbc\""), myPrologCompiler
				.generateProlog(new OperatorETIR(left, operator, right)));
		myMocksControl.verify();
	}

	@Test
	public void shouldGeneratePrologForIf() {
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(test.accept(myPrologCompiler)).andReturn(
				new Code("test prolog"));
		EasyMock.expect(consequence.accept(myPrologCompiler)).andReturn(
				new Code("then prolog"));
		EasyMock.expect(otherwise.accept(myPrologCompiler)).andReturn(
				new Code("else prolog"));

		myMocksControl.replay();
		assertEquals(new Code(".sub main", "test prolog", "then prolog",
				"else prolog", "load_bytecode \"print.pbc\""), //
				myPrologCompiler.generateProlog(new IfETIR(test, consequence,
						otherwise)));
		myMocksControl.verify();
	}
}
