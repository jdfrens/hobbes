package org.norecess.hobbes.translator;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.drivers.AbortTranslatorException;
import org.norecess.hobbes.drivers.StatusCodes;
import org.norecess.hobbes.drivers.system.IExternalSystem;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.typechecker.ITopLevelTypeChecker;

import com.google.inject.Inject;

public class GenericTranslator {

	private final IExternalSystem		myExternalSystem;
	private final IHobbesFrontEnd		myFrontEnd;
	private final ITopLevelTypeChecker	myTypeChecker;
	private final ITranslator			myTranslator;

	@Inject
	public GenericTranslator(IExternalSystem externalSystem,
			IHobbesFrontEnd frontEnd, ITopLevelTypeChecker typeChecker,
			ITranslator translator) {
		myExternalSystem = externalSystem;
		myFrontEnd = frontEnd;
		myTypeChecker = typeChecker;
		myTranslator = translator;
	}

	public void translate(String[] args, PrintStream out, PrintStream err)
			throws IOException, RecognitionException {
		try {
			ExpressionTIR typedExpression = myTypeChecker.typeCheck(err,
					myFrontEnd.process());
			myTranslator.evalAndPrint(out, typedExpression);
			myExternalSystem.exit(StatusCodes.STATUS_OK);
		} catch (AbortTranslatorException e) {
			myExternalSystem.exit(e.getStatus());
		}
	}

}
