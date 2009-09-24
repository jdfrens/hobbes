package org.norecess.hobbes;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.BooleanETIR;

public class HobbesBoolean {

	public static BooleanETIR	TRUE	= BooleanETIR.TRUE;
	public static BooleanETIR	FALSE	= BooleanETIR.FALSE;

	public static BooleanETIR parse(String input) {
		if ("#t".equals(input)) {
			return TRUE;
		} else if ("#f".equals(input)) {
			return FALSE;
		} else {
			throw new IllegalArgumentException(input
					+ " is not a valid boolean");
		}
	}

	public static DatumTIR convert(boolean b) {
		return b ? TRUE : FALSE;
	}

}
