package org.norecess.hobbes.drivers.injection;

import org.norecess.hobbes.drivers.system.ExternalSystem;
import org.norecess.hobbes.drivers.system.IExternalSystem;

import com.google.inject.AbstractModule;

public class ExternalSystemModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IExternalSystem.class).to(ExternalSystem.class);
	}

}
