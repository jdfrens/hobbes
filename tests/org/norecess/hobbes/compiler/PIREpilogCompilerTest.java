package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.backend.Code;

public class PIREpilogCompilerTest {

	private PIREpilogCompiler	myEpilogCompiler;

	@Before
	public void setUp() {
		myEpilogCompiler = new PIREpilogCompiler();
	}

	@Test
	public void shouldCompileEpilog() {
		assertEquals(new Code(".end"), myEpilogCompiler.generate());
	}

}
