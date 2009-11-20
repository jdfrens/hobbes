package org.norecess.hobbes.drivers;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.backend.ICodeWriter;
import org.norecess.hobbes.backend.IPIRCleaner;
import org.norecess.hobbes.compiler.IPIRCompiler;
import org.norecess.hobbes.drivers.injection.FrontEndModule;
import org.norecess.hobbes.drivers.injection.PIRModule;
import org.norecess.hobbes.drivers.injection.TypeCheckerModule;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * The main driver. Usage: java org.norecess.hobbes.drivers.PIRCompiler
 * infile.hob outfile.pir
 */
public class PIRCompilerCLI {

	private final IHobbesFrontEnd	myFrontEnd;
	private final IPIRCompiler		myCompiler;
	private final IPIRCleaner		myPirCleaner;
	private final ICodeWriter		myCodeWriter;

	@Inject
	public PIRCompilerCLI(IHobbesFrontEnd frontEnd, IPIRCompiler compiler,
			IPIRCleaner cleaner, ICodeWriter codeWriter) {
		myFrontEnd = frontEnd;
		myPirCleaner = cleaner;
		myCodeWriter = codeWriter;
		myCompiler = compiler;
	}

	public void generateCode(String sourceFile, PrintStream out)
			throws IOException, RecognitionException {
		myCodeWriter.writeCode(myPirCleaner.process(myCompiler
				.compile(myFrontEnd.process())));
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		Guice.createInjector(new FrontEndModule(args), //
				new TypeCheckerModule(), //
				new PIRModule() //
				).getInstance(PIRCompilerCLI.class).generateCode(args[0],
						System.out);
	}

}
