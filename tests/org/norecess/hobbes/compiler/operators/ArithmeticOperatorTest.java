package org.norecess.hobbes.compiler.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.operators.ArithmeticOperator;
import org.norecess.hobbes.compiler.resources.IntegerRegister;
import org.norecess.hobbes.compiler.resources.ResourceAllocator;

public class ArithmeticOperatorTest {

	private ArithmeticOperator	myApersandOperator;
	private ArithmeticOperator	myDollarOperator;

	@Before
	public void setUp() {
		myApersandOperator = new ArithmeticOperator("&");
		myDollarOperator = new ArithmeticOperator("$");
	}

	@Test
	public void shouldCompileInstructions() {
		assertEquals(new Code("$I5 &= $I12"), myApersandOperator.compile(
				new ResourceAllocator(), new IntegerRegister(5), new IntegerRegister(12)));
		assertEquals(new Code("$I23 $= $I9"), myDollarOperator.compile(
				new ResourceAllocator(), new IntegerRegister(23), new IntegerRegister(9)));
	}

}
