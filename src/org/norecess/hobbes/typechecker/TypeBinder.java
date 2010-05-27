package org.norecess.hobbes.typechecker;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.declarations.IFunctionDTIR;
import org.norecess.citkit.tir.declarations.ITypeDTIR;
import org.norecess.citkit.tir.declarations.IVariableDTIR;
import org.norecess.citkit.types.HobbesType;

public class TypeBinder implements ITypeBinder {

	private final IEnvironment<HobbesType>	myEnvironment;

	public TypeBinder(IEnvironment<HobbesType> environment) {
		myEnvironment = environment;
	}

	public IEnvironment<HobbesType> visitVariableDTIR(IVariableDTIR declaration) {
		myEnvironment.add(declaration.getSymbol(), declaration
				.getInititialization().getType());
		return myEnvironment;
	}

	public IEnvironment<HobbesType> visitFunctionDTIR(IFunctionDTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IEnvironment<HobbesType> visitTypeDTIR(ITypeDTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

}
