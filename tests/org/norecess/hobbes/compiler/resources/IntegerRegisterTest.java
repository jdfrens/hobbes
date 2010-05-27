package org.norecess.hobbes.compiler.resources;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.IntegerType;

public class IntegerRegisterTest {

	@Test
	public void shouldBeEquals() {
		assertTrue(new IntegerRegister(5).equals(new IntegerRegister(5)));
		assertTrue(new IntegerRegister(88).equals(new IntegerRegister(88)));
	}

	@Test
	public void shouldNotBeEquals() {
		assertFalse(new IntegerRegister(5).equals(new IntegerRegister(666)));
		assertFalse(new IntegerRegister(4).equals(new NumberRegister(4)));
	}

	@Test
	public void shouldBeCompatibleWithIntAndBoolTypes() {
		assertTrue(new IntegerRegister(4).isCompatible(IntegerType.TYPE));
		assertTrue(new IntegerRegister(2).isCompatible(BooleanType.TYPE));
	}

	@Test
	public void shouldNotBeCompatibleWithFloatType() {
		assertFalse(new IntegerRegister(99)
				.isCompatible(FloatingPointType.TYPE));
	}

}
