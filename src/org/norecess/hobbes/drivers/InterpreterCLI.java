package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.frontend.HobbesFrontEnd;
import org.norecess.hobbes.interpreter.HobbesInterpreter;

public class InterpreterCLI {

	private final HobbesFrontEnd	myFrontEnd;

	public InterpreterCLI(HobbesFrontEnd frontEnd) {
		myFrontEnd = frontEnd;
	}

	public String interpret(String[] args) throws RecognitionException {
		return new HobbesInterpreter(args).interpret(myFrontEnd.process());
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		InterpreterCLI interpreter = new InterpreterCLI(new HobbesFrontEnd(new File(
				args[0])));
		System.out.println(interpreter.interpret(args));
	}

}
