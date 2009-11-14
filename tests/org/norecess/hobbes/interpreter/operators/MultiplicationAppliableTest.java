package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class MultiplicationAppliableTest {

	private MultiplicationAppliable	myOperator;

	@Before
	public void setUp() {
		myOperator = new MultiplicationAppliable();
	}

	@Test
	public void shouldInterpretMultiplication() {
		assertEquals(new IntegerETIR(16), myOperator.apply(new IntegerETIR(2),
				new IntegerETIR(8)));
		assertEquals(new IntegerETIR(-150), myOperator.apply(
				new IntegerETIR(15), new IntegerETIR(-10)));
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
