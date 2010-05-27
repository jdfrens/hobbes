package org.norecess.hobbes.compiler.resources;

import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;

public class ResourceAllocator implements IResourceAllocator {

	private int	myRegisterCounter;
	private int	myLabelCounter;

	public ResourceAllocator() {
		myRegisterCounter = 1;
		myLabelCounter = 1;
	}

	public IRegister nextRegister(HobbesType type) {
		if (needsNumberRegister(type)) {
			return new NumberRegister(myRegisterCounter++);
		} else if (needsIntegerRegister(type)) {
			return new IntegerRegister(myRegisterCounter++);
		} else {
			throw new IllegalArgumentException(type
					+ " cannot be allocated a register.");
		}
	}

	private boolean needsIntegerRegister(HobbesType type) {
		return (type == IntegerType.TYPE) || (type == BooleanType.TYPE);
	}

	private boolean needsNumberRegister(HobbesType type) {
		return (type == FloatingPointType.TYPE);
	}

	public ILabel nextLabel() {
		return new Label(myLabelCounter++);
	}

	public ICode createCode() {
		return new Code();
	}

}
