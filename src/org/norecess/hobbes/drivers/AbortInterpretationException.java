package org.norecess.hobbes.drivers;

public class AbortInterpretationException extends RuntimeException {

	private static final long	serialVersionUID	= 3820852567425800207L;

	private final int			myStatus;

	public AbortInterpretationException(int status) {
		myStatus = status;
	}

	public int getStatus() {
		return myStatus;
	}

}
