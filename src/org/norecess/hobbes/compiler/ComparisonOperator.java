package org.norecess.hobbes.compiler;

import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class ComparisonOperator implements OperatorInstruction {

	private final String	myOperator;

	public ComparisonOperator(String operator) {
		myOperator = operator;
	}

	public ICode compile(IResourceAllocator resourceAllocator,
			IRegister target, IRegister temp) {
		ICode code = resourceAllocator.createCode();
		code.add(target, " = ", target, " ", myOperator, " ", temp);
		return code;
	}
}
