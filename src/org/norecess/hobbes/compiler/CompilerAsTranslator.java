package org.norecess.hobbes.compiler;

import java.io.IOException;
import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.backend.ICodeWriter;
import org.norecess.hobbes.backend.IPIRCleaner;
import org.norecess.hobbes.translator.ITranslator;

import com.google.inject.Inject;

public class CompilerAsTranslator implements ITranslator {

	private final IPIRCompiler	myCompiler;
	private final IPIRCleaner	myPirCleaner;
	private final ICodeWriter	myCodeWriter;

	@Inject
	public CompilerAsTranslator(IPIRCompiler compiler, IPIRCleaner pirCleaner,
			ICodeWriter codeWriter) {
		myCompiler = compiler;
		myPirCleaner = pirCleaner;
		myCodeWriter = codeWriter;
	}

	public void evalAndPrint(PrintStream out, ExpressionTIR expression)
			throws IOException {
		myCodeWriter.writeCode(out,
				myPirCleaner.process(myCompiler.compile(expression)));
	}

}
