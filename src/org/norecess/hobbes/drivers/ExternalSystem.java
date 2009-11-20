package org.norecess.hobbes.drivers;


public class ExternalSystem implements IExternalSystem {

	public void exit(int status) {
		System.exit(status);
	}

}
