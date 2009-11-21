package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.backend.ICode;

public interface IPIRCompiler {

	public ICode compile(HobbesType returnType, ExpressionTIR tir)
			throws IOException;

}
