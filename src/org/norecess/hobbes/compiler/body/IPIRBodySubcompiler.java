package org.norecess.hobbes.compiler.body;

import java.util.List;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.citkit.visitors.LValueTIRVisitor;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.body.declarations.IPIRDeclarationCompiler;
import org.norecess.hobbes.compiler.resources.IRegister;

public interface IPIRBodySubcompiler extends ExpressionTIRVisitor<ICode>,
		LValueTIRVisitor<ICode> {

	ICode recurse(ExpressionTIR expression, IRegister target);

	ICode recurse(IEnvironment<IRegister> environment,
			ExpressionTIR expression, IRegister target);

	ICode bind(List<? extends DeclarationTIR> declarations,
			IEnvironment<IRegister> newEnvironment);

	IPIRDeclarationCompiler createBinder(IEnvironment<IRegister> environment);

}
