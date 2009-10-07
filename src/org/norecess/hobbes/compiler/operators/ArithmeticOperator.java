package org.norecess.hobbes.compiler.operators;

import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class ArithmeticOperator implements OperatorInstruction {

	private final String	myOperator;

	public ArithmeticOperator(String operator) {
		myOperator = operator;
	}

	public ICode compile(IResourceAllocator resourceAllocator,
			IRegister target, IRegister temp) {
		ICode code = resourceAllocator.createCode();
		code.add(target, " " + myOperator + "= ", temp);
		return code;
	}

}
