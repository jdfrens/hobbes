package org.norecess.hobbes.drivers.injection;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.drivers.InterpreterCLI;

import com.google.inject.Guice;

public class InterpreterModuleTest {

	private InterpreterModule	myInterpreterModule;

	@Before
	public void setUp() {
		myInterpreterModule = new InterpreterModule();
	}

	@Test
	public void shouldGenerateInstanceOfCLI() {
		Guice.createInjector( //
				myInterpreterModule, //
				new FrontEndModule(new String[] { "foo", "bar" }), //
				new TypeCheckerModule() //
				).getInstance(InterpreterCLI.class);
	}
}
