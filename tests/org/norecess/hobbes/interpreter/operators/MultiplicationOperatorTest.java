package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.expressions.IntegerETIR;

public class MultiplicationOperatorTest {

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

}
