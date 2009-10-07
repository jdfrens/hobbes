package org.norecess.hobbes.typechecker;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.declarations.IFunctionDTIR;
import org.norecess.citkit.tir.declarations.ITypeDTIR;
import org.norecess.citkit.tir.declarations.IVariableDTIR;
import org.norecess.citkit.types.PrimitiveType;

public class TypeBinder implements ITypeBinder {

	private final IEnvironment<PrimitiveType>	myEnvironment;
	private final ITypeChecker					myTypeChecker;

	public TypeBinder(IEnvironment<PrimitiveType> environment,
			ITypeChecker typeChecker) {
		myEnvironment = environment;
		myTypeChecker = typeChecker;
	}

	public IEnvironment<PrimitiveType> visitVariableDTIR(
			IVariableDTIR declaration) {
		myEnvironment.add(declaration.getSymbol(), myTypeChecker
				.recurse(declaration.getInititialization()));
		return myEnvironment;
	}

	public IEnvironment<PrimitiveType> visitFunctionDTIR(IFunctionDTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IEnvironment<PrimitiveType> visitTypeDTIR(ITypeDTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

}
