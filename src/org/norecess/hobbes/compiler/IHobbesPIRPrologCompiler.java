package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;

public interface IHobbesPIRPrologCompiler extends ExpressionTIRVisitor<ICode> {

	ICode generateProlog(ICode code, ExpressionTIR expr);

}
