package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegisterAllocatorTest {

	@Test
	public void shouldAllocateRegisters() {
		RegisterAllocator allocator = new RegisterAllocator();
		assertEquals(new Register(0), allocator.next());
		assertEquals(new Register(1), allocator.next());
		assertEquals(new Register(2), allocator.next());
		assertEquals(new Register(3), allocator.next());
		assertEquals(new Register(4), allocator.next());
	}

	@Test
	public void shouldAllocateLotsOfRegisters() {
		RegisterAllocator allocator = new RegisterAllocator();
		for (int i = 0; i < 2000; i++) {
			allocator.next();
		}
		assertEquals(new Register(2000), allocator.next());
	}

}
