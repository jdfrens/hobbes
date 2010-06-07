package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.BooleanETIR;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class OrAppliable implements Appliable {

	public DatumTIR apply(DatumTIR i, DatumTIR j) {
		throw new OperatorTypeException();
	}

	public DatumTIR apply(BooleanETIR a, BooleanETIR b) {
		return HobbesBoolean.convert(a.getValue() || b.getValue());
	}

}
