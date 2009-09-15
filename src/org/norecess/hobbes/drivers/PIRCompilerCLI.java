package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.norecess.hobbes.backend.CodeWriter;
import org.norecess.hobbes.backend.PIRCleaner;
import org.norecess.hobbes.compiler.HobbesPIRBodyCompiler;
import org.norecess.hobbes.compiler.HobbesPIRCompiler;
import org.norecess.hobbes.compiler.HobbesPIRPrologCompiler;
import org.norecess.hobbes.compiler.RegisterAllocator;
import org.norecess.hobbes.frontend.HobbesFrontEnd;

/**
 * The main driver. Usage: java org.norecess.hobbes.drivers.PIRCompiler
 * infile.hob outfile.pir
 */
public class PIRCompilerCLI {

	private final HobbesFrontEnd	myFrontEnd;
	private final PrintWriter		myWriter;

	public PIRCompilerCLI(String inputName, PrintStream outputStream)
			throws IOException {
		this(new HobbesFrontEnd(new File(inputName)), new PrintWriter(
				outputStream));
	}

	public PIRCompilerCLI(HobbesFrontEnd frontEnd, PrintWriter writer) {
		myFrontEnd = frontEnd;
		myWriter = writer;
	}

	public void generateCode() throws IOException, RecognitionException {
		Tree tree = myFrontEnd.process();
		new CodeWriter(myWriter).writeCode(new PIRCleaner()
				.process(new HobbesPIRCompiler(new HobbesPIRPrologCompiler(),
						new HobbesPIRBodyCompiler(new RegisterAllocator()))
						.compile(myFrontEnd.process(tree))));
	}

	public void done() {
		myWriter.close();
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		PIRCompilerCLI compiler = new PIRCompilerCLI(args[0], System.out);
		compiler.generateCode();
		compiler.done();
	}

}
