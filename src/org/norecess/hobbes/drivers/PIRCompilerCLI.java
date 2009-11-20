package org.norecess.hobbes.drivers;

import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.backend.ICodeWriter;
import org.norecess.hobbes.backend.IPIRCleaner;
import org.norecess.hobbes.compiler.IPIRCompiler;
import org.norecess.hobbes.drivers.injection.ExternalSystemModule;
import org.norecess.hobbes.drivers.injection.FrontEndModule;
import org.norecess.hobbes.drivers.injection.PIRModule;
import org.norecess.hobbes.drivers.injection.TypeCheckerModule;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.typechecker.ITopLevelTypeChecker;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

/**
 * The main driver. Usage: java org.norecess.hobbes.drivers.PIRCompiler
 * infile.hob outfile.pir
 */
public class PIRCompilerCLI {

	private final IExternalSystem		myExternalSystem;
	private final IHobbesFrontEnd		myFrontEnd;
	private final ITopLevelTypeChecker	myTopLevelTypeChecker;
	private final IPIRCompiler			myCompiler;
	private final IPIRCleaner			myPirCleaner;
	private final ICodeWriter			myCodeWriter;

	@Inject
	public PIRCompilerCLI(IExternalSystem externalSystem,
			IHobbesFrontEnd frontEnd, ITopLevelTypeChecker topLevelTypeChecker,
			IPIRCompiler compiler, IPIRCleaner cleaner, ICodeWriter codeWriter) {
		myExternalSystem = externalSystem;
		myFrontEnd = frontEnd;
		myTopLevelTypeChecker = topLevelTypeChecker;
		myPirCleaner = cleaner;
		myCodeWriter = codeWriter;
		myCompiler = compiler;
	}

	public void generateCode(String sourceFile, PrintStream out)
			throws IOException, RecognitionException {
		try {
			ExpressionTIR expression = myFrontEnd.process();
			HobbesType returnType = myTopLevelTypeChecker.typeCheck(System.err,
					expression);
			myCodeWriter.writeCode(myPirCleaner.process(myCompiler.compile(
					returnType, expression)));
		} catch (AbortTranslatorException e) {
			myExternalSystem.exit(e.getStatus());
		}
	}

	// ExpressionTIR tir = myFrontEnd.process();
	// HobbesType returnType = myTranslatorSystem.typeCheck(err, tir);
	// myTranslatorSystem.evalAndPrint(out, returnType, tir);
	// myExternalSystem.exit(CLIStatusCodes.STATUS_OK);

	public static void main(String[] args) throws IOException,
			RecognitionException {
		Guice.createInjector(generateInjectorModules(args)).getInstance(
				PIRCompilerCLI.class).generateCode(args[0], System.out);
	}

	public static Module[] generateInjectorModules(String[] args) {
		return new Module[] { new ExternalSystemModule(), //
				new FrontEndModule(args), //
				new TypeCheckerModule(), //
				new PIRModule() };
	}
}
