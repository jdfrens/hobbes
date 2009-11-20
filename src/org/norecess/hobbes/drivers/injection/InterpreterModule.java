package org.norecess.hobbes.drivers.injection;

import java.util.Map;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.environment.NullEnvironment;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IOperatorETIR.IOperator;
import org.norecess.hobbes.interpreter.ErrorHandler;
import org.norecess.hobbes.interpreter.IErrorHandler;
import org.norecess.hobbes.interpreter.IInterpreter;
import org.norecess.hobbes.interpreter.IInterpreterSystem;
import org.norecess.hobbes.interpreter.Interpreter;
import org.norecess.hobbes.interpreter.InterpreterSystem;
import org.norecess.hobbes.interpreter.operators.Appliable;
import org.norecess.hobbes.interpreter.operators.AppliableFactory;
import org.norecess.hobbes.output.HobbesOutput;
import org.norecess.hobbes.output.IHobbesOutput;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class InterpreterModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IHobbesOutput.class).to(HobbesOutput.class);
		bind(IErrorHandler.class).to(ErrorHandler.class);
		bind(IInterpreter.class).to(Interpreter.class);
		bind(IInterpreterSystem.class).to(InterpreterSystem.class);
	}

	@Provides
	public Map<IOperator, Appliable> provideAppliables() {
		return new AppliableFactory().createAppliables();
	}

	@Provides
	public IEnvironment<DatumTIR> provideInitialEnvironment() {
		return new NullEnvironment<DatumTIR>();
	}
}
