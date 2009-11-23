package org.norecess.hobbes.compiler.body;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumberRegisterTest {

	@Test
	public void shouldToString() {
		assertEquals("$N0", new NumberRegister(0).toString());
		assertEquals("$N8", new NumberRegister(8).toString());
	}

}
