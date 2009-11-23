package org.norecess.hobbes.compiler.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResourceAllocatorTest {

	@Test
	public void shouldAllocateRegisters() {
		ResourceAllocator allocator = new ResourceAllocator();
		assertEquals(new IntegerRegister(1), allocator.nextRegister());
		assertEquals(new IntegerRegister(2), allocator.nextRegister());
		assertEquals(new IntegerRegister(3), allocator.nextRegister());
		assertEquals(new IntegerRegister(4), allocator.nextRegister());
	}

	@Test
	public void shouldAllocateLotsOfRegisters() {
		ResourceAllocator allocator = new ResourceAllocator();
		for (int i = 1; i < 2000; i++) {
			allocator.nextRegister();
		}
		assertEquals(new IntegerRegister(2000), allocator.nextRegister());
	}

}
