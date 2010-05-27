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

public class SubtractionAppliableTest {

	private Appliable	myOperator;

	@Before
	public void setUp() {
		myOperator = PolyD.build(Appliable.class, new SubtractionAppliable());
	}

	@Test
	public void shouldInterpretIntegerSubtraction() {
		assertEquals(new IntegerETIR(-6),
				myOperator.apply(new IntegerETIR(2), new IntegerETIR(8)));
		assertEquals(new IntegerETIR(5),
				myOperator.apply(new IntegerETIR(15), new IntegerETIR(10)));
	}

	@Test
	public void shouldInterpretFloatSubstraction() {
		assertEquals(new FloatingPointETIR(3.14159 - 3.0), myOperator.apply(
				new FloatingPointETIR(3.14159), new FloatingPointETIR(3.0)));
		assertEquals(new FloatingPointETIR(8.5), myOperator.apply(
				new FloatingPointETIR(14.9), new FloatingPointETIR(6.4)));
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
