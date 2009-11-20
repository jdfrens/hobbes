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
import org.norecess.hobbes.translator.ITranslator;
import org.norecess.hobbes.typechecker.ITopLevelTypeChecker;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

public class InterpreterCLI {

	private final IExternalSystem		myExternalSystem;
	private final IHobbesFrontEnd		myFrontEnd;
	private final ITopLevelTypeChecker	myTypeChecker;
	private final ITranslator			myTranslator;

	@Inject
	public InterpreterCLI(IExternalSystem externalSystem,
			IHobbesFrontEnd frontEnd, ITopLevelTypeChecker typeChecker,
			ITranslator translator) {
		myExternalSystem = externalSystem;
		myFrontEnd = frontEnd;
		myTypeChecker = typeChecker;
		myTranslator = translator;
	}

	public void doit(String[] args, PrintStream out, PrintStream err)
			throws IOException, RecognitionException {
		try {
			ExpressionTIR expression = myFrontEnd.process();
			HobbesType returnType = myTypeChecker.typeCheck(err, expression);
			myTranslator.evalAndPrint(out, returnType, expression);
			myExternalSystem.exit(CLIStatusCodes.STATUS_OK);
		} catch (AbortTranslatorException e) {
			myExternalSystem.exit(e.getStatus());
		}
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		Guice.createInjector(createInjectorModules(args)). //
				getInstance(InterpreterCLI.class). //
				doit(args, System.out, System.err);
	}

	public static Module[] createInjectorModules(String[] args) {
		return new Module[] { new ExternalSystemModule(), //
				new FrontEndModule(args), //
				new TypeCheckerModule(), //
				new InterpreterModule() };
	}

}
