package org.norecess.hobbes.drivers;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.hobbes.drivers.injection.FrontEndModule;
import org.norecess.hobbes.drivers.injection.InterpreterModule;
import org.norecess.hobbes.drivers.injection.TypeCheckerModule;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.IInterpreterSystem;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class InterpreterCLI {

	private final IHobbesFrontEnd		myFrontEnd;
	private final IInterpreterSystem	mySystem;

	@Inject
	public InterpreterCLI(IHobbesFrontEnd frontEnd, IInterpreterSystem system) {
		myFrontEnd = frontEnd;
		mySystem = system;
	}

	public void doit(PrintStream out, PrintStream err, String[] args)
			throws IOException, RecognitionException {
		ExpressionTIR tir = myFrontEnd.process();
		mySystem.typeCheck(err, tir);
		mySystem.evalAndPrint(out, tir);
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		try {
			InterpreterCLI interpreterCLI = Guice.createInjector(
					new FrontEndModule(args), //
					new TypeCheckerModule(), //
					new InterpreterModule() //
					).getInstance(InterpreterCLI.class);
			interpreterCLI.doit(System.out, System.err, args);
			System.exit(CLIStatusCodes.STATUS_OK);
		} catch (AbortInterpretationException e) {
			System.exit(e.getStatus());
		}
	}

}
