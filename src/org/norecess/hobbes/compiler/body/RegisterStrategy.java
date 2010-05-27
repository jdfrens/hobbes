package org.norecess.hobbes.compiler.body;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class RegisterStrategy implements IRegisterStrategy {

	private final IResourceAllocator	myResourceAllocator;

	public RegisterStrategy(IResourceAllocator resourceAllocator) {
		myResourceAllocator = resourceAllocator;
	}

	public IRegister[] operatorRegisters(IRegister target, ExpressionTIR left,
			ExpressionTIR right) {
		if (target.isCompatible(left.getType())) {
			return new IRegister[] { target,
					myResourceAllocator.nextRegister(right.getType()) };
		} else {
			return new IRegister[] {
					myResourceAllocator.nextRegister(left.getType()),
					myResourceAllocator.nextRegister(right.getType()) };
		}
	}

}
