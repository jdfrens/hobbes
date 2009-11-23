package org.norecess.hobbes.compiler.body;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.ICompilerFactory;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IntegerRegister;

import com.google.inject.Inject;

/*
 * A "component compiler" is a compiler that compiles only components of a program.
 * Like a single integer.
 */
public class PIRBodyCompiler implements IPIRBodyCompiler {

	public final static IRegister	ACC			= new IntegerRegister(0);
	public final static IRegister	FLOAT_ACC	= new NumberRegister(0);

	private final ICompilerFactory	myBodyVisitorFactory;

	@Inject
	public PIRBodyCompiler(ICompilerFactory factory) {
		myBodyVisitorFactory = factory;
	}

	public ICode generate(HobbesType returnType, ExpressionTIR tir) {
		ICode code = new Code();
		code.append(tir.accept(myBodyVisitorFactory
				.createBodyVisitor(getTarget(returnType))));
		return code;
	}

	private IRegister getTarget(HobbesType returnType) {
		return returnType == FloatingPointType.FLOATING_POINT_TYPE ? FLOAT_ACC
				: ACC;
	}

	public ICode generatePrint(HobbesType returnType, ExpressionTIR tir) {
		ICode code = new Code();
		if (returnType == BooleanType.BOOLEAN_TYPE) {
			code.add("print_bool(", getTarget(returnType), ")");
		} else {
			code.add("print " + getTarget(returnType));
		}
		code.add("print \"\\n\"");
		return code;
	}

}
