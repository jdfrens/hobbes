package org.norecess.hobbes.compiler;

import org.norecess.hobbes.compiler.body.IPIRBodyVisitor;
import org.norecess.hobbes.typechecker.ITypeChecker;

public interface ICompilerFactory {

	public abstract IPIRBodyVisitor createBodyVisitor();

	public abstract ITypeChecker createTypeChecker();

}
