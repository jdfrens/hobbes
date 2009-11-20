package org.norecess.hobbes.drivers.injection;

import com.google.inject.AbstractModule;

public class TypeCheckerModule extends AbstractModule {

	@Override
	protected void configure() {
		throw new IllegalStateException("unimplemented!");
	}

}
