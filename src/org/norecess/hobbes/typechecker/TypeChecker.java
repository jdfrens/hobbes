package org.norecess.hobbes.typechecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.IPosition;
import org.norecess.citkit.tir.LValueTIR;
import org.norecess.citkit.tir.declarations.IFunctionDTIR;
import org.norecess.citkit.tir.declarations.ITypeDTIR;
import org.norecess.citkit.tir.declarations.IVariableDTIR;
import org.norecess.citkit.tir.expressions.BreakETIR;
import org.norecess.citkit.tir.expressions.FloatingPointETIR;
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
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.tir.expressions.IRecordETIR;
import org.norecess.citkit.tir.expressions.ISequenceETIR;
import org.norecess.citkit.tir.expressions.IStringETIR;
import org.norecess.citkit.tir.expressions.IVariableETIR;
import org.norecess.citkit.tir.expressions.IWhileETIR;
import org.norecess.citkit.tir.expressions.NilETIR;
import org.norecess.citkit.tir.factories.IExpressionTIRFactory;
import org.norecess.citkit.tir.lvalues.IFieldValueTIR;
import org.norecess.citkit.tir.lvalues.ISimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.ISubscriptLValueTIR;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.interpreter.IErrorHandler;
import org.norecess.hobbes.typechecker.operators.OperatorTypeChecker;

import com.google.inject.Inject;

public class TypeChecker implements ITypeChecker {

	private final IEnvironment<HobbesType>				myEnvironment;
	private final IExpressionTIRFactory					myExpressionFactory;
	private final Map<IOperator, OperatorTypeChecker>	myOperatorTypeCheckers;
	private final ITypeChecker							myRecurser;
	private final IErrorHandler							myErrorHandler;

	@Inject
	public TypeChecker(IEnvironment<HobbesType> environment,
			IExpressionTIRFactory expressionFactory,
			IErrorHandler errorHandler,
			Map<IOperator, OperatorTypeChecker> operatorTypeCheckers) {
		myEnvironment = environment;
		myExpressionFactory = expressionFactory;
		myErrorHandler = errorHandler;
		myOperatorTypeCheckers = operatorTypeCheckers;
		myRecurser = this;
	}

	TypeChecker(IEnvironment<HobbesType> environment,
			IExpressionTIRFactory expressionFactory,
			IErrorHandler errorHandler,
			Map<IOperator, OperatorTypeChecker> operatorTypeCheckers,
			ITypeChecker recurser) {
		myEnvironment = environment;
		myExpressionFactory = expressionFactory;
		myErrorHandler = errorHandler;
		myOperatorTypeCheckers = operatorTypeCheckers;
		myRecurser = recurser;
	}

	public ExpressionTIR recurse(ExpressionTIR expression) {
		return expression.accept(this);
	}

	public ExpressionTIR recurse(IEnvironment<HobbesType> newEnvironment,
			ExpressionTIR expression) {
		return expression.accept(new TypeChecker(newEnvironment,
				myExpressionFactory, myErrorHandler, myOperatorTypeCheckers));
	}

	public List<DeclarationTIR> recurse(
			List<? extends DeclarationTIR> declarations) {
		List<DeclarationTIR> typedDeclarations = new ArrayList<DeclarationTIR>();
		for (DeclarationTIR declaration : declarations) {
			typedDeclarations.add(myRecurser.recurse(declaration));
		}
		return typedDeclarations;
	}

	public DeclarationTIR recurse(DeclarationTIR declaration) {
		return declaration.accept(myRecurser);
	}

	public IEnvironment<HobbesType> bind(
			List<? extends DeclarationTIR> declarations) {
		IEnvironment<HobbesType> newEnvironment = myEnvironment.create();
		ITypeBinder binder = myRecurser.createBinder(newEnvironment);
		for (DeclarationTIR declaration : declarations) {
			declaration.accept(binder);
		}
		return newEnvironment;
	}

	public ITypeBinder createBinder(IEnvironment<HobbesType> environment) {
		return new TypeBinder(environment);
	}

	public ExpressionTIR visitIntegerETIR(IIntegerETIR i) {
		return i;
	}

	public ExpressionTIR visitFloatingPointETIR(FloatingPointETIR x) {
		return x;
	}

	public ExpressionTIR visitBooleanETIR(IBooleanETIR b) {
		return b;
	}

	public ExpressionTIR visitOperatorETIR(IOperatorETIR expression) {
		return typecheck(expression, myRecurser.recurse(expression.getLeft()),
				myRecurser.recurse(expression.getRight()));
	}

