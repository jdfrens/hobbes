package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.HobbesBoolean;

import ovm.polyd.PolyD;

public class OrAppliableTest {

	private Appliable	myOperator;

	@Before
	public void setUp() {
		myOperator = PolyD.build(Appliable.class, new OrAppliable());
	}

	@Test
	public void shouldOrBooleans() {
		assertEquals(HobbesBoolean.TRUE,
				myOperator.apply(HobbesBoolean.TRUE, HobbesBoolean.TRUE));
		assertEquals(HobbesBoolean.TRUE,
				myOperator.apply(HobbesBoolean.TRUE, HobbesBoolean.FALSE));
		assertEquals(HobbesBoolean.TRUE,
				myOperator.apply(HobbesBoolean.FALSE, HobbesBoolean.TRUE));
		assertEquals(HobbesBoolean.FALSE,
				myOperator.apply(HobbesBoolean.FALSE, HobbesBoolean.FALSE));
	}

}
