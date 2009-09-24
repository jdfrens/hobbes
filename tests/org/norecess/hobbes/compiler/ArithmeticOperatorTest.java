package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.resources.Register;
import org.norecess.hobbes.compiler.resources.ResourceAllocator;

public class ArithmeticOperatorTest {

	private ArithmeticOperator	myApersandOperator;
	private ArithmeticOperator	myDollarOperator;

	@Before
	public void setUp() {
		myApersandOperator = new ArithmeticOperator(new ResourceAllocator(),
				"&");
		myDollarOperator = new ArithmeticOperator(new ResourceAllocator(), "$");
	}

	@Test
	public void shouldCompileInstructions() {
		assertEquals(new Code("$I5 &= $I12"), myApersandOperator.compile(
				new Register(5), new Register(12)));
		assertEquals(new Code("$I23 $= $I9"), myDollarOperator.compile(
				new Register(23), new Register(9)));
	}

}
