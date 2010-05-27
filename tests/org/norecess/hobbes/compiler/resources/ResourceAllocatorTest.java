package org.norecess.hobbes.compiler.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.StringType;

public class ResourceAllocatorTest {

	private ResourceAllocator	myAllocator;

	@Before
	public void setUp() {
		myAllocator = new ResourceAllocator();
	}

	@Test
	public void shouldAllocateIntegerRegistersForInts() {
		assertEquals(new IntegerRegister(1),
				myAllocator.nextRegister(IntegerType.TYPE));
		assertEquals(new IntegerRegister(2),
				myAllocator.nextRegister(IntegerType.TYPE));
		assertEquals(new IntegerRegister(3),
				myAllocator.nextRegister(IntegerType.TYPE));
		assertEquals(new IntegerRegister(4),
				myAllocator.nextRegister(IntegerType.TYPE));
	}

	@Test
	public void shouldAllocateIntegerRegistersForBools() {
		assertEquals(new IntegerRegister(1),
				myAllocator.nextRegister(BooleanType.TYPE));
		assertEquals(new IntegerRegister(2),
				myAllocator.nextRegister(BooleanType.TYPE));
		assertEquals(new IntegerRegister(3),
				myAllocator.nextRegister(BooleanType.TYPE));
		assertEquals(new IntegerRegister(4),
				myAllocator.nextRegister(BooleanType.TYPE));
	}

	@Test
	public void shouldAllocateNumberRegistersForFloats() {
		assertEquals(new NumberRegister(1),
				myAllocator.nextRegister(FloatingPointType.TYPE));
		assertEquals(new NumberRegister(2),
				myAllocator.nextRegister(FloatingPointType.TYPE));
		assertEquals(new NumberRegister(3),
				myAllocator.nextRegister(FloatingPointType.TYPE));
		assertEquals(new NumberRegister(4),
				myAllocator.nextRegister(FloatingPointType.TYPE));
	}

	@Test
	public void shouldAllocateRegisters() {
		assertEquals(new IntegerRegister(1),
				myAllocator.nextRegister(BooleanType.TYPE));
		assertEquals(new NumberRegister(2),
				myAllocator.nextRegister(FloatingPointType.TYPE));
		assertEquals(new IntegerRegister(3),
				myAllocator.nextRegister(IntegerType.TYPE));
		assertEquals(new IntegerRegister(4),
				myAllocator.nextRegister(IntegerType.TYPE));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAllocationRegisterForRecognizedTypes() {
		myAllocator.nextRegister(StringType.TYPE);
	}

}
