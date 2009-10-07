package org.norecess.hobbes.drivers;

import org.junit.Test;
import org.norecess.hobbes.interpreter.IInterpreter;

import com.google.inject.Guice;

public class InterpreterModuleTest {

	@Test
	public void shouldGenerateInstance() {
		Guice.createInjector(
				new InterpreterModule(new String[] { "foo", "bar" }))
				.getInstance(IInterpreter.class);
	}

}
