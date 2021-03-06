package org.norecess.hobbes.interpreter;

import java.io.IOException;
import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.output.IHobbesOutput;
import org.norecess.hobbes.translator.ITranslator;

import com.google.inject.Inject;

public class InterpreterAsTranslator implements ITranslator {

	private final IInterpreter	myInterpreter;
	private final IHobbesOutput	myHobbesOutput;

	@Inject
	public InterpreterAsTranslator(IInterpreter interpreter,
			IHobbesOutput hobbesOutput) {
		myInterpreter = interpreter;
		myHobbesOutput = hobbesOutput;
	}

	public void evalAndPrint(PrintStream out, ExpressionTIR expression)
			throws IOException {
		out.println(myHobbesOutput.asHobbesOutput(myInterpreter
				.interpret(expression)));
	}

}
