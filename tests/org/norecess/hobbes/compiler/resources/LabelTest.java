package org.norecess.hobbes.compiler.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LabelTest {

	@Test
	public void shouldHaveToString() {
		assertEquals("label_23", new Label(23).toString());
		assertEquals("label_42", new Label(42).toString());
	}

}
