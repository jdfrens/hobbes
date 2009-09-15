package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;

public class HobbesPIRBodyVisitorTest {

	private IMocksControl			myMocksControl;

	private IHobbesPIRBodyVisitor	myRecurser;
	private IRegisterAllocator		myRegisterAllocator;

	private HobbesPIRBodyVisitor	myVisitor;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myRecurser = myMocksControl.createMock(IHobbesPIRBodyVisitor.class);
		myRegisterAllocator = myMocksControl
				.createMock(IRegisterAllocator.class);

		myVisitor = new HobbesPIRBodyVisitor(myRecurser, myRegisterAllocator,
				new Register(3));
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
	public void shouldCompilePlus() {
		IOperator operator = myMocksControl.createMock(IOperator.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);

		EasyMock.expect(myRegisterAllocator.next()).andReturn(new Register(8));
		EasyMock.expect(left.accept(myVisitor))
				.andReturn(new Code("left code"));
		EasyMock.expect(myRecurser.recurse(right, new Register(8))).andReturn(
				new Code("right code"));
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
	public void shouldCompileArgvReference() {
		myMocksControl.replay();
		assertEquals(new Code("$I3 = 88", "$I3 = argv[$I3]"), myVisitor
				.visitVariableETIR(new VariableETIR(new SubscriptLValueTIR(
						new SimpleLValueTIR("ARGV"), new IntegerETIR(88)))));
		myMocksControl.verify();
	}

}
