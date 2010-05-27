package org.norecess.hobbes.compiler.body;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.compiler.resources.IRegister;

public interface IRegisterStrategy {

	IRegister[] operatorRegisters(IRegister target, ExpressionTIR left,
			ExpressionTIR right);

}
