package org.norecess.hobbes.compiler;

import org.norecess.hobbes.backend.ICode;

public interface IResourceAllocator {

	ICode code();

	IRegister nextRegister();

	ILabel nextLabel();

}
