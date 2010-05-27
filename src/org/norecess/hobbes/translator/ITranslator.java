package org.norecess.hobbes.translator;

import java.io.IOException;
import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;

public interface ITranslator {

	void evalAndPrint(PrintStream out, ExpressionTIR expression)
			throws IOException;

}
