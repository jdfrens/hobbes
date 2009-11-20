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
		String[] args = new String[] { "foo", "bar" };
		Guice.createInjector(InterpreterCLI.createInjectorModules(args))
				.getInstance(InterpreterCLI.class);
	}
}
