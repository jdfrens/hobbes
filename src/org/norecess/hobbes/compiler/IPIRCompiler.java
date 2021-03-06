package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.ICode;

public interface IPIRCompiler {

	public ICode compile(ExpressionTIR tir) throws IOException;

}
