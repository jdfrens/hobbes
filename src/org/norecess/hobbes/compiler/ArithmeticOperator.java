package org.norecess.hobbes.compiler;

import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class ArithmeticOperator implements OperatorInstruction {

	private final String	myOperator;

	public ArithmeticOperator(IResourceAllocator resourceAllocator,
			String operator) {
		myOperator = operator;
	}

	public ICode compile(IRegister target, IRegister temp) {
		Code code = new Code();
		code.add(target, " " + myOperator + "= ", temp);
		return code;
	}

}
