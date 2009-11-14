package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.interpreter.HobbesTypeException;

public class MultiplicationAppliableTest {

	private MultiplicationAppliable	myOperator;

	@Before
	public void setUp() {
		myOperator = new MultiplicationAppliable();
	}

	@Test
	public void shouldInterpretMultiplication() {
		assertEquals(new IntegerETIR(16), myOperator.apply(new IntegerETIR(2),
				new IntegerETIR(8)));
		assertEquals(new IntegerETIR(-150), myOperator.apply(
				new IntegerETIR(15), new IntegerETIR(-10)));
	}

	@Test
	public void shouldNotMultiplyTwoBooleans() {
		try {
			myOperator.apply(HobbesBoolean.TRUE, HobbesBoolean.FALSE);
			fail("should not multiply non-integers");
		} catch (HobbesTypeException e) {
			assertEquals("bool * bool", e.getMessage());
		}
	}

	@Test
	public void shouldNotMultiplyBooleanAndInteger() {
		try {
			myOperator.apply(HobbesBoolean.TRUE, new IntegerETIR(1231));
			fail("should not multiply non-integers");
		} catch (HobbesTypeException e) {
			assertEquals("bool * int", e.getMessage());
		}
	}
}
