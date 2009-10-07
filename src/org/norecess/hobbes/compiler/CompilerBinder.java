package org.norecess.hobbes.compiler;

import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.declarations.IFunctionDTIR;
import org.norecess.citkit.tir.declarations.ITypeDTIR;
import org.norecess.citkit.tir.declarations.IVariableDTIR;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class CompilerBinder implements ICompilerBinder {

	private final IPIRBodyVisitor			myCompiler;
	private final IResourceAllocator		myResourceAllocator;
	private final IEnvironment<IRegister>	myEnvironment;

	public CompilerBinder(IPIRBodyVisitor compiler,
			IResourceAllocator resourceAllocator,
			IEnvironment<IRegister> environment) {
		myCompiler = compiler;
		myResourceAllocator = resourceAllocator;
		myEnvironment = environment;
	}

	public ICode visitFunctionDTIR(IFunctionDTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitTypeDTIR(ITypeDTIR arg0) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitVariableDTIR(IVariableDTIR variable) {
		ICode code = myResourceAllocator.createCode();
		IRegister register = myResourceAllocator.nextRegister();
		code.append(myCompiler
				.recurse(variable.getInititialization(), register));
		myEnvironment.add(variable.getSymbol(), register);
		return code;
	}

}
