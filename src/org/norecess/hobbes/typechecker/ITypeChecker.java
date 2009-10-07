package org.norecess.hobbes.typechecker;

import java.util.List;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.citkit.visitors.LValueTIRVisitor;

public interface ITypeChecker extends ExpressionTIRVisitor<PrimitiveType>,
		LValueTIRVisitor<PrimitiveType> {

	PrimitiveType recurse(IEnvironment<PrimitiveType> newEnvironment,
			ExpressionTIR expression);

	PrimitiveType recurse(ExpressionTIR expression);

	IEnvironment<PrimitiveType> bind(
			IEnvironment<PrimitiveType> newEnvironment,
			List<? extends DeclarationTIR> declarations);

	ITypeBinder createBinder(IEnvironment<PrimitiveType> environment);

}
