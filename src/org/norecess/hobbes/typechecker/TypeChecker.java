package org.norecess.hobbes.typechecker;

import java.util.Arrays;
import java.util.List;

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
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;

public class TypeChecker implements ExpressionTIRVisitor<PrimitiveType> {

	private static final List<Operator>	ARITHMETIC_OPERATORS	= Arrays
																		.asList(new Operator[] {
			Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY,
			Operator.DIVIDE, Operator.MODULUS							});

	public PrimitiveType visitIntegerETIR(IIntegerETIR i) {
		return IntegerType.INTEGER_TYPE;
	}

	public PrimitiveType visitBooleanETIR(IBooleanETIR b) {
		return BooleanType.BOOLEAN_TYPE;
	}

	public PrimitiveType visitOperatorETIR(IOperatorETIR expression) {
		if (ARITHMETIC_OPERATORS.contains(expression.getOperator())) {
			return IntegerType.INTEGER_TYPE;
		} else {
			return BooleanType.BOOLEAN_TYPE;
		}
	}

	//
	// Unimplemented
	//
	public PrimitiveType visitArrayETIR(IArrayETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitAssignmentETIR(IAssignmentETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitBreakETIR(BreakETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitCallETIR(ICallETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitFieldAssignmentETIR(IFieldAssignmentTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitForETIR(IForETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitIfETIR(IIfETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitLambdaETIR(ILambdaETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitLetETIR(ILetETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitNilETIR(NilETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitRecordETIR(IRecordETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitSequenceETIR(ISequenceETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitStringETIR(IStringETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitVariableETIR(IVariableETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitWhileETIR(IWhileETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

}
