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
import org.norecess.hobbes.compiler.resources.NumberRegister;

import com.google.inject.Inject;

/*
 * Compiles the body of a function.
 */
public class PIRBodyCompiler implements IPIRBodyCompiler {

	public final static IRegister	INT_ACC		= new IntegerRegister(0);
	public final static IRegister	FLOAT_ACC	= new NumberRegister(0);

	private final ICompilerFactory	myCompilerFactory;

	@Inject
	public PIRBodyCompiler(ICompilerFactory factory) {
		myCompilerFactory = factory;
	}

	public ICode generate(ExpressionTIR tir) {
		ICode code = new Code();
		code.append(tir.accept(myCompilerFactory
				.createBodySubcompiler(getTarget(tir.getType()))));
		return code;
	}

	private IRegister getTarget(HobbesType returnType) {
		if (returnType == FloatingPointType.TYPE) {
			return FLOAT_ACC;
		} else {
			return INT_ACC;
		}
	}

	public ICode generatePrint(ExpressionTIR tir) {
		ICode code = new Code();
		BooleanType foo = BooleanType.TYPE;
		if (tir.getType() == foo) {
			code.add("print_bool(", getTarget(tir.getType()), ")");
		} else {
			code.add("print " + getTarget(tir.getType()));
		}
		code.add("print \"\\n\"");
		return code;
	}

}
