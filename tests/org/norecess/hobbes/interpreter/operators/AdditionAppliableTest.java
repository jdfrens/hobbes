package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.expressions.FloatingPointETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;

import ovm.polyd.PolyD;

public class AdditionAppliableTest {

	private Appliable	myOperator;

	@Before
	public void setUp() {
		myOperator = PolyD.build(Appliable.class, new AdditionAppliable());
	}

	@Test
	public void shouldInterpretIntegerAddition() {
		assertEquals(new IntegerETIR(10),
				myOperator.apply(new IntegerETIR(2), new IntegerETIR(8)));
		assertEquals(new IntegerETIR(5),
				myOperator.apply(new IntegerETIR(15), new IntegerETIR(-10)));
	}

	@Test
	public void shouldInterpretFloatAddition() {
		assertEquals(new FloatingPointETIR(4.14159), myOperator.apply(
				new FloatingPointETIR(3.14159), new FloatingPointETIR(1.0)));
		assertEquals(new FloatingPointETIR(5.5), myOperator.apply(
				new FloatingPointETIR(3.1), new FloatingPointETIR(2.4)));
	}

}
