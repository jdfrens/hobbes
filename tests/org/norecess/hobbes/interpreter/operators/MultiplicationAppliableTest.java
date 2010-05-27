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

public class MultiplicationAppliableTest {

	private Appliable	myOperator;

	@Before
	public void setUp() {
		myOperator = PolyD
				.build(Appliable.class, new MultiplicationAppliable());
	}

	@Test
	public void shouldInterpretIntegerMultiplication() {
		assertEquals(new IntegerETIR(16),
				myOperator.apply(new IntegerETIR(2), new IntegerETIR(8)));
		assertEquals(new IntegerETIR(-150),
				myOperator.apply(new IntegerETIR(15), new IntegerETIR(-10)));
	}

	@Test
	public void shouldInterpretFloatMultiplication() {
		assertEquals(new FloatingPointETIR(3.2), myOperator.apply(
				new FloatingPointETIR(0.8), new FloatingPointETIR(4.0)));
		assertEquals(new FloatingPointETIR(32.0), myOperator.apply(
				new FloatingPointETIR(2.0), new FloatingPointETIR(16.0)));
	}

	@Test(expected = OperatorTypeException.class)
	public void shouldThrowTypeExceptionByDefault() {
		IMocksControl control = EasyMock.createControl();
		DatumTIR datum1 = control.createMock(DatumTIR.class);
		DatumTIR datum2 = control.createMock(DatumTIR.class);

		control.replay();
		myOperator.apply(datum1, datum2);
	}

}
