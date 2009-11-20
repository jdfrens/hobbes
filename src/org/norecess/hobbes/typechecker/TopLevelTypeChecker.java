package org.norecess.hobbes.typechecker;

import java.io.PrintStream;

import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.drivers.AbortTranslatorException;
import org.norecess.hobbes.drivers.CLIStatusCodes;

import com.google.inject.Inject;

public class TopLevelTypeChecker implements ITopLevelTypeChecker {

	private final ITypeChecker	myTypeChecker;

	@Inject
	public TopLevelTypeChecker(ITypeChecker typeChecker) {
		myTypeChecker = typeChecker;
	}

	public HobbesType typeCheck(PrintStream err, ExpressionTIR expression) {
		try {
			return expression.accept(myTypeChecker);
		} catch (HobbesTypeException e) {
			err.println("Error on line " + e.getPosition().getPosition() + ": "
					+ e.getMessage());
			throw new AbortTranslatorException(CLIStatusCodes.STATUS_TYPE_ERROR);
		}
	}

}
