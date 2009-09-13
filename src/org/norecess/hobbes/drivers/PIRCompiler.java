package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.backend.CodeWriter;
import org.norecess.hobbes.backend.PIRCleaner;
import org.norecess.hobbes.compiler.Code;
import org.norecess.hobbes.compiler.HobbesPIRCompiler;
import org.norecess.hobbes.frontend.HobbesFrontEnd;

/**
 * The main driver. Usage: java org.norecess.hobbes.drivers.PIRCompiler
 * infile.hob outfile.pir
 */
public class PIRCompiler {

	private final HobbesFrontEnd	myFrontEnd;
	private final PrintWriter		myWriter;

	public PIRCompiler(HobbesFrontEnd frontEnd, PrintWriter writer) {
		myFrontEnd = frontEnd;
		myWriter = writer;
	}

	public void init() {
	}

	public void generateCode() throws IOException, RecognitionException {
		new CodeWriter(myWriter).writeCode(new PIRCleaner()
				.process(new HobbesPIRCompiler()
						.compile(myFrontEnd, new Code())));
	}

	public void done() {
		myWriter.close();
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		PIRCompiler compiler = new PIRCompiler(new HobbesFrontEnd(new File(
				args[0])), new PrintWriter(System.out));
		compiler.init();
		compiler.generateCode();
		compiler.done();
	}

}
