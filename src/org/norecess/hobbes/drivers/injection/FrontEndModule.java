package org.norecess.hobbes.drivers.injection;

import java.io.File;

import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.factories.ExpressionTIRFactory;
import org.norecess.citkit.tir.factories.IExpressionTIRFactory;
import org.norecess.hobbes.drivers.ArgsToIntegers;
import org.norecess.hobbes.frontend.HobbesFrontEnd;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class FrontEndModule extends AbstractModule {

	private final String	myInputFilename;
	private final String[]	myArgs;

	public FrontEndModule(String[] args) {
		myInputFilename = args[0];
		myArgs = args;
	}

	@Override
	protected void configure() {
		bind(IHobbesFrontEnd.class).to(HobbesFrontEnd.class);
		bind(File.class).annotatedWith(Names.named("SourceFile")).toInstance(
				new File(myInputFilename));
	}

	@Provides
	public IIntegerETIR[] provideIntegerArguments() {
		return new ArgsToIntegers().convertArgs(myArgs);
	}

	@Provides
	public IExpressionTIRFactory provideExpressionTIRFactory() {
		return new ExpressionTIRFactory();
	}
}
