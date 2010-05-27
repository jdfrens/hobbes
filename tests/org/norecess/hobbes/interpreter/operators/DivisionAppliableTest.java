package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.FloatingPointETIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.typechecker.OperatorTypeException;

import ovm.polyd.PolyD;

public class DivisionAppliableTest {

	private Appliable	myOperator;

	@Before
	public void setUp() {
		myOperator = PolyD.build(Appliable.class, new DivisionAppliable());
	}

	@Test
	public void shouldInterpretIntegerDivision() {
		assertEquals(new IntegerETIR(4),
				myOperator.apply(new IntegerETIR(8), new IntegerETIR(2)));
		assertEquals(new IntegerETIR(2),
				myOperator.apply(new IntegerETIR(15), new IntegerETIR(6)));
	}

	@Test
	public void shouldInterpretFloatDivision() {
		assertEquals(new FloatingPointETIR(2.0), myOperator.apply(
				new FloatingPointETIR(16.0), new FloatingPointETIR(8.0)));
		assertEquals(new FloatingPointETIR(44.4), myOperator.apply(
				new FloatingPointETIR(88.8), new FloatingPointETIR(2.0)));
	}

	@Test(expected = OperatorTypeException.class)
	public void shouldTypeCheck() {
		IMocksControl myMocksControl = EasyMock.createControl();
		DatumTIR datum1 = myMocksControl.createMock(DatumTIR.class);
		DatumTIR datum2 = myMocksControl.createMock(DatumTIR.class);

		myMocksControl.replay();
		myOperator.apply(datum1, datum2);
	}

}
