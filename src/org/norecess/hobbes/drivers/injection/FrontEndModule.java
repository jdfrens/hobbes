package org.norecess.hobbes.drivers.injection;

import com.google.inject.AbstractModule;

public class FrontEndModule extends AbstractModule {

	private final String[]	myArgs;

	public FrontEndModule(String[] args) {
		myArgs = args;
	}

	@Override
	protected void configure() {
		throw new IllegalStateException("unimplemented!");
	}

}
