package org.norecess.hobbes.compiler.resources;

public class AbstractRegister implements IRegister {

	protected final int	myI;

	public AbstractRegister(int i) {
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
		IntegerRegister other = (IntegerRegister) obj;
		if (myI != other.myI) {
			return false;
		}
		return true;
	}

}
