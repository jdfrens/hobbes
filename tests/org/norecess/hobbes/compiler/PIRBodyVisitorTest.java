package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertSame;

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
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.ILabel;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;
import org.norecess.hobbes.compiler.resources.Register;

public class PIRBodyVisitorTest {

	private IMocksControl		myMocksControl;

	private IPIRBodyVisitor		myRecurser;
	private IResourceAllocator	myResourceAllocator;
	private IRegister			myTarget;

	private PIRBodyVisitor		myVisitor;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createStrictControl();

		myRecurser = myMocksControl.createMock(IPIRBodyVisitor.class);
		myResourceAllocator = myMocksControl
				.createMock(IResourceAllocator.class);
		myTarget = new Register(3);

		myVisitor = new PIRBodyVisitor(myRecurser, myResourceAllocator,
				myTarget);
	}

	@Test
	public void shouldCompileInteger() {
		ICode code = expectNewCode();

		EasyMock.expect(code.add("$I3 = 8")).andReturn(code);

		myMocksControl.replay();
		assertSame(code, myVisitor.visitIntegerETIR(new IntegerETIR(8)));
		myMocksControl.verify();
	}

	private ICode expectNewCode() {
		ICode code = myMocksControl.createMock(ICode.class);
		EasyMock.expect(myResourceAllocator.createCode()).andReturn(code);
		return code;
	}

	@Test
	public void shouldCompileOperator() {
		ICode code = expectNewCode();
		ICode leftCode = myMocksControl.createMock(ICode.class);
		ICode rightCode = myMocksControl.createMock(ICode.class);
		IOperator operator = myMocksControl.createMock(IOperator.class);
		ExpressionTIR left = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR right = myMocksControl.createMock(ExpressionTIR.class);
		IRegister subTarget = new Register(8);

		EasyMock.expect(left.accept(myVisitor)).andReturn(leftCode);
		EasyMock.expect(code.append(leftCode)).andReturn(code);
		EasyMock.expect(myResourceAllocator.nextRegister())
				.andReturn(subTarget);
		EasyMock.expect(myRecurser.recurse(right, subTarget)).andReturn(
				rightCode);
		EasyMock.expect(code.append(rightCode)).andReturn(code);
		EasyMock.expect(operator.getPunctuation()).andReturn("P");
		EasyMock.expect(code.add("$I3 P= $I8")).andReturn(code);

		myMocksControl.replay();
		assertSame(code, myVisitor.visitOperatorETIR(new OperatorETIR(left,
				operator, right)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileIf() {
		ICode code = expectNewCode();
		ExpressionTIR test = myMocksControl.createMock(ExpressionTIR.class);
		ExpressionTIR consequence = myMocksControl
				.createMock(ExpressionTIR.class);
		ExpressionTIR otherwise = myMocksControl
				.createMock(ExpressionTIR.class);
		ICode testCode = myMocksControl.createMock(ICode.class);
		ICode consequenceCode = myMocksControl.createMock(ICode.class);
		ICode otherwiseCode = myMocksControl.createMock(ICode.class);
		ILabel elseLabel = myMocksControl.createMock("thenLabel", ILabel.class);
		ILabel endLabel = myMocksControl.createMock("endLabel", ILabel.class);

		EasyMock.expect(myRecurser.recurse(test, myTarget)).andReturn(testCode);
		EasyMock.expect(code.append(testCode)).andReturn(code);
		EasyMock.expect(myRecurser.recurse(otherwise, myTarget)).andReturn(
				otherwiseCode);
		EasyMock.expect(code.append(otherwiseCode)).andReturn(code);
		EasyMock.expect(myResourceAllocator.nextLabel()).andReturn(elseLabel);
		EasyMock.expect(code.add(elseLabel)).andReturn(code);
		EasyMock.expect(myRecurser.recurse(consequence, myTarget)).andReturn(
				consequenceCode);
		EasyMock.expect(code.append(consequenceCode)).andReturn(code);
		EasyMock.expect(myResourceAllocator.nextLabel()).andReturn(endLabel);
		EasyMock.expect(code.add(endLabel)).andReturn(code);

		myMocksControl.replay();
		assertSame(code, myVisitor.visitIfETIR(new IfETIR(test, consequence,
				otherwise)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileVariableExpression() {
		ICode code = myMocksControl.createMock(ICode.class);
		LValueTIR lvalue = myMocksControl.createMock(LValueTIR.class);

		EasyMock.expect(lvalue.accept(myVisitor)).andReturn(code);

		myMocksControl.replay();
		assertSame(code, myVisitor.visitVariableETIR(new VariableETIR(lvalue)));
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileArgvReference() {
		ICode code = myMocksControl.createMock(ICode.class);
		ICode indexcode = myMocksControl.createMock(ICode.class);

		EasyMock.expect(myResourceAllocator.createCode()).andReturn(code);
		EasyMock.expect(
				myRecurser.recurse(EasyMock.eq(new IntegerETIR(88)), EasyMock
						.same(myTarget))).andReturn(indexcode);
		EasyMock.expect(code.append(indexcode)).andReturn(code);
		EasyMock.expect(code.add("$I3 = argv[$I3]")).andReturn(code);

		myMocksControl.replay();
		assertSame(code, myVisitor.visitSubscriptLValue(new SubscriptLValueTIR(
				null, new IntegerETIR(88))));
		myMocksControl.verify();
	}

}
