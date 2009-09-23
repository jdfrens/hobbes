package org.norecess.hobbes.interpreter;

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
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.citkit.visitors.LValueTIRVisitor;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.interpreter.operators.ApplyingOperator;

public class Interpreter implements ExpressionTIRVisitor<IIntegerETIR>,
		LValueTIRVisitor<IIntegerETIR> {

	private final IIntegerETIR[]					myArgv;
	private final Map<Operator, ApplyingOperator>	myOperatorInterpreter;

	public Interpreter(IIntegerETIR[] argv,
			Map<Operator, ApplyingOperator> operatorInterpreters) {
		myArgv = argv;
		myOperatorInterpreter = operatorInterpreters;
	}

	public IIntegerETIR interpret(ExpressionTIR expression) {
		return expression.accept(this);
	}

	public IIntegerETIR visitIntegerETIR(IIntegerETIR integer) {
		return integer;
	}

	public IIntegerETIR visitOperatorETIR(IOperatorETIR expression) {
		return myOperatorInterpreter.get(expression.getOperator()).apply(
				expression.getLeft().accept(this),
				expression.getRight().accept(this));
	}

	public IIntegerETIR visitVariableETIR(IVariableETIR variable) {
		return myArgv[variable.getLValue().accept(this).getValue()];
	}

	public IIntegerETIR visitSubscriptLValue(ISubscriptLValueTIR subscript) {
		return subscript.getIndex().accept(this);
	}

	public IIntegerETIR visitIfETIR(IIfETIR ife) {
		if (HobbesBoolean.TRUE.equals(ife.getTest().accept(this))) {
			return ife.getThenClause().accept(this);
		} else {
			return ife.getElseClause().accept(this);
		}
	}

	//
	// Unimplemented features
	//
	public IIntegerETIR visitArrayETIR(IArrayETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitAssignmentETIR(IAssignmentETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitBooleanETIR(IBooleanETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitBreakETIR(BreakETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitCallETIR(ICallETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitFieldAssignmentETIR(IFieldAssignmentTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitForETIR(IForETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitLambdaETIR(ILambdaETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitLetETIR(ILetETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitNilETIR(NilETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitRecordETIR(IRecordETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitSequenceETIR(ISequenceETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitStringETIR(IStringETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitWhileETIR(IWhileETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitFieldLValue(IFieldValueTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitSimpleLValue(ISimpleLValueTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

}
