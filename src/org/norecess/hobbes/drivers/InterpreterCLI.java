package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.frontend.HobbesFrontEnd;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.Interpreter;

public class InterpreterCLI {

	private final IHobbesFrontEnd	myFrontEnd;

	public InterpreterCLI(IHobbesFrontEnd frontEnd) {
		myFrontEnd = frontEnd;
	}

	public IIntegerETIR interpret(IIntegerETIR[] args)
			throws RecognitionException {
		return new Interpreter(args).interpret(myFrontEnd.process());
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		IIntegerETIR[] integerArgs = convertArgs(args);
		InterpreterCLI interpreter = new InterpreterCLI(new HobbesFrontEnd(
				new File(args[0])));
		System.out.println(interpreter.interpret(integerArgs));
	}

	private static IIntegerETIR[] convertArgs(String[] args) {
		IIntegerETIR integerArgs[] = new IIntegerETIR[args.length];
		for (int i = 0; i < args.length; i++) {
			try {
				integerArgs[i] = new IntegerETIR(args[i]);
			} catch (NumberFormatException e) {
				integerArgs[i] = new IntegerETIR(-666);
			}
		}
		return integerArgs;
	}

}
