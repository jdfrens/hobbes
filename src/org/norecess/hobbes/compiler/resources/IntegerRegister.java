package org.norecess.hobbes.compiler.resources;

public class IntegerRegister extends AbstractRegister {

	public IntegerRegister(int i) {
		super(i);
	}

	@Override
	public String toString() {
		return "$I" + myI;
	}

}
