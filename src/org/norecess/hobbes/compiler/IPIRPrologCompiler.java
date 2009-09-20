package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.hobbes.backend.ICode;

public interface IPIRPrologCompiler extends ExpressionTIRVisitor<ICode> {

	ICode generateProlog(ExpressionTIR expr);

}