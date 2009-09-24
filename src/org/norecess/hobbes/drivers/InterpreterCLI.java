package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.hobbes.frontend.HobbesFrontEnd;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.Interpreter;
import org.norecess.hobbes.interpreter.operators.ApplyingOperator;
import org.norecess.hobbes.interpreter.operators.OperatorInterpretersFactory;

public class InterpreterCLI {

	private final IHobbesFrontEnd	myFrontEnd;

	public InterpreterCLI(IHobbesFrontEnd frontEnd) {
		myFrontEnd = frontEnd;
	}

	public DatumTIR interpret(IIntegerETIR[] args,
			Map<Operator, ApplyingOperator> operatorInterpreters)
			throws RecognitionException {
		return new Interpreter(args, operatorInterpreters).interpret(myFrontEnd
				.process());
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		IIntegerETIR[] integerArgs = convertArgs(args);
		InterpreterCLI interpreter = new InterpreterCLI(new HobbesFrontEnd(
				new File(args[0])));
		System.out
				.println(interpreter.interpret(integerArgs,
						new OperatorInterpretersFactory()
								.createOperatorInterpreters()));
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
