package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.FloatingPointETIR;
import org.norecess.citkit.tir.expressions.IFloatingPointETIR;
import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class MultiplicationAppliable implements Appliable {

	public DatumTIR apply(DatumTIR i, DatumTIR j) {
		throw new OperatorTypeException();
	}

	public IIntegerETIR apply(IIntegerETIR i, IIntegerETIR j) {
		return new IntegerETIR(i.getValue() * j.getValue());
	}

	public IFloatingPointETIR apply(IFloatingPointETIR x, IFloatingPointETIR y) {
		return new FloatingPointETIR(x.getValue() * y.getValue());
	}

}
