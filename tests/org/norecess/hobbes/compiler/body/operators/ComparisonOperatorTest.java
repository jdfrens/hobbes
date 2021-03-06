package org.norecess.hobbes.compiler.body.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IntegerRegister;
import org.norecess.hobbes.compiler.resources.NumberRegister;
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
	public void shouldCompileComparisonInstructions2() {
		assertEquals(new Code("$I2 = $I55 @ $I3"), myAtOperator.compile(
				new ResourceAllocator(), new IntegerRegister(2),
				new IRegister[] { new IntegerRegister(55),
						new IntegerRegister(3) }));
		assertEquals(new Code("$I2 = $N55 # $N3"),
				myPoundOperator
						.compile(new ResourceAllocator(),
								new IntegerRegister(2), new IRegister[] {
										new NumberRegister(55),
										new NumberRegister(3) }));
	}

}
