package org.norecess.hobbes.compiler.resources;

import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;

public class ResourceAllocator implements IResourceAllocator {

	private int	myRegisterCounter;
	private int	myLabelCounter;

	public ResourceAllocator() {
		myRegisterCounter = 0;
		myLabelCounter = 1;
	}

	public IRegister nextRegister() {
		return new Register(myRegisterCounter++);
	}

	public ILabel nextLabel() {
		return new Label(myLabelCounter++);
	}

	public ICode createCode() {
		return new Code();
	}

}
