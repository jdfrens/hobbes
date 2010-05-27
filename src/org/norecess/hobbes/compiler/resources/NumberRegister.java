package org.norecess.hobbes.compiler.resources;

import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.HobbesType;

public class NumberRegister extends AbstractRegister {

	public NumberRegister(int i) {
		super(i);
	}

	@Override
	public String toString() {
		return "$N" + myI;
	}

	public boolean isCompatible(HobbesType type) {
		return (type == FloatingPointType.TYPE);
	}

}
