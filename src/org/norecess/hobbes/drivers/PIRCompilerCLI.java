package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.backend.CodeWriter;
import org.norecess.hobbes.backend.PIRCleaner;
import org.norecess.hobbes.compiler.CompilerFactory;
import org.norecess.hobbes.compiler.OperatorInstructionsFactory;
import org.norecess.hobbes.compiler.PIRBodyCompiler;
import org.norecess.hobbes.compiler.PIRCompiler;
import org.norecess.hobbes.compiler.PIREpilogCompiler;
import org.norecess.hobbes.compiler.PIRPrologCompiler;
import org.norecess.hobbes.compiler.resources.ResourceAllocator;
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
		ResourceAllocator resourceAllocator = new ResourceAllocator();
		new CodeWriter(myWriter)
				.writeCode(new PIRCleaner().process(new PIRCompiler(
						new PIRPrologCompiler(), new PIRBodyCompiler(
								new CompilerFactory(resourceAllocator,
										new OperatorInstructionsFactory()
												.create())),
						new PIREpilogCompiler()).compile(myFrontEnd.process())));
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
