package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.hobbes.frontend.HobbesFrontEnd;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.Interpreter;
import org.norecess.hobbes.interpreter.operators.AdditionOperator;
import org.norecess.hobbes.interpreter.operators.ApplyingOperator;
import org.norecess.hobbes.interpreter.operators.DivisionOperator;
import org.norecess.hobbes.interpreter.operators.ModulusOperator;
import org.norecess.hobbes.interpreter.operators.MultiplicationOperator;
import org.norecess.hobbes.interpreter.operators.SubtractionOperator;

import ovm.polyd.PolyD;

public class InterpreterCLI {

	private final IHobbesFrontEnd	myFrontEnd;

	public InterpreterCLI(IHobbesFrontEnd frontEnd) {
		myFrontEnd = frontEnd;
	}

	public DatumTIR interpret(IIntegerETIR[] args) throws RecognitionException {
		return new Interpreter(args, createOperatorInterpreters())
				.interpret(myFrontEnd.process());
	}

	private HashMap<Operator, ApplyingOperator> createOperatorInterpreters() {
		HashMap<Operator, ApplyingOperator> operatorInterpreters = new HashMap<Operator, ApplyingOperator>();
		operatorInterpreters.put(Operator.ADD, PolyD.build(
				ApplyingOperator.class, new AdditionOperator()));
		operatorInterpreters.put(Operator.SUBTRACT, PolyD.build(
				ApplyingOperator.class, new SubtractionOperator()));
		operatorInterpreters.put(Operator.MULTIPLY, PolyD.build(
				ApplyingOperator.class, new MultiplicationOperator()));
		operatorInterpreters.put(Operator.DIVIDE, PolyD.build(
				ApplyingOperator.class, new DivisionOperator()));
		operatorInterpreters.put(Operator.MODULUS, PolyD.build(
				ApplyingOperator.class, new ModulusOperator()));
		return operatorInterpreters;
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
