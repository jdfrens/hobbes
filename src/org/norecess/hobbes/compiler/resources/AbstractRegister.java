package org.norecess.hobbes.compiler.resources;

public abstract class AbstractRegister implements IRegister {

	protected final int	myI;

	public AbstractRegister(int i) {
		myI = i;
	}

	@Override
	public int hashCode() {
		return 31 + myI;
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
		AbstractRegister other = (AbstractRegister) obj;
		if (myI != other.myI) {
			return false;
		}
		return true;
	}

}
