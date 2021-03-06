package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.FloatingPointETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.HobbesBoolean;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class NotEqualsAppliableTest {

	private NotEqualsAppliable	myOperator;

	@Before
	public void setUp() {
		myOperator = new NotEqualsAppliable();
	}

	@Test(expected = OperatorTypeException.class)
	public void shouldTypeCheck() {
		IMocksControl myMocksControl = EasyMock.createControl();
		DatumTIR datum1 = myMocksControl.createMock(DatumTIR.class);
		DatumTIR datum2 = myMocksControl.createMock(DatumTIR.class);

		myMocksControl.replay();
		myOperator.apply(datum1, datum2);
	}

	@Test
	public void shouldCompareInts() {
		assertEquals(HobbesBoolean.TRUE,
				myOperator.apply(new IntegerETIR(4), new IntegerETIR(12)));
		assertEquals(HobbesBoolean.TRUE,
				myOperator.apply(new IntegerETIR(9), new IntegerETIR(1)));
		assertEquals(HobbesBoolean.FALSE,
				myOperator.apply(new IntegerETIR(23), new IntegerETIR(23)));
	}

	@Test
	public void shouldCompareFloats() {
		assertEquals(HobbesBoolean.TRUE, myOperator.apply(
				new FloatingPointETIR(4.2), new FloatingPointETIR(12.4)));
		assertEquals(HobbesBoolean.TRUE, myOperator.apply(
				new FloatingPointETIR(9.8), new FloatingPointETIR(1.6)));
		assertEquals(HobbesBoolean.FALSE, myOperator.apply(
				new FloatingPointETIR(23.0), new FloatingPointETIR(23.0)));
	}

	@Test
	public void shouldCompareBools() {
		assertEquals(HobbesBoolean.FALSE,
				myOperator.apply(HobbesBoolean.TRUE, HobbesBoolean.TRUE));
		assertEquals(HobbesBoolean.FALSE,
				myOperator.apply(HobbesBoolean.FALSE, HobbesBoolean.FALSE));
		assertEquals(HobbesBoolean.TRUE,
				myOperator.apply(HobbesBoolean.TRUE, HobbesBoolean.FALSE));
		assertEquals(HobbesBoolean.TRUE,
				myOperator.apply(HobbesBoolean.FALSE, HobbesBoolean.TRUE));
	}

}
