package org.norecess.hobbes.compiler.resources;

import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.backend.ICode;

public interface IResourceAllocator {

	ICode createCode();

	IRegister nextRegister(HobbesType type);

	ILabel nextLabel();

}
