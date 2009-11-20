package org.norecess.hobbes.typechecker;

import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;

public interface ITopLevelTypeChecker {

	HobbesType typeCheck(PrintStream err, ExpressionTIR expression);

}
