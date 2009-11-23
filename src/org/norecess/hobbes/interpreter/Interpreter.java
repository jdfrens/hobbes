package org.norecess.hobbes.interpreter;

import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.data.DatumTIR;
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
import org.norecess.citkit.tir.lvalues.IFieldValueTIR;
import org.norecess.citkit.tir.lvalues.ISimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.ISubscriptLValueTIR;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.interpreter.operators.Appliable;

import com.google.inject.Inject;

public class Interpreter implements IInterpreter {

	private final IInterpreter				myRecursion;
	private final IIntegerETIR[]			myArgv;
	private final IEnvironment<DatumTIR>	myEnvironment;
	private final Map<IOperator, Appliable>	myAppliables;
	private final IErrorHandler				myErrorHandler;

	@Inject
	public Interpreter(IIntegerETIR[] argv, IEnvironment<DatumTIR> environment,
			Map<IOperator, Appliable> appliables, IErrorHandler errorHandler) {
		myRecursion = this;
		myArgv = argv;
		myEnvironment = environment;
		myAppliables = appliables;
		myErrorHandler = errorHandler;
	}

	public Interpreter(IInterpreter recursion, IIntegerETIR[] argv,
			IEnvironment<DatumTIR> environment,
			Map<IOperator, Appliable> appliables, IErrorHandler errorHandler) {
		myRecursion = recursion;
		myArgv = argv;
		myEnvironment = environment;
		myAppliables = appliables;
		myErrorHandler = errorHandler;
	}

	public DatumTIR interpret(ExpressionTIR expression) {
		return expression.accept(this);
	}

	public DatumTIR interpret(IEnvironment<DatumTIR> newEnvironment,
			ExpressionTIR expression) {
		return new Interpreter(myArgv, newEnvironment, myAppliables,
				myErrorHandler).interpret(expression);
	}

	public IIntegerETIR visitIntegerETIR(IIntegerETIR integer) {
		return integer;
	}

	public DatumTIR visitFloatingPointETIR(FloatingPointETIR floatingPoint) {
		return floatingPoint;
	}

	public IBooleanETIR visitBooleanETIR(IBooleanETIR bool) {
		return bool;
	}

	public DatumTIR visitOperatorETIR(IOperatorETIR expression) {
		return visitInterpretedOperatorETIR(expression, //
				expression.getOperator(), //
				expression.getLeft().accept(this), //
				expression.getRight().accept(this));
	}

	private DatumTIR visitInterpretedOperatorETIR(IOperatorETIR expression,
			IOperator operator, DatumTIR leftResult, DatumTIR rightResult) {
		return myAppliables.get(operator).apply(leftResult, rightResult);
	}

	public DatumTIR visitVariableETIR(IVariableETIR variable) {
		return variable.getLValue().accept(this);
	}

	public DatumTIR visitSubscriptLValue(ISubscriptLValueTIR subscript) {
		return myArgv[((IIntegerETIR) subscript.getIndex().accept(this))
				.getValue()];
	}

	public DatumTIR visitIfETIR(IIfETIR ife) {
		if (HobbesBoolean.TRUE.equals(ife.getTest().accept(this))) {
			return ife.getThenClause().accept(this);
		} else {
			return ife.getElseClause().accept(this);
		}
	}

	public DatumTIR visitLetETIR(ILetETIR let) {
		return myRecursion.interpret(
				myRecursion.createBinder().bind(let.getDeclarations()),
				let.getBody());
	}

	public IDeclarationBinder createBinder() {
		return new DeclarationBinder(myRecursion, myEnvironment.create());
	}

	public DatumTIR visitSimpleLValue(ISimpleLValueTIR simple) {
		return myEnvironment.get(simple.getName());
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

}
