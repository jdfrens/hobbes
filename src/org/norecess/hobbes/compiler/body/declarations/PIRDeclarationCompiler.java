package org.norecess.hobbes.compiler.body.declarations;

import org.norecess.citkit.ISymbol;
import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.declarations.IFunctionDTIR;
import org.norecess.citkit.tir.declarations.ITypeDTIR;
import org.norecess.citkit.tir.declarations.IVariableDTIR;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.body.IPIRBodySubcompiler;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

/**
 * Compiles a declaration of a <code>let</code>. This includes binding the
 * declaration's variable in the appropriate environment.
 */
public class PIRDeclarationCompiler implements IPIRDeclarationCompiler {

	private final IPIRBodySubcompiler		myCompiler;
	private final IResourceAllocator		myResourceAllocator;
	private final IEnvironment<IRegister>	myEnvironment;
	private final IPIRDeclarationCompiler	myRecurser;

	public PIRDeclarationCompiler(IPIRBodySubcompiler compiler,
			IResourceAllocator resourceAllocator,
			IEnvironment<IRegister> environment,
			IPIRDeclarationCompiler recurser) {
		myCompiler = compiler;
		myResourceAllocator = resourceAllocator;
		myEnvironment = environment;
		myRecurser = recurser;
	}

	public PIRDeclarationCompiler(IPIRBodySubcompiler compiler,
			IResourceAllocator resourceAllocator,
			IEnvironment<IRegister> environment) {
		myCompiler = compiler;
		myResourceAllocator = resourceAllocator;
		myEnvironment = environment;
		myRecurser = this;
	}

	public ICode visitFunctionDTIR(IFunctionDTIR function) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitTypeDTIR(ITypeDTIR type) {
		throw new IllegalStateException("unimplemented!");
	}

	public ICode visitVariableDTIR(IVariableDTIR variable) {
		IRegister register = myResourceAllocator.nextRegister(variable
				.getInititialization().getType());
		myRecurser.bind(variable.getSymbol(), register);
		return myRecurser.compile(register, variable.getInititialization());
	}

	public void bind(ISymbol variable, IRegister register) {
		myEnvironment.add(variable, register);
	}

	public ICode compile(IRegister register, ExpressionTIR initialization) {
		return myCompiler.recurse(initialization, register);
	}

}
