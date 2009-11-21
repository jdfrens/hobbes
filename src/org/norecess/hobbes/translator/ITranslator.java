package org.norecess.hobbes.translator;

import java.io.IOException;
import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;

public interface ITranslator {

	void evalAndPrint(PrintStream out, HobbesType returnType,
			ExpressionTIR expression) throws IOException;

}
