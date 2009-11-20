package org.norecess.hobbes.interpreter;

import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.drivers.AbortTranslatorException;
import org.norecess.hobbes.drivers.CLIStatusCodes;
import org.norecess.hobbes.output.IHobbesOutput;
import org.norecess.hobbes.translator.ITranslatorSystem;
import org.norecess.hobbes.typechecker.HobbesTypeException;
import org.norecess.hobbes.typechecker.ITypeChecker;

import com.google.inject.Inject;

@Deprecated
public class InterpreterSystem implements ITranslatorSystem {

	private final ITypeChecker	myTypeChecker;
	private final IInterpreter	myInterpreter;
	private final IHobbesOutput	myHobbesOutput;

	@Inject
	public InterpreterSystem(ITypeChecker typeChecker,
			IInterpreter interpreter, IHobbesOutput hobbesOutput) {
		myTypeChecker = typeChecker;
		myInterpreter = interpreter;
		myHobbesOutput = hobbesOutput;
	}

	@Deprecated
	public void evalAndPrint(PrintStream out, HobbesType returnType,
			ExpressionTIR expression) {
		out.println(myHobbesOutput.asHobbesOutput(myInterpreter
				.interpret(expression)));
	}

	@Deprecated
	public HobbesType typeCheck(PrintStream err, ExpressionTIR expression) {
		try {
			return expression.accept(myTypeChecker);
		} catch (HobbesTypeException e) {
			err.println("Error on line " + e.getPosition().getPosition() + ": "
					+ e.getMessage());
			throw new AbortTranslatorException(CLIStatusCodes.STATUS_TYPE_ERROR);
		}
	}

}
