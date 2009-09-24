package org.norecess.hobbes.compiler.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.norecess.hobbes.compiler.resources.Register;
import org.norecess.hobbes.compiler.resources.ResourceAllocator;

public class ResourceAllocatorTest {

	@Test
	public void shouldAllocateRegisters() {
		ResourceAllocator allocator = new ResourceAllocator();
		assertEquals(new Register(0), allocator.nextRegister());
		assertEquals(new Register(1), allocator.nextRegister());
		assertEquals(new Register(2), allocator.nextRegister());
		assertEquals(new Register(3), allocator.nextRegister());
		assertEquals(new Register(4), allocator.nextRegister());
	}

	@Test
	public void shouldAllocateLotsOfRegisters() {
		ResourceAllocator allocator = new ResourceAllocator();
		for (int i = 0; i < 2000; i++) {
			allocator.nextRegister();
		}
		assertEquals(new Register(2000), allocator.nextRegister());
	}

}