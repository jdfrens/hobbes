package org.norecess.hobbes.compiler.body.operators;

import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public interface OperatorInstruction {

	ICode compile(IResourceAllocator resourceAllocator, IRegister target,
			IRegister[] registers);

}
