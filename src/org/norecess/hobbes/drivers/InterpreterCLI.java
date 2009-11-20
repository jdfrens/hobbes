package org.norecess.hobbes.drivers;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.drivers.injection.ExternalSystemModule;
import org.norecess.hobbes.drivers.injection.FrontEndModule;
import org.norecess.hobbes.drivers.injection.InterpreterModule;
import org.norecess.hobbes.drivers.injection.TypeCheckerModule;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.ITranslatorSystem;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class InterpreterCLI {

	private final IExternalSystem	myExternalSystem;
	private final IHobbesFrontEnd	myFrontEnd;
	private final ITranslatorSystem	myTranslatorSystem;

	@Inject
	public InterpreterCLI(IExternalSystem externalSystem,
			IHobbesFrontEnd frontEnd, ITranslatorSystem system) {
		myExternalSystem = externalSystem;
		myFrontEnd = frontEnd;
		myTranslatorSystem = system;
	}

	public void doit(String[] args, PrintStream out, PrintStream err)
			throws IOException, RecognitionException {
		try {
			ExpressionTIR tir = myFrontEnd.process();
			HobbesType returnType = myTranslatorSystem.typeCheck(err, tir);
			myTranslatorSystem.evalAndPrint(out, returnType, tir);
			myExternalSystem.exit(CLIStatusCodes.STATUS_OK);
		} catch (AbortTranslatorException e) {
			myExternalSystem.exit(e.getStatus());
		}
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		InterpreterCLI interpreterCLI = Guice.createInjector(
				new ExternalSystemModule(), //
				new FrontEndModule(args), //
				new TypeCheckerModule(), //
				new InterpreterModule() //
				).getInstance(InterpreterCLI.class);
		interpreterCLI.doit(args, System.out, System.err);
	}

}
