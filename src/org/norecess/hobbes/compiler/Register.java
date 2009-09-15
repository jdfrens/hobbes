package org.norecess.hobbes.compiler;

public class Register implements IRegister {

	private final int	myI;

	public Register(int i) {
		myI = i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + myI;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Register other = (Register) obj;
		if (myI != other.myI) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "$I" + myI;
	}

}
