package org.norecess.hobbes.compiler.prolog;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.visitors.DeclarationTIRVisitor;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.citkit.visitors.LValueTIRVisitor;
import org.norecess.hobbes.backend.ICode;

public interface IPIRPrologCompiler extends ExpressionTIRVisitor<ICode>,
		LValueTIRVisitor<ICode>, DeclarationTIRVisitor<ICode> {

	ICode generateProlog(ExpressionTIR expr);

}
