package org.norecess.hobbes.typechecker;

import org.norecess.citkit.tir.IPosition;

public class HobbesTypeException extends RuntimeException {

	private static final long	serialVersionUID	= -6875606283247186133L;

	private IPosition			myPosition;

	public HobbesTypeException(String message) {
		super(message);
	}

	public HobbesTypeException(IPosition position, String message) {
		super(message);
		myPosition = position;
	}

	public void setPositionIfUnset(IPosition position) {
		if (myPosition == null) {
			myPosition = position;
		}
	}

	public IPosition getPosition() {
		return myPosition;
	}

}
