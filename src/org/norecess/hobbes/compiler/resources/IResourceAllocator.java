package org.norecess.hobbes.compiler.resources;

import org.norecess.hobbes.backend.ICode;

public interface IResourceAllocator {

	ICode code();

	IRegister nextRegister();

	ILabel nextLabel();

}
