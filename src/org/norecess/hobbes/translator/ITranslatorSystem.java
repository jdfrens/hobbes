package org.norecess.hobbes.translator;

import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;

@Deprecated
public interface ITranslatorSystem {

	@Deprecated
	HobbesType typeCheck(PrintStream err, ExpressionTIR expression);

	@Deprecated
	void evalAndPrint(PrintStream out, HobbesType returnType,
			ExpressionTIR expression);

}
