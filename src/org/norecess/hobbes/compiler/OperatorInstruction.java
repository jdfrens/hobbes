package org.norecess.hobbes.compiler;

import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;

public interface OperatorInstruction {

	ICode compile(IRegister target, IRegister temp);

}
