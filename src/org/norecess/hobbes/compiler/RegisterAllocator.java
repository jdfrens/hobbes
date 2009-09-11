package org.norecess.hobbes.compiler;

public class RegisterAllocator implements IRegisterAllocator {

	private int	myCounter;

	public RegisterAllocator() {
		myCounter = 0;
	}

	public String next() {
		return "$I" + myCounter++;
	}

}
