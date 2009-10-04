package org.norecess.hobbes.compiler;

import java.util.Map;

import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;
import org.norecess.hobbes.typechecker.ITypeChecker;
import org.norecess.hobbes.typechecker.TypeChecker;

import com.google.inject.Inject;

public class CompilerFactory implements ICompilerFactory {

	private final IResourceAllocator					myResourceAllocator;
	private final Map<Operator, OperatorInstruction>	myOperatorInstructions;

	@Inject
	public CompilerFactory(IResourceAllocator resourceAllocator,
			Map<Operator, OperatorInstruction> operatorInstructions) {
		myResourceAllocator = resourceAllocator;
		myOperatorInstructions = operatorInstructions;
	}

	public IPIRBodyVisitor createBodyVisitor() {
		return new PIRBodyVisitor(myResourceAllocator, myOperatorInstructions,
				PIRBodyCompiler.ACC);
	}

	public ITypeChecker createTypeChecker() {
		return new TypeChecker();
	}

}
