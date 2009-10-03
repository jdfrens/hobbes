package org.norecess.hobbes.drivers;

import java.io.File;
import java.util.Map;

import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.hobbes.frontend.HobbesFrontEnd;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;
import org.norecess.hobbes.interpreter.operators.Appliable;
import org.norecess.hobbes.interpreter.operators.AppliableFactory;
import org.norecess.hobbes.output.HobbesOutput;
import org.norecess.hobbes.output.IHobbesOutput;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class InterpreterModule extends AbstractModule {

	private final String[]	myArgs;

	public InterpreterModule(String[] args) {
		myArgs = args;
	}

	@Override
	protected void configure() {
		bind(IHobbesOutput.class).to(HobbesOutput.class);
		bind(IHobbesFrontEnd.class).to(HobbesFrontEnd.class);
		bind(File.class).annotatedWith(Names.named("SourceFile")).toInstance(
				new File(myArgs[0]));
	}

	@Provides
	public Map<IOperator, Appliable> provideAppliables() {
		return new AppliableFactory().createAppliables();
	}

	@Provides
	public IIntegerETIR[] provideIntegerArguments() {
		return new ArgsToIntegers().convertArgs(myArgs);
	}
}
