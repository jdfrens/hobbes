package org.norecess.hobbes.compiler;

import java.util.Map;

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
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.tir.lvalues.IFieldValueTIR;
import org.norecess.citkit.tir.lvalues.ISimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.ISubscriptLValueTIR;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.ILabel;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class PIRBodyVisitor implements IPIRBodyVisitor {

	private final IPIRBodyVisitor						myRecurser;
	private final IResourceAllocator					myResourceAllocator;
	private final Map<Operator, OperatorInstruction>	myOperatorInstructions;
	private final IRegister								myTarget;

	public PIRBodyVisitor(IResourceAllocator resourceAllocator,
			Map<Operator, OperatorInstruction> operatorInstructions,
			IRegister target) {
		myOperatorInstructions = operatorInstructions;
		myRecurser = this;
		myResourceAllocator = resourceAllocator;
		myTarget = target;
	}

	public PIRBodyVisitor(IPIRBodyVisitor recurser,
			IResourceAllocator resourceAllocator,
			Map<Operator, OperatorInstruction> operatorInstructions,
			IRegister target) {
		myRecurser = recurser;
		myResourceAllocator = resourceAllocator;
		myOperatorInstructions = operatorInstructions;
		myTarget = target;
	}

	public ICode recurse(ExpressionTIR expression, IRegister target) {
		return expression.accept(new PIRBodyVisitor(myResourceAllocator,
				myOperatorInstructions, target));
	}

	public ICode visitIntegerETIR(IIntegerETIR integer) {
		return myResourceAllocator.createCode().add(myTarget,
				" = " + integer.getValue());
	}

	public ICode visitBooleanETIR(IBooleanETIR b) {
		ICode code = myResourceAllocator.createCode();
		code.add(myTarget, " = ", b == HobbesBoolean.TRUE ? "1" : "0");
		return code;
	}

	public ICode visitVariableETIR(IVariableETIR variable) {
		return variable.getLValue().accept(this);
	}

	public ICode visitSubscriptLValue(ISubscriptLValueTIR lValue) {
		ICode code = myResourceAllocator.createCode();
		code.append(myRecurser.recurse(lValue.getIndex(), myTarget));
		code.add(myTarget, " = argv[", myTarget, "]");
		return code;
	}

	public ICode visitOperatorETIR(IOperatorETIR expression) {
		ICode code = myResourceAllocator.createCode();
		code.append(expression.getLeft().accept(this));
		IRegister next = myResourceAllocator.nextRegister();
		code.append(myRecurser.recurse(expression.getRight(), next));
		code.append(myOperatorInstructions.get(expression.getOperator())
				.compile(myResourceAllocator, myTarget, next));
		return code;
	}

	public ICode visitIfETIR(IIfETIR ife) {
		ICode code = myResourceAllocator.createCode();
		ILabel thenLabel = myResourceAllocator.nextLabel();
		ILabel endLabel = myResourceAllocator.nextLabel();
		code.append(myRecurser.recurse(ife.getTest(), myTarget));
		code.add("if ", myTarget, " goto ", thenLabel);
		code.append(myRecurser.recurse(ife.getElseClause(), myTarget));
		code.add("goto ", endLabel);
		code.add(thenLabel);
		code.append(myRecurser.recurse(ife.getThenClause(), myTarget));
		code.add(endLabel);
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
