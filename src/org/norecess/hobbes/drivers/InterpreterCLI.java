package org.norecess.hobbes.drivers;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.drivers.injection.ExternalSystemModule;
import org.norecess.hobbes.drivers.injection.FrontEndModule;
import org.norecess.hobbes.drivers.injection.InterpreterModule;
import org.norecess.hobbes.drivers.injection.TypeCheckerModule;
import org.norecess.hobbes.translator.GenericTranslator;

import com.google.inject.Guice;
import com.google.inject.Module;

public class InterpreterCLI {

	public static void main(String[] args) throws IOException,
			RecognitionException {
		Guice.createInjector(createInjectorModules(args)). //
				getInstance(GenericTranslator.class). //
				translate(args, System.out, System.err);
	}

	public static Module[] createInjectorModules(String[] args) {
		return new Module[] { new ExternalSystemModule(), //
				new FrontEndModule(args), //
				new TypeCheckerModule(), //
				new InterpreterModule() };
	}

}
