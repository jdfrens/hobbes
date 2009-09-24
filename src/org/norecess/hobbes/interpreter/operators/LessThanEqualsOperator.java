package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.hobbes.HobbesBoolean;

public class LessThanEqualsOperator implements ApplyingOperator {

	public DatumTIR apply(DatumTIR i, DatumTIR j) {
		throw new IllegalStateException("unimplemented!");
	}

	public DatumTIR apply(IIntegerETIR i, IIntegerETIR j) {
		return HobbesBoolean.convert(i.getValue() <= j.getValue());
	}

}
