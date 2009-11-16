package org.norecess.hobbes.output;

import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.hobbes.HobbesBoolean;

public class HobbesOutput implements IHobbesOutput {

	public String asHobbesOutput(DatumTIR value) {
		if (HobbesBoolean.TRUE.equals(value)) {
			return "#t";
		} else if (HobbesBoolean.FALSE.equals(value)) {
			return "#f";
		} else {
			return value.toString();
		}
	}

}
