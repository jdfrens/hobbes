package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;

public class DivisionAppliable implements Appliable {

	public DatumTIR apply(DatumTIR i, DatumTIR j) {
		throw new IllegalStateException("unimplemented!");
	}

	public IIntegerETIR apply(IIntegerETIR i, IIntegerETIR j) {
		return new IntegerETIR(i.getValue() / j.getValue());
	}

}
