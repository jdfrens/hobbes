package org.norecess.hobbes.output;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.hobbes.HobbesBoolean;

public class HobbesOutput implements IHobbesOutput {

	public String asHobbesOutput(DatumTIR value) {
		if (value == HobbesBoolean.TRUE) {
			return "#t";
		} else if (value == HobbesBoolean.FALSE) {
			return "#f";
		} else {
			return value.toString();
		}
	}

}
