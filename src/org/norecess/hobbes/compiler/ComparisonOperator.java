package org.norecess.hobbes.compiler;

import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class ComparisonOperator implements OperatorInstruction {

	private final IResourceAllocator	myResourceAllocator;
	private final String				myOperator;

	public ComparisonOperator(IResourceAllocator resourceAllocator,
			String operator) {
		myResourceAllocator = resourceAllocator;
		myOperator = operator;
	}

	public ICode compile(IRegister target, IRegister temp) {
		ICode code = myResourceAllocator.createCode();
		code.add(target, " = ", target, " ", myOperator, " ", temp);
		return code;
	}
}
