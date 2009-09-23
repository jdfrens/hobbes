package org.norecess.hobbes.interpreter.operators;

import org.norecess.citkit.tir.data.DatumTIR;

import ovm.polyd.policy.MultiDisp;
import ovm.polyd.tags.DispatchingPolicy;
import ovm.polyd.tags.PolyD;

@PolyD
@DispatchingPolicy(MultiDisp.class)
public interface ApplyingOperator {

	DatumTIR apply(DatumTIR i, DatumTIR j);

}
