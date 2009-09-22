package org.norecess.hobbes;

import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;

public class HobbesBoolean {

	public static IIntegerETIR	TRUE	= new IntegerETIR(1);
	public static IIntegerETIR	FALSE	= new IntegerETIR(0);

	public static IIntegerETIR parse(String input) {
		if ("#t".equals(input)) {
			return TRUE;
		} else if ("#f".equals(input)) {
			return FALSE;
		} else {
			throw new IllegalArgumentException(input
					+ " is not a valid boolean");
		}
	}

}
