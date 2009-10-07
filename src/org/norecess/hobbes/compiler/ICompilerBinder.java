package org.norecess.hobbes.compiler;

import org.norecess.citkit.visitors.DeclarationTIRVisitor;
import org.norecess.hobbes.backend.ICode;

public interface ICompilerBinder extends DeclarationTIRVisitor<ICode> {

}
