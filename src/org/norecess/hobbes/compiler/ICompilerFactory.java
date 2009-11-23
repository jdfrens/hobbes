package org.norecess.hobbes.compiler;

import org.norecess.hobbes.compiler.body.IPIRBodyVisitor;
import org.norecess.hobbes.compiler.resources.IRegister;

public interface ICompilerFactory {

	public abstract IPIRBodyVisitor createBodyVisitor(IRegister register);

}
