package org.norecess.hobbes.drivers.injection;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.drivers.InterpreterCLI;
import org.norecess.hobbes.interpreter.IInterpreter;

import com.google.inject.Guice;

public class InterpreterModuleTest {

	private InterpreterModule	myInterpreterModule;

	@Before
	public void setUp() {
		myInterpreterModule = new InterpreterModule(
				new String[] { "foo", "bar" });
	}

	@Test
	public void shouldGenerateInstance() {
		Guice.createInjector(myInterpreterModule).getInstance(
				IInterpreter.class);
	}

	@Test
	public void shouldGenerateInstanceOfCLI() {
		Guice.createInjector(myInterpreterModule).getInstance(
				InterpreterCLI.class);
	}
}
