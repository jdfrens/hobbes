package org.norecess.hobbes.compiler.body.operators;

import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class ArithmeticOperator implements OperatorInstruction {

	private final String	myOperator;

	public ArithmeticOperator(String operator) {
		myOperator = operator;
	}

	public ICode compile(IResourceAllocator resourceAllocator,
			IRegister target, IRegister[] registers) {
		ICode code = resourceAllocator.createCode();
		if (target.equals(registers[0])) {
			code.add(target, " " + myOperator + "= ", registers[1]);
		} else {
			code.add(target, " = " + registers[0] + " " + myOperator + " ",
					registers[1]);
		}
		return code;
	}
}
