package org.norecess.hobbes.compiler.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.operators.ComparisonOperator;
import org.norecess.hobbes.compiler.resources.IntegerRegister;
import org.norecess.hobbes.compiler.resources.ResourceAllocator;

public class ComparisonOperatorTest {

	private ComparisonOperator	myAtOperator;
	private ComparisonOperator	myPoundOperator;

	@Before
	public void setUp() {
		myAtOperator = new ComparisonOperator("@");
		myPoundOperator = new ComparisonOperator("#");
	}

	@Test
	public void shouldCompileComparisonInstructions() {
		assertEquals(new Code("$I2 = $I2 @ $I3"), myAtOperator.compile(
				new ResourceAllocator(), new IntegerRegister(2), new IntegerRegister(3)));
		assertEquals(new Code("$I89 = $I89 # $I45"), myPoundOperator.compile(
				new ResourceAllocator(), new IntegerRegister(89), new IntegerRegister(45)));
	}

}
