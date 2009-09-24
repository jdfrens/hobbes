package org.norecess.hobbes.compiler;

import java.util.Map;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

/*
 * A "component compiler" is a compiler that compiles only components of a program.
 * Like a single integer.
 */
public class PIRBodyCompiler implements IPIRBodyCompiler {

	private final IResourceAllocator					myResourceAllocator;
	private final Map<Operator, OperatorInstruction>	myOperatorInstructions;

	public PIRBodyCompiler(IResourceAllocator resourceAllocator,
			Map<Operator, OperatorInstruction> operatorInstructions) {
		myResourceAllocator = resourceAllocator;
		myOperatorInstructions = operatorInstructions;
	}

	public ICode generate(ExpressionTIR tir) {
		IRegister target = myResourceAllocator.nextRegister();
		ICode code = new Code();
		code.append(tir.accept(new PIRBodyVisitor(myResourceAllocator,
				myOperatorInstructions, target)));
		code.add("print " + target);
		code.add("print \"\\n\"");
		code.add(".end");
		return code;
	}

}
