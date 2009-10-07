package org.norecess.hobbes.compiler;

import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.compiler.body.IPIRBodyVisitor;
import org.norecess.hobbes.compiler.body.PIRBodyCompiler;
import org.norecess.hobbes.compiler.body.PIRBodyVisitor;
import org.norecess.hobbes.compiler.operators.OperatorInstruction;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;
import org.norecess.hobbes.typechecker.ITypeChecker;
import org.norecess.hobbes.typechecker.TypeChecker;

import com.google.inject.Inject;

public class CompilerFactory implements ICompilerFactory {

	private final IResourceAllocator					myResourceAllocator;
	private final Map<Operator, OperatorInstruction>	myOperatorInstructions;
	private final IEnvironment<IRegister>				myEnvironment;
	private final IEnvironment<PrimitiveType>			myTypeEnvironment;

	@Inject
	public CompilerFactory(IResourceAllocator resourceAllocator,
			Map<Operator, OperatorInstruction> operatorInstructions,
			IEnvironment<IRegister> environment,
			IEnvironment<PrimitiveType> typeEnvironment) {
		myResourceAllocator = resourceAllocator;
		myOperatorInstructions = operatorInstructions;
		myEnvironment = environment;
		myTypeEnvironment = typeEnvironment;
	}

	public IPIRBodyVisitor createBodyVisitor() {
		return new PIRBodyVisitor(myResourceAllocator, myOperatorInstructions,
				myEnvironment, PIRBodyCompiler.ACC);
	}

	public ITypeChecker createTypeChecker() {
		return new TypeChecker(myTypeEnvironment);
	}

}
