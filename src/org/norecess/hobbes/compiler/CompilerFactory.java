package org.norecess.hobbes.compiler;

import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.hobbes.compiler.body.IPIRBodySubcompiler;
import org.norecess.hobbes.compiler.body.IRegisterStrategy;
import org.norecess.hobbes.compiler.body.PIRBodySubcompiler;
import org.norecess.hobbes.compiler.body.operators.OperatorInstruction;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

import com.google.inject.Inject;

public class CompilerFactory implements ICompilerFactory {

	private final IResourceAllocator					myResourceAllocator;
	private final Map<IOperator, OperatorInstruction>	myOperatorInstructions;
	private final IRegisterStrategy						myRegisterStrategy;
	private final IEnvironment<IRegister>				myEnvironment;

	@Inject
	public CompilerFactory(IResourceAllocator resourceAllocator,
			Map<IOperator, OperatorInstruction> operatorInstructions,
			IRegisterStrategy registerStrategy,
			IEnvironment<IRegister> environment) {
		myResourceAllocator = resourceAllocator;
		myOperatorInstructions = operatorInstructions;
		myRegisterStrategy = registerStrategy;
		myEnvironment = environment;
	}

	public IPIRBodySubcompiler createBodySubcompiler(IRegister target) {
		return new PIRBodySubcompiler(myResourceAllocator,
				myOperatorInstructions, myRegisterStrategy, myEnvironment,
				target);
	}

}
