package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.typechecker.HobbesTypeException;

public class AdditionAppliable implements Appliable {

	public DatumTIR apply(DatumTIR i, DatumTIR j) {
		throw new HobbesTypeException(i.getType().toShortString() + " + "
				+ j.getType().toShortString());
	}

	public IIntegerETIR apply(IIntegerETIR i, IIntegerETIR j) {
		return new IntegerETIR(i.getValue() + j.getValue());
	}

}
