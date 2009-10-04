package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.ICode;

public interface IPIRCompiler {

	public abstract ICode compile(ExpressionTIR tir) throws IOException,
			RecognitionException;

}
