package org.norecess.hobbes.interpreter;

import java.util.List;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.declarations.IFunctionDTIR;
import org.norecess.citkit.tir.declarations.ITypeDTIR;
import org.norecess.citkit.tir.declarations.IVariableDTIR;

public class DeclarationBinder implements IDeclarationBinder {

	private final IInterpreter				myInterpreter;
	private final IEnvironment<DatumTIR>	myEnvironment;

	public DeclarationBinder(IInterpreter interpreter,
			IEnvironment<DatumTIR> environment) {
		myInterpreter = interpreter;
		myEnvironment = environment;
	}

	public IEnvironment<DatumTIR> getEnvironment() {
		return myEnvironment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((myEnvironment == null) ? 0 : myEnvironment.hashCode());
		result = prime * result
				+ ((myInterpreter == null) ? 0 : myInterpreter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DeclarationBinder other = (DeclarationBinder) obj;
		if (myEnvironment == null) {
			if (other.myEnvironment != null) {
				return false;
			}
		} else if (!myEnvironment.equals(other.myEnvironment)) {
			return false;
		}
		if (myInterpreter == null) {
			if (other.myInterpreter != null) {
				return false;
			}
		} else if (!myInterpreter.equals(other.myInterpreter)) {
			return false;
		}
		return true;
	}

	public IEnvironment<DatumTIR> bind(
			List<? extends DeclarationTIR> declarations) {
		for (DeclarationTIR declaration : declarations) {
			declaration.accept(this);
		}
		return myEnvironment;
	}

	public IEnvironment<DatumTIR> visitVariableDTIR(IVariableDTIR declaration) {
		myEnvironment.add(declaration.getSymbol(), declaration
				.getInititialization().accept(myInterpreter));
		return null;
	}

	public IEnvironment<DatumTIR> visitFunctionDTIR(IFunctionDTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public IEnvironment<DatumTIR> visitTypeDTIR(ITypeDTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

}
