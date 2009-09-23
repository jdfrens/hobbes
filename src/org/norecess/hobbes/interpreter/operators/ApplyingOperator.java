package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.expressions.IIntegerETIR;

public interface ApplyingOperator {

	IIntegerETIR apply(IIntegerETIR i, IIntegerETIR j);

}
