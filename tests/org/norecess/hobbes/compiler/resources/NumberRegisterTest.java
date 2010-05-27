package org.norecess.hobbes.compiler.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.IntegerType;

public class NumberRegisterTest {

	@Test
	public void shouldToString() {
		assertEquals("$N0", new NumberRegister(0).toString());
		assertEquals("$N8", new NumberRegister(8).toString());
	}

	@Test
	public void shouldBeCompatibleWithFloatType() {
		assertTrue(new NumberRegister(8).isCompatible(FloatingPointType.TYPE));
	}

	@Test
	public void shouldNotBeCompatibleWithIntOrBoolType() {
		assertFalse(new NumberRegister(88).isCompatible(IntegerType.TYPE));
		assertFalse(new NumberRegister(888).isCompatible(BooleanType.TYPE));
	}

}
