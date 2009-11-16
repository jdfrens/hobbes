package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.body.IPIRBodyCompiler;
import org.norecess.hobbes.compiler.epilog.IPIREpilogCompiler;
import org.norecess.hobbes.compiler.prolog.IPIRPrologCompiler;

import com.google.inject.Inject;

/*
 * This is the top-level compiler.  The component compiler does the hard work.
 */
public class PIRCompiler implements IPIRCompiler {

	private final IPIRPrologCompiler	myPrologCompiler;
	private final IPIRBodyCompiler		myBodyCompiler;
	private final IPIREpilogCompiler	myEpilogCompiler;

	@Inject
	public PIRCompiler(IPIRPrologCompiler prologCompiler,
			IPIRBodyCompiler componentCompiler,
			IPIREpilogCompiler epilogCompiler) {
		myPrologCompiler = prologCompiler;
		myBodyCompiler = componentCompiler;
		myEpilogCompiler = epilogCompiler;
	}

	public ICode compile(ExpressionTIR tir) throws IOException,
			RecognitionException {
		PrimitiveType returnType = myBodyCompiler.typeCheck(tir);
		ICode code = new Code();
		code.append(myPrologCompiler.generateProlog(tir));
		code.append(myBodyCompiler.generate(tir));
		code.append(myBodyCompiler.generatePrint(returnType, tir));
		code.append(myEpilogCompiler.generate());
		return code;
	}

}
