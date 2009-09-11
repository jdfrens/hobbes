package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegisterAllocatorTest {

	@Test
	public void shouldAllocateRegisters() {
		RegisterAllocator allocator = new RegisterAllocator();
		assertEquals("$I0", allocator.next());
		assertEquals("$I1", allocator.next());
		assertEquals("$I2", allocator.next());
		assertEquals("$I3", allocator.next());
		assertEquals("$I4", allocator.next());
	}

	@Test
	public void shouldAllocateLotsOfRegisters() {
		RegisterAllocator allocator = new RegisterAllocator();
		for (int i = 0; i < 2000; i++) {
			allocator.next();
		}
		assertEquals("$I2000", allocator.next());
	}

}
