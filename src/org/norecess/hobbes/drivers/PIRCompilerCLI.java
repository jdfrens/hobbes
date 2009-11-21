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
import org.norecess.hobbes.drivers.system.IExternalSystem;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.typechecker.ITopLevelTypeChecker;

import com.google.inject.Guice;
import com.google.inject.Module;

/**
 * The main driver. Usage: java org.norecess.hobbes.drivers.PIRCompiler
 * infile.hob outfile.pir
 */
public class PIRCompilerCLI {

	private IExternalSystem			myExternalSystem;
	private IHobbesFrontEnd			myFrontEnd;
	private ITopLevelTypeChecker	myTopLevelTypeChecker;

	private IPIRCompiler			myCompiler;
	private IPIRCleaner				myPirCleaner;
	private ICodeWriter				myCodeWriter;

	public void generateCode(String sourceFile, PrintStream out)
			throws IOException, RecognitionException {
		try {
			ExpressionTIR expression = myFrontEnd.process();
			HobbesType returnType = myTopLevelTypeChecker.typeCheck(System.err,
					expression);
			myCodeWriter.writeCode(out, myPirCleaner.process(myCompiler
					.compile(returnType, expression)));
		} catch (AbortTranslatorException e) {
			myExternalSystem.exit(e.getStatus());
		}
	}

	public static void main(String[] args) throws IOException,
			RecognitionException {
		Guice.createInjector(generateInjectorModules(args)).getInstance(
				InterpreterCLI.class).doit(args, System.out, System.err);
	}

	public static Module[] generateInjectorModules(String[] args) {
		return new Module[] { new ExternalSystemModule(), //
				new FrontEndModule(args), //
				new TypeCheckerModule(), //
				new PIRModule() };
	}
}
