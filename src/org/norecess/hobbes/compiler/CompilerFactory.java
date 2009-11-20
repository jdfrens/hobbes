package org.norecess.hobbes.compiler;

import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.compiler.body.IPIRBodyVisitor;
import org.norecess.hobbes.compiler.body.PIRBodyCompiler;
import org.norecess.hobbes.compiler.body.PIRBodyVisitor;
import org.norecess.hobbes.compiler.operators.OperatorInstruction;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;
import org.norecess.hobbes.interpreter.IErrorHandler;
import org.norecess.hobbes.typechecker.ITypeChecker;
import org.norecess.hobbes.typechecker.TypeChecker;
import org.norecess.hobbes.typechecker.operators.OperatorTypeChecker;

import com.google.inject.Inject;

public class CompilerFactory implements ICompilerFactory {

	private final IResourceAllocator					myResourceAllocator;
	private final Map<IOperator, OperatorInstruction>	myOperatorInstructions;
	private final IEnvironment<IRegister>				myEnvironment;
	private final IEnvironment<PrimitiveType>			myTypeEnvironment;
	private final IErrorHandler							myErrorHandler;
	private final Map<IOperator, OperatorTypeChecker>	myOperatorTypeCheckers;

	@Inject
	public CompilerFactory(IResourceAllocator resourceAllocator,
			Map<IOperator, OperatorInstruction> operatorInstructions,
			IEnvironment<IRegister> environment,
			IEnvironment<PrimitiveType> typeEnvironment,
			IErrorHandler errorHandler,
			Map<IOperator, OperatorTypeChecker> operatorTypeCheckers) {
		myResourceAllocator = resourceAllocator;
		myOperatorInstructions = operatorInstructions;
		myEnvironment = environment;
		myTypeEnvironment = typeEnvironment;
		myErrorHandler = errorHandler;
		myOperatorTypeCheckers = operatorTypeCheckers;
	}

	public IPIRBodyVisitor createBodyVisitor() {
		return new PIRBodyVisitor(myResourceAllocator, myOperatorInstructions,
				myEnvironment, PIRBodyCompiler.ACC);
	}

	@Deprecated
	public ITypeChecker createTypeChecker() {
		return new TypeChecker(myTypeEnvironment, myErrorHandler,
				myOperatorTypeCheckers);
	}

}
