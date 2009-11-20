package org.norecess.hobbes.interpreter;

import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;

public interface ITranslatorSystem {

	HobbesType typeCheck(PrintStream err, ExpressionTIR tir);

	void evalAndPrint(PrintStream out, ExpressionTIR tir);

}
