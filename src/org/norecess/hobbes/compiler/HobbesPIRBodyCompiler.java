package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;

/*
 * A "component compiler" is a compiler that compiles only components of a program.
 * Like a single integer.
 */
public class HobbesPIRBodyCompiler implements IHobbesPIRBodyCompiler {

	private final IRegisterAllocator	myRegisterAllocator;

	public HobbesPIRBodyCompiler(IRegisterAllocator registerAllocator) {
		myRegisterAllocator = registerAllocator;
	}

	public ICode generate(ExpressionTIR tir) {
		IRegister target = myRegisterAllocator.next();
		ICode code = new Code();
		code.append(tir.accept(new HobbesPIRBodyVisitor(target)));
		code.add("print " + target);
		code.add("print \"\\n\"");
		code.add(".end");
		return code;
	}

}
