package org.norecess.hobbes.drivers;

public class AbortTranslatorException extends RuntimeException {

	private static final long	serialVersionUID	= 3820852567425800207L;

	private final int			myStatus;

	public AbortTranslatorException(int status) {
		myStatus = status;
	}

	public int getStatus() {
		return myStatus;
	}

}
