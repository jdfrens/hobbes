package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.HobbesBoolean;

public class LessThanAppliable implements Appliable {

	public DatumTIR apply(DatumTIR i, DatumTIR j) {
		throw new IllegalStateException("unimplemented!");
	}

	public DatumTIR apply(IntegerETIR i, IntegerETIR j) {
		return HobbesBoolean.convert(i.getValue() < j.getValue());
	}
}
