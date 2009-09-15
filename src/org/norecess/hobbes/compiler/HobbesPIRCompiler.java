package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;

/*
 * This is the top-level compiler.  The component compiler does the hard work.
 */
public class HobbesPIRCompiler {

	private final IHobbesPIRBodyCompiler	myComponentCompiler;
	private final IHobbesPIRPrologCompiler	myPrologCompiler;

	public HobbesPIRCompiler(IHobbesPIRPrologCompiler prologCompiler,
			IHobbesPIRBodyCompiler componentCompiler) {
		myPrologCompiler = prologCompiler;
		myComponentCompiler = componentCompiler;
	}

	public ICode compile(ExpressionTIR tir) throws IOException,
			RecognitionException {
		ICode code = new Code();
		code.append(myPrologCompiler.generateProlog(tir));
		code.append(myComponentCompiler.generate(tir));
		return code;
	}

}
