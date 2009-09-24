package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.expressions.IntegerETIR;

public class DivisionOperatorTest {

	private DivisionAppliable	myOperator;

	@Before
	public void setUp() {
		myOperator = new DivisionAppliable();
	}

	@Test
	public void shouldInterpretDivision() {
		assertEquals(new IntegerETIR(4), myOperator.apply(new IntegerETIR(8),
				new IntegerETIR(2)));
		assertEquals(new IntegerETIR(2), myOperator.apply(new IntegerETIR(15),
				new IntegerETIR(6)));
	}

}
