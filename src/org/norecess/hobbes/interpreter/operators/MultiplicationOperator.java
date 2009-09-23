package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.expressions.IIntegerETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;

public class MultiplicationOperator implements ApplyingOperator {

	public IIntegerETIR apply(IIntegerETIR i, IIntegerETIR j) {
		return new IntegerETIR(i.getValue() * j.getValue());
	}

}
