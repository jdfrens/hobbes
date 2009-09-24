package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.HobbesBoolean;

public class LessThanEqualsOperatorTest {

	private LessThanEqualsOperator	myOperator;

	@Before
	public void setUp() {
		myOperator = new LessThanEqualsOperator();
	}

	@Test
	public void shouldApplyLessThanEquals() {
		assertEquals(HobbesBoolean.TRUE, myOperator.apply(new IntegerETIR(4),
				new IntegerETIR(12)));
		assertEquals(HobbesBoolean.FALSE, myOperator.apply(new IntegerETIR(9),
				new IntegerETIR(1)));
		assertEquals(HobbesBoolean.TRUE, myOperator.apply(new IntegerETIR(23),
				new IntegerETIR(23)));
	}

}
