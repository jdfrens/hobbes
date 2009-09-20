package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

/*
 * A "component compiler" is a compiler that compiles only components of a program.
 * Like a single integer.
 */
public class PIRBodyCompiler implements IPIRBodyCompiler {

	private final IResourceAllocator	myRegisterAllocator;

	public PIRBodyCompiler(IResourceAllocator registerAllocator) {
		myRegisterAllocator = registerAllocator;
	}

	public ICode generate(ExpressionTIR tir) {
		IRegister target = myRegisterAllocator.nextRegister();
		ICode code = new Code();
		code
				.append(tir.accept(new PIRBodyVisitor(myRegisterAllocator,
						target)));
		code.add("print " + target);
		code.add("print \"\\n\"");
		code.add(".end");
		return code;
	}

}
