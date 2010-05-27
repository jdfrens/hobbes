package org.norecess.hobbes.compiler.resources;

import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.types.IntegerType;

public class IntegerRegister extends AbstractRegister {

	public IntegerRegister(int i) {
		super(i);
	}

	@Override
	public String toString() {
		return "$I" + myI;
	}

	public boolean isCompatible(HobbesType type) {
		return (type == IntegerType.TYPE) || (type == BooleanType.TYPE);
	}

}
