package org.norecess.hobbes.compiler;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.citkit.visitors.LValueTIRVisitor;

public interface IHobbesPIRBodyVisitor extends ExpressionTIRVisitor<ICode>,
		LValueTIRVisitor<ICode> {

	ICode recurse(ExpressionTIR expression, IRegister target);

}
