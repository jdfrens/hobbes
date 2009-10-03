package org.norecess.hobbes.drivers;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.Interpreter;
import org.norecess.hobbes.output.HobbesOutput;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class InterpreterCLI {

	private final HobbesOutput		myHobbesOutput;
	private final IHobbesFrontEnd	myFrontEnd;
	private final Interpreter		myInterpreter;

	@Inject
	public InterpreterCLI(HobbesOutput hobbesOutput, IHobbesFrontEnd frontEnd,
			Interpreter interpreter) {
		myHobbesOutput = hobbesOutput;
		myFrontEnd = frontEnd;
		myInterpreter = interpreter;
	}

	public void doit(String[] args) throws IOException, RecognitionException {
		System.out.println(myHobbesOutput.asHobbesOutput(myInterpreter
				.interpret(myFrontEnd.process())));
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		Guice.createInjector(new InterpreterModule(args)).getInstance(
				InterpreterCLI.class).doit(args);
	}

}
