package org.norecess.hobbes.compiler.body.declarations;

import org.norecess.citkit.ISymbol;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.visitors.DeclarationTIRVisitor;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;

/**
 * @see PIRDeclarationCompiler
 */
public interface IPIRDeclarationCompiler extends DeclarationTIRVisitor<ICode> {

	void bind(ISymbol variable, IRegister register);

	ICode compile(IRegister register, ExpressionTIR initialization);

}
