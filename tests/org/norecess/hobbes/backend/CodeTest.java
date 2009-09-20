package org.norecess.hobbes.backend;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.norecess.hobbes.compiler.resources.Label;

public class CodeTest {

	@Test
	public void shouldAddColonToLabel() {
		Code code = new Code();
		code.add(new Label(55));
		assertEquals(new Code("label_55:"), code);
	}

}
