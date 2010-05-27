package org.norecess.hobbes.typechecker;

import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;

public interface ITopLevelTypeChecker {

	ExpressionTIR typeCheck(PrintStream err, ExpressionTIR expression);

}
