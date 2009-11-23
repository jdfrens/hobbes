package org.norecess.hobbes.compiler;

import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.hobbes.compiler.body.IPIRBodyVisitor;
import org.norecess.hobbes.compiler.body.PIRBodyVisitor;
import org.norecess.hobbes.compiler.operators.OperatorInstruction;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

import com.google.inject.Inject;

public class CompilerFactory implements ICompilerFactory {

	private final IResourceAllocator					myResourceAllocator;
	private final Map<IOperator, OperatorInstruction>	myOperatorInstructions;
	private final IEnvironment<IRegister>				myEnvironment;

	@Inject
	public CompilerFactory(IResourceAllocator resourceAllocator,
			Map<IOperator, OperatorInstruction> operatorInstructions,
			IEnvironment<IRegister> environment) {
		myResourceAllocator = resourceAllocator;
		myOperatorInstructions = operatorInstructions;
		myEnvironment = environment;
	}

	public IPIRBodyVisitor createBodyVisitor(IRegister target) {
		return new PIRBodyVisitor(myResourceAllocator, myOperatorInstructions,
				myEnvironment, target);
	}

}
