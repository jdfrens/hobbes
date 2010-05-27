package org.norecess.hobbes.typechecker;

import java.util.List;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.LValueTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.citkit.visitors.DeclarationTIRVisitor;
import org.norecess.citkit.visitors.ExpressionTIRVisitor;
import org.norecess.citkit.visitors.LValueTIRVisitor;

public interface ITypeChecker extends ExpressionTIRVisitor<ExpressionTIR>,
		LValueTIRVisitor<LValueTIR>, DeclarationTIRVisitor<DeclarationTIR> {

	ExpressionTIR recurse(IEnvironment<HobbesType> newEnvironment,
			ExpressionTIR expression);

	ExpressionTIR recurse(ExpressionTIR expression);

	DeclarationTIR recurse(DeclarationTIR declarationTIR);

	IEnvironment<HobbesType> bind(List<? extends DeclarationTIR> declarations);

	ITypeBinder createBinder(IEnvironment<HobbesType> environment);

	List<DeclarationTIR> recurse(List<? extends DeclarationTIR> declarations);

}
