package org.norecess.hobbes.interpreter;

import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;

public interface IInterpreterSystem {

	void typeCheck(PrintStream err, ExpressionTIR tir);

	void evalAndPrint(PrintStream out, ExpressionTIR tir);

}
