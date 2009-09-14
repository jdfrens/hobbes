package org.norecess.hobbes.interpreter;

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
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.NilETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.tir.lvalues.IFieldValueTIR;
import org.norecess.citkit.tir.lvalues.ISimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.ISubscriptLValueTIR;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.citkit.visitors.LValueTIRVisitor;

public class HobbesInterpreter implements ExpressionTIRVisitor<IIntegerETIR>,
		LValueTIRVisitor<IIntegerETIR> {

	private final IIntegerETIR[]	myArgv;

	public HobbesInterpreter(IIntegerETIR[] argv) {
		myArgv = argv;
	}

	public IIntegerETIR interpret(ExpressionTIR expression) {
		return expression.accept(this);
	}

	public IIntegerETIR visitIntegerETIR(IIntegerETIR integer) {
		return integer;
	}

	public IntegerETIR visitOperatorETIR(IOperatorETIR expression) {
		return apply(expression.getOperator(), //
				expression.getLeft().accept(this).getValue(), //
				expression.getRight().accept(this).getValue());
	}

	public IntegerETIR apply(IOperator operator, int left, int right) {
		if (operator == Operator.ADD) {
			return new IntegerETIR(left + right);
		} else if (operator == Operator.SUBTRACT) {
			return new IntegerETIR(left - right);
		} else if (operator == Operator.MULTIPLY) {
			return new IntegerETIR(left * right);
		} else {
			throw new IllegalArgumentException(operator + " is not supported.");
		}
	}

	public IIntegerETIR visitVariableETIR(IVariableETIR variable) {
		return myArgv[variable.getLValue().accept(this).getValue()];
	}

	public IIntegerETIR visitSubscriptLValue(ISubscriptLValueTIR subscript) {
		return subscript.getIndex().accept(this);
	}

	// private Object something(Tree ast) {
	// switch (ast.getType()) {
	// case HobbesParser.MINUS:
	// int child = Integer.parseInt(interpret(ast.getChild(0)));
	// return String.valueOf(-child);
	// case HobbesParser.PLUS:
	// case HobbesParser.MULTIPLY:
	// int left = Integer.parseInt(interpret(ast.getChild(0)));
	// int right = Integer.parseInt(interpret(ast.getChild(1)));
	// switch (ast.getType()) {
	// case HobbesParser.PLUS:
	// return String.valueOf(left + right);
	// case HobbesParser.MULTIPLY:
	// return String.valueOf(left * right);
	// }
	// default:
	// throw new IllegalArgumentException(ast + " cannot be interpreted");
	// }
	// }

	public IntegerETIR visitArrayETIR(IArrayETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitAssignmentETIR(IAssignmentETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitBooleanETIR(IBooleanETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitBreakETIR(BreakETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitCallETIR(ICallETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitFieldAssignmentETIR(IFieldAssignmentTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitForETIR(IForETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitIfETIR(IIfETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitLambdaETIR(ILambdaETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitLetETIR(ILetETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitNilETIR(NilETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitRecordETIR(IRecordETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitSequenceETIR(ISequenceETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitStringETIR(IStringETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IntegerETIR visitWhileETIR(IWhileETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitFieldLValue(IFieldValueTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR visitSimpleLValue(ISimpleLValueTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

}
