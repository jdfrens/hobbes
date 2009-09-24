package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.expressions.IntegerETIR;

public class ModulusOperatorTest {

	private ModulusAppliable	myOperator;

	@Before
	public void setUp() {
		myOperator = new ModulusAppliable();
	}

	@Test
	public void shouldInterpretModulus() {
		assertEquals(new IntegerETIR(0), myOperator.apply(new IntegerETIR(8),
				new IntegerETIR(2)));
		assertEquals(new IntegerETIR(3), myOperator.apply(new IntegerETIR(15),
				new IntegerETIR(4)));
	}

}
