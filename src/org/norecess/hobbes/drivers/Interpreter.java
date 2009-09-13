package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.frontend.HobbesFrontEnd;
import org.norecess.hobbes.interpreter.HobbesInterpreter;

public class Interpreter {

	private final HobbesFrontEnd	myFrontEnd;

	public Interpreter(HobbesFrontEnd frontEnd) {
		myFrontEnd = frontEnd;
	}

	public String interpret(String[] args) throws RecognitionException {
		return new HobbesInterpreter(args).interpret(myFrontEnd.process());
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		Interpreter interpreter = new Interpreter(new HobbesFrontEnd(new File(
				args[0])));
		System.out.println(interpreter.interpret(args));
	}

}
