package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.frontend.HobbesFrontEnd;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.Interpreter;
import org.norecess.hobbes.interpreter.operators.Appliable;
import org.norecess.hobbes.interpreter.operators.AppliableFactory;

public class InterpreterCLI {

	private final IHobbesFrontEnd	myFrontEnd;

	public InterpreterCLI(IHobbesFrontEnd frontEnd) {
		myFrontEnd = frontEnd;
	}

	public DatumTIR interpret(IIntegerETIR[] args,
			Map<Operator, Appliable> appliables) throws RecognitionException {
		return new Interpreter(args, appliables)
				.interpret(myFrontEnd.process());
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		IIntegerETIR[] integerArgs = convertArgs(args);
		InterpreterCLI interpreter = new InterpreterCLI(new HobbesFrontEnd(
				new File(args[0])));
		System.out.println(toHobbesString(interpreter.interpret(integerArgs,
				new AppliableFactory().createAppliables())));
	}

	private static String toHobbesString(DatumTIR datum) {
		if (datum == HobbesBoolean.TRUE) {
			return "#t";
		} else if (datum == HobbesBoolean.FALSE) {
			return "#f";
		} else {
			return datum.toString();
		}
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
