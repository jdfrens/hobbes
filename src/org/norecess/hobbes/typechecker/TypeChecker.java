package org.norecess.hobbes.typechecker;

import java.util.List;
import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.IPosition;
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
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.lvalues.IFieldValueTIR;
import org.norecess.citkit.tir.lvalues.ISimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.ISubscriptLValueTIR;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.interpreter.IErrorHandler;
import org.norecess.hobbes.typechecker.operators.OperatorTypeChecker;

import com.google.inject.Inject;

public class TypeChecker implements ITypeChecker {

	private final IEnvironment<PrimitiveType>			myEnvironment;
	private final Map<IOperator, OperatorTypeChecker>	myOperatorTypeCheckers;
	private final ITypeChecker							myRecurser;
	private final IErrorHandler							myErrorHandler;

	@Inject
	public TypeChecker(IEnvironment<PrimitiveType> environment,
			IErrorHandler errorHandler,
			Map<IOperator, OperatorTypeChecker> operatorTypeCheckers) {
		myEnvironment = environment;
		myErrorHandler = errorHandler;
		myOperatorTypeCheckers = operatorTypeCheckers;
		myRecurser = this;
	}

	public TypeChecker(IEnvironment<PrimitiveType> environment,
			IErrorHandler errorHandler,
			Map<IOperator, OperatorTypeChecker> operatorTypeCheckers,
			ITypeChecker recurser) {
		myEnvironment = environment;
		myErrorHandler = errorHandler;
		myOperatorTypeCheckers = operatorTypeCheckers;
		myRecurser = recurser;
	}

	public PrimitiveType recurse(ExpressionTIR expression) {
		return expression.accept(this);
	}

	public PrimitiveType recurse(IEnvironment<PrimitiveType> newEnvironment,
			ExpressionTIR expression) {
		return expression.accept(new TypeChecker(newEnvironment,
				myErrorHandler, myOperatorTypeCheckers));
	}

	public IEnvironment<PrimitiveType> bind(
			IEnvironment<PrimitiveType> newEnvironment,
			List<? extends DeclarationTIR> declarations) {
		ITypeBinder binder = myRecurser.createBinder(newEnvironment);
		for (DeclarationTIR declaration : declarations) {
			declaration.accept(binder);
		}
		return newEnvironment;
	}

	public ITypeBinder createBinder(IEnvironment<PrimitiveType> environment) {
		return new TypeBinder(environment, myRecurser);
	}

	public PrimitiveType visitIntegerETIR(IIntegerETIR i) {
		return IntegerType.INTEGER_TYPE;
	}

	public PrimitiveType visitBooleanETIR(IBooleanETIR b) {
		return BooleanType.BOOLEAN_TYPE;
	}

	public PrimitiveType visitOperatorETIR(IOperatorETIR expression) {
		return visitInterpretedOperatorETIR(expression, expression
				.getOperator(), myRecurser.recurse(expression.getLeft()),
				myRecurser.recurse(expression.getRight()));
	}

	private PrimitiveType visitInterpretedOperatorETIR(
			IOperatorETIR expression, IOperator operator,
			PrimitiveType leftType, PrimitiveType rightType) {
		try {
			return myOperatorTypeCheckers.get(operator).check(leftType,
					rightType);
		} catch (OperatorTypeException e) {
			throw myErrorHandler.handleTypeError(expression, leftType,
					rightType);
		}
	}

	public PrimitiveType visitVariableETIR(IVariableETIR variable) {
		return variable.getLValue().accept(this);
	}

	public PrimitiveType visitSimpleLValue(ISimpleLValueTIR simple) {
		return myEnvironment.get(simple.getName());
	}

	public PrimitiveType visitSubscriptLValue(ISubscriptLValueTIR arg0) {
		return IntegerType.INTEGER_TYPE;
	}

	public PrimitiveType visitIfETIR(IIfETIR ife) {
		checkIfTest(ife.getPosition(), myRecurser.recurse(ife.getTest()));
		myRecurser.recurse(ife.getElseClause());
		return myRecurser.recurse(ife.getThenClause());
	}

	private void checkIfTest(IPosition position, PrimitiveType testType) {
		if (testType != BooleanType.BOOLEAN_TYPE) {
			throw new HobbesTypeException(position,
					"if test must be bool, not " + testType.toShortString());
		}
	}

	public PrimitiveType visitLetETIR(ILetETIR let) {
		IEnvironment<PrimitiveType> newEnvironment = myEnvironment.create();
		myRecurser.bind(newEnvironment, let.getDeclarations());
		return myRecurser.recurse(newEnvironment, let.getBody());
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

	public PrimitiveType visitLambdaETIR(ILambdaETIR arg0) {
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

	public PrimitiveType visitWhileETIR(IWhileETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public PrimitiveType visitFieldLValue(IFieldValueTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

}
