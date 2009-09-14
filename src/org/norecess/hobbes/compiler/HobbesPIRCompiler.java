package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.norecess.citkit.tir.ExpressionTIR;

/*
 * This is the top-level compiler.  The component compiler does the hard work.
 */
public class HobbesPIRCompiler {

	private final IHobbesPIRBodyCompiler	myComponentCompiler;
	private final IHobbesPIRPrologCompiler		myPrologCompiler;

	// public HobbesPIRCompiler() {
	// this(new HobbesPIRComponentCompiler(new RegisterAllocator()));
	// }

	public HobbesPIRCompiler(IHobbesPIRPrologCompiler prologCompiler,
			IHobbesPIRBodyCompiler componentCompiler) {
		myPrologCompiler = prologCompiler;
		myComponentCompiler = componentCompiler;
	}

	public ICode compile(ExpressionTIR tir, Tree tree, ICode code)
			throws IOException, RecognitionException {
		myPrologCompiler.generateProlog(code, tir);
		myComponentCompiler.generate(code, tree);
		myComponentCompiler.generateEpilog(code);
		return code;
	}

}
