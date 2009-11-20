package org.norecess.hobbes.compiler;

import org.norecess.hobbes.compiler.body.IPIRBodyVisitor;

public interface ICompilerFactory {

	public abstract IPIRBodyVisitor createBodyVisitor();

}
