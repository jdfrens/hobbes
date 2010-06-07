package org.norecess.hobbes.compiler.body.operators;

import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class LogicOperator implements OperatorInstruction {

	private final String	myOperator;

	public LogicOperator(String operator) {
		myOperator = operator;
	}

	public ICode compile(IResourceAllocator resourceAllocator,
			IRegister target, IRegister[] registers) {
		ICode code = resourceAllocator.createCode();
		code.add(target, " = ", myOperator, " ", registers[0], ", ",
				registers[1]);
		return code;
	}

}
