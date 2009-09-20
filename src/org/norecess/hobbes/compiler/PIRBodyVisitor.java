package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.expressions.BreakETIR;
import org.norecess.citkit.tir.expressions.IArrayETIR;
import org.norecess.citkit.tir.expressions.IAssignmentETIR;
import org.norecess.citkit.tir.expressions.IBooleanETIR;
import org.norecess.citkit.tir.expressions.ICallETIR;
import org.norecess.citkit.tir.expressions.IFieldAssignmentTIR;
import org.norecess.citkit.tir.expressions.IForETIR;
import org.norecess.citkit.tir.expressions.IIfETIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.ILambdaETIR;
import org.norecess.citkit.tir.expressions.ILetETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR;
import org.norecess.citkit.tir.expressions.IRecordETIR;
import org.norecess.citkit.tir.expressions.ISequenceETIR;
import org.norecess.citkit.tir.expressions.IStringETIR;
import org.norecess.citkit.tir.expressions.IVariableETIR;
import org.norecess.citkit.tir.expressions.IWhileETIR;
import org.norecess.citkit.tir.expressions.NilETIR;
import org.norecess.citkit.tir.lvalues.IFieldValueTIR;
import org.norecess.citkit.tir.lvalues.ISimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.ISubscriptLValueTIR;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;

public class PIRBodyVisitor implements IPIRBodyVisitor {

	private final IPIRBodyVisitor		myRecurser;
	private final IResourceAllocator	myRegisterAllocator;
	private final IRegister				myTarget;

	public PIRBodyVisitor(IResourceAllocator registerAllocator, IRegister target) {
		myRecurser = this;
		myRegisterAllocator = registerAllocator;
		myTarget = target;
	}

	public PIRBodyVisitor(IPIRBodyVisitor recurser,
			IResourceAllocator registerAllocator, IRegister target) {
		myRecurser = recurser;
		myRegisterAllocator = registerAllocator;
		myTarget = target;
	}

	public ICode recurse(ExpressionTIR expression, IRegister target) {
		return expression
				.accept(new PIRBodyVisitor(myRegisterAllocator, target));
	}

	public ICode visitIntegerETIR(IIntegerETIR integer) {
		return new Code(myTarget.asString() + " = " + integer.getValue());
	}

	public ICode visitVariableETIR(IVariableETIR variable) {
		return variable.getLValue().accept(this);
	}

	public ICode visitSubscriptLValue(ISubscriptLValueTIR lValue) {
		ICode code = new Code();
		code.append(lValue.getIndex().accept(this));
		code.add(myTarget.asString() + " = argv[" + myTarget.asString() + "]");
		return code;
	}

	public ICode visitOperatorETIR(IOperatorETIR expression) {
		ICode code = new Code();
		code.append(expression.getLeft().accept(this));
		IRegister next = myRegisterAllocator.nextRegister();
		code.append(myRecurser.recurse(expression.getRight(), next));
		code.add(myTarget.asString() + " "
				+ expression.getOperator().getPunctuation() + "= "
				+ next.asString());
		return code;
	}

	public ICode visitIfETIR(IIfETIR ife) {
		ICode code = new Code();
		code.append(myRecurser.recurse(ife.getTest(), myTarget));
		code.append(myRecurser.recurse(ife.getElseClause(), myTarget));
		code.append(myRecurser.recurse(ife.getThenClause(), myTarget));
		return code;
	}

	//
	// Unimplemented
	//
	public ICode visitArrayETIR(IArrayETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitAssignmentETIR(IAssignmentETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitBooleanETIR(IBooleanETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitBreakETIR(BreakETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitCallETIR(ICallETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitFieldAssignmentETIR(IFieldAssignmentTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitForETIR(IForETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitLambdaETIR(ILambdaETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitLetETIR(ILetETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitNilETIR(NilETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitRecordETIR(IRecordETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitSequenceETIR(ISequenceETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitStringETIR(IStringETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitWhileETIR(IWhileETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitFieldLValue(IFieldValueTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitSimpleLValue(ISimpleLValueTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

}