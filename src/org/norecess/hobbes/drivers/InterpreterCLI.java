package org.norecess.hobbes.drivers;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.drivers.injection.InterpreterModule;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.IInterpreter;
import org.norecess.hobbes.output.IHobbesOutput;
import org.norecess.hobbes.typechecker.HobbesTypeException;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class InterpreterCLI {

	public static final int			STATUS_OK			= 0;
	public static final int			STATUS_TYPE_ERROR	= 1;

	private final IHobbesOutput		myHobbesOutput;
	private final IHobbesFrontEnd	myFrontEnd;
	private final IInterpreter		myInterpreter;

	@Inject
	public InterpreterCLI(IHobbesOutput hobbesOutput, IHobbesFrontEnd frontEnd,
			IInterpreter interpreter) {
		myHobbesOutput = hobbesOutput;
		myFrontEnd = frontEnd;
		myInterpreter = interpreter;
	}

	public int doit(PrintStream out, PrintStream err, String[] args)
			throws IOException, RecognitionException {
		try {
			out.println(myHobbesOutput.asHobbesOutput(myInterpreter
					.interpret(myFrontEnd.process())));
			return STATUS_OK;
		} catch (HobbesTypeException e) {
			err.println("Error on line " + e.getPosition().getPosition() + ": "
					+ e.getMessage() + " is not defined");
			return STATUS_TYPE_ERROR;
		}
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		System.exit(Guice.createInjector(new InterpreterModule(args))
				.getInstance(InterpreterCLI.class).doit(System.out, System.err,
						args));
	}

}
