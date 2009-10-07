package org.norecess.hobbes.drivers.injection;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.environment.IEnvironment;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.drivers.injection.PIRModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

public class PIRModuleTest {

	private Injector	myInjector;

	@Before
	public void setUp() {
		myInjector = Guice.createInjector(new PIRModule());
	}

	@Test
	public void shouldProduceIEnvironment() {
		myInjector.getInstance(new Key<IEnvironment<IRegister>>() {
		});
	}
}
