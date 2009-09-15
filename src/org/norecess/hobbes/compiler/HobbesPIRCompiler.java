package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.ExpressionTIR;

/*
 * This is the top-level compiler.  The component compiler does the hard work.
 */
public class HobbesPIRCompiler {

	private final IHobbesPIRBodyCompiler	myComponentCompiler;
	private final IHobbesPIRPrologCompiler	myPrologCompiler;

	// public HobbesPIRCompiler() {
	// this(new HobbesPIRComponentCompiler(new RegisterAllocator()));
	// }

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
