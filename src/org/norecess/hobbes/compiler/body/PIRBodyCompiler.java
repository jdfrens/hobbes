package org.norecess.hobbes.compiler.body;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.BooleanType;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.ICompilerFactory;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.Register;

import com.google.inject.Inject;

/*
 * A "component compiler" is a compiler that compiles only components of a program.
 * Like a single integer.
 */
public class PIRBodyCompiler implements IPIRBodyCompiler {

	public final static IRegister	ACC	= new Register(0);

	private final ICompilerFactory	myBodyVisitorFactory;

	@Inject
	public PIRBodyCompiler(ICompilerFactory factory) {
		myBodyVisitorFactory = factory;
	}

	public ICode generate(ExpressionTIR tir) {
		ICode code = new Code();
		code.append(tir.accept(myBodyVisitorFactory.createBodyVisitor()));
		return code;
	}

	public ICode generatePrint(ExpressionTIR tir) {
		ICode code = new Code();
		if (tir.accept(myBodyVisitorFactory.createTypeChecker()) == BooleanType.BOOLEAN_TYPE) {
			code.add("print_bool(", ACC, ")");
		} else {
			code.add("print " + ACC);
		}
		code.add("print \"\\n\"");
		return code;
	}

}
