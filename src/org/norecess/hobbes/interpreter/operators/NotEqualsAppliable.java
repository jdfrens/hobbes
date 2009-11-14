package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class NotEqualsAppliable implements Appliable {

	public DatumTIR apply(DatumTIR i, DatumTIR j) {
		throw new OperatorTypeException();
	}

	public DatumTIR apply(IIntegerETIR i, IIntegerETIR j) {
		return HobbesBoolean.convert(i.getValue() != j.getValue());
	}

}
