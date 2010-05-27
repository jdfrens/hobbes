package org.norecess.hobbes.compiler;

import org.norecess.hobbes.compiler.body.IPIRBodySubcompiler;
import org.norecess.hobbes.compiler.resources.IRegister;

public interface ICompilerFactory {

	public abstract IPIRBodySubcompiler createBodySubcompiler(IRegister register);

}
