package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.citkit.visitors.LValueTIRVisitor;
import org.norecess.hobbes.backend.ICode;

public interface IPIRBodyVisitor extends ExpressionTIRVisitor<ICode>,
		LValueTIRVisitor<ICode> {

	ICode recurse(ExpressionTIR expression, IRegister target);

}