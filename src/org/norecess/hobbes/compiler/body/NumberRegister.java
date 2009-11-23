package org.norecess.hobbes.compiler.body;

import org.norecess.hobbes.compiler.resources.AbstractRegister;

public class NumberRegister extends AbstractRegister {

	public NumberRegister(int i) {
		super(i);
	}

	@Override
	public String toString() {
		return "$N" + myI;
	}

}