	private ExpressionTIR typecheck(IOperatorETIR expression,
			ExpressionTIR typedLeft, ExpressionTIR typedRight) {
		try {
			return myExpressionFactory.buildOperatorETIR(
					expression.getPosition(),
					inferType(expression.getOperator(), typedRight.getType(),
							typedLeft.getType()), typedLeft, expression
							.getOperator(), typedRight);
		} catch (OperatorTypeException e) {
			throw myErrorHandler.handleTypeError(expression,
					typedLeft.getType(), typedRight.getType());
		}
	}

	private PrimitiveType inferType(IOperator operator, HobbesType rightType,
			HobbesType leftType) {
		return myOperatorTypeCheckers.get(operator).check(leftType, rightType);
	}

	public ExpressionTIR visitVariableETIR(IVariableETIR variable) {
		return myExpressionFactory.buildVariableETIR(variable.getPosition(),
				variable.getLValue().accept(this));
	}

	public ISimpleLValueTIR visitSimpleLValue(ISimpleLValueTIR simple) {
		return myExpressionFactory.buildSimpleLValueTIR(simple.getPosition(),
				myEnvironment.get(simple.getName()), simple.getName());
	}

	public LValueTIR visitSubscriptLValue(ISubscriptLValueTIR subscript) {
		ExpressionTIR typedIndex = myRecurser.recurse(subscript.getIndex());
		if (typedIndex.getType() != IntegerType.TYPE) {
			throw new HobbesTypeException(subscript.getPosition(),
					"array index must be an int.");
		}
		return myExpressionFactory.buildSubscriptLValueTIR(
				subscript.getPosition(), IntegerType.TYPE,
				subscript.getVariable(), typedIndex);
	}

	public ExpressionTIR visitIfETIR(IIfETIR ife) {
		ExpressionTIR typedTest = myRecurser.recurse(ife.getTest());
		checkTest(ife.getPosition(), typedTest.getType());
		ExpressionTIR typedConsequence = myRecurser
				.recurse(ife.getThenClause());
		ExpressionTIR typedOtherwise = myRecurser.recurse(ife.getElseClause());
		checkThenAndElse(ife.getPosition(), typedConsequence.getType(),
				typedOtherwise.getType());
		return myExpressionFactory.buildIfETIR(ife.getPosition(), typedTest,
				typedConsequence, typedOtherwise);
	}

	private void checkThenAndElse(IPosition position, HobbesType thenType,
			HobbesType elseType) {
		if (elseType != thenType) {
			throw new HobbesTypeException(position, "then clause is "
					+ thenType.toShortString() + ", else clause is "
					+ elseType.toShortString());
		}
	}

	private void checkTest(IPosition position, HobbesType testType) {
		if (testType != BooleanType.TYPE) {
			throw new HobbesTypeException(position,
					"if test must be bool, not " + testType.toShortString());
		}
	}

	public ExpressionTIR visitLetETIR(ILetETIR let) {
		List<DeclarationTIR> typedDeclarations = myRecurser.recurse(let
				.getDeclarations());
		ExpressionTIR typedBody = myRecurser.recurse(
				myRecurser.bind(typedDeclarations), let.getBody());
		return myExpressionFactory.buildLetETIR(let.getPosition(),
				typedDeclarations, typedBody);
	}

	public DeclarationTIR visitVariableDTIR(IVariableDTIR declaration) {
		return myExpressionFactory.buildVariableDTIR(declaration.getPosition(),
				declaration.getSymbol(),
				myRecurser.recurse(declaration.getInititialization()));
	}

	//
	// Unimplemented
	//
	public ExpressionTIR visitArrayETIR(IArrayETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitAssignmentETIR(IAssignmentETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitBreakETIR(BreakETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitCallETIR(ICallETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitFieldAssignmentETIR(IFieldAssignmentTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitForETIR(IForETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitLambdaETIR(ILambdaETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitNilETIR(NilETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitRecordETIR(IRecordETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitSequenceETIR(ISequenceETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitStringETIR(IStringETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ExpressionTIR visitWhileETIR(IWhileETIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public LValueTIR visitFieldLValue(IFieldValueTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public DeclarationTIR visitFunctionDTIR(IFunctionDTIR declaration) {
		throw new IllegalStateException("unimplemented!");
	}

	public DeclarationTIR visitTypeDTIR(ITypeDTIR declaration) {
		throw new IllegalStateException("unimplemented!");
	}

}
