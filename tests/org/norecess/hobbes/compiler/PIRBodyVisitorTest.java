package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.expressions.IfETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class PIRBodyVisitorTest {

	private IMocksControl		myMocksControl;

	private IPIRBodyVisitor		myRecurser;
	private IResourceAllocator	myResourceAllocator;
	private IRegister			myTarget;

	private PIRBodyVisitor		myVisitor;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myRecurser = myMocksControl.createMock(IPIRBodyVisitor.class);
		myResourceAllocator = myMocksControl
				.createMock(IResourceAllocator.class);
		myTarget = myMocksControl.createMock(IRegister.class);
		EasyMock.expect(myTarget.asString()).andReturn("$I3").anyTimes();

		myVisitor = new PIRBodyVisitor(myRecurser, myResourceAllocator,
				myTarget);
	}

	@Test
	public void shouldCompileInteger() {
		myMocksControl.replay();
		assertEquals(//
				new Code("$I3 = 8"), //
				myVisitor.visitIntegerETIR(new IntegerETIR(8)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileOperator() {
		IOperator operator = myMocksControl.createMock(IOperator.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);
		IRegister subTarget = myMocksControl.createMock(IRegister.class);

		EasyMock.expect(myResourceAllocator.nextRegister())
				.andReturn(subTarget);
		EasyMock.expect(left.accept(myVisitor))
				.andReturn(new Code("left code"));
		EasyMock.expect(myRecurser.recurse(right, subTarget)).andReturn(
				new Code("right code"));
		EasyMock.expect(subTarget.asString()).andReturn("$I8");
		EasyMock.expect(operator.getPunctuation()).andReturn("P");

		myMocksControl.replay();
		assertEquals(//
				new Code("left code", //
						"right code", //
						"$I3 P= $I8"), //
				myVisitor.visitOperatorETIR(new OperatorETIR(left, operator,
						right)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileIf() {
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);

		EasyMock.expect(myRecurser.recurse(test, myTarget)).andReturn(
				new Code("compute test"));
		EasyMock.expect(myRecurser.recurse(consequence, myTarget)).andReturn(
				new Code("compute then"));
		EasyMock.expect(myRecurser.recurse(otherwise, myTarget)).andReturn(
				new Code("compute else"));

		myMocksControl.replay();
		assertEquals(new Code("compute test", "compute else", "compute then"), //
				myVisitor.visitIfETIR(new IfETIR(test, consequence, otherwise)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileArgvReference() {
		myMocksControl.replay();
		assertEquals(new Code("$I3 = 88", "$I3 = argv[$I3]"), //
				myVisitor.visitVariableETIR(new VariableETIR(
						new SubscriptLValueTIR(new SimpleLValueTIR("ARGV"),
								new IntegerETIR(88)))));
		myMocksControl.verify();
	}

}
