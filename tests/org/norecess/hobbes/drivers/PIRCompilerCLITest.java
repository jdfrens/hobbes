package org.norecess.hobbes.drivers;

import org.junit.Test;

import com.google.inject.Guice;

public class PIRCompilerCLITest {

	@Test
	public void shouldProduceCLI() {
		String[] args = new String[] { "foo", "bar" };
		Guice.createInjector(PIRCompilerCLI.generateInjectorModules(args))
				.getInstance(PIRCompilerCLI.class);
	}

}
