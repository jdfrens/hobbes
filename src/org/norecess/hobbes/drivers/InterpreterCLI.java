package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.frontend.HobbesFrontEnd;
import org.norecess.hobbes.interpreter.HobbesInterpreter;

public class InterpreterCLI {

	private final HobbesFrontEnd	myFrontEnd;

	public InterpreterCLI(HobbesFrontEnd frontEnd) {
		myFrontEnd = frontEnd;
	}

	public IIntegerETIR interpret(IIntegerETIR[] args)
			throws RecognitionException {
		return new HobbesInterpreter(args).interpret(myFrontEnd
				.process(myFrontEnd.process()));
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		IIntegerETIR integerArgs[] = new IIntegerETIR[args.length];
		for (int i = 0; i < args.length; i++) {
			try {
				integerArgs[i] = new IntegerETIR(args[i]);
			} catch (NumberFormatException e) {
				integerArgs[i] = new IntegerETIR(-666);
			}
		}
		InterpreterCLI interpreter = new InterpreterCLI(new HobbesFrontEnd(
				new File(args[0])));
		System.out.println(interpreter.interpret(integerArgs));
	}

}
