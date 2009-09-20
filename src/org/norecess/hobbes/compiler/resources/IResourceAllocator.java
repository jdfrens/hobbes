package org.norecess.hobbes.compiler.resources;

import org.norecess.hobbes.backend.ICode;

public interface IResourceAllocator {

	ICode createCode();

	IRegister nextRegister();

	ILabel nextLabel();

}
