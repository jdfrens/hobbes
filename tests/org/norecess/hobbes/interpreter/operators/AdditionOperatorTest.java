package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.expressions.IntegerETIR;

public class AdditionOperatorTest {

	private AdditionAppliable	myOperator;

	@Before
	public void setUp() {
		myOperator = new AdditionAppliable();
	}

	@Test
	public void shouldInterpretMultiplication() {
		assertEquals(new IntegerETIR(10), myOperator.apply(new IntegerETIR(2),
				new IntegerETIR(8)));
		assertEquals(new IntegerETIR(5), myOperator.apply(new IntegerETIR(15),
				new IntegerETIR(-10)));
	}

}
