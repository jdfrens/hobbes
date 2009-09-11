package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;

/*
 * This is the top-level compiler.  The component compiler does the hard work.
 */
public class HobbesPIRCompiler {

	private final IHobbesPIRComponentCompiler	myComponentCompiler;

	public HobbesPIRCompiler() {
		this(new HobbesPIRComponentCompiler(new RegisterAllocator()));
	}

	public HobbesPIRCompiler(IHobbesPIRComponentCompiler componentCompiler) {
		myComponentCompiler = componentCompiler;
	}

	public ICode compile(IHobbesFrontEnd frontEnd, ICode code)
			throws IOException, RecognitionException {
		Tree tree = frontEnd.process();
		myComponentCompiler.generateProlog(code, tree);
		myComponentCompiler.generateCode(code, tree);
		myComponentCompiler.generateEpilog(code);
		return code;
	}

}