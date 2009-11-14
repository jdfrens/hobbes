package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class ModulusAppliableTest {

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

	@Test(expected = OperatorTypeException.class)
	public void shouldTypeCheck() {
		IMocksControl myMocksControl = EasyMock.createControl();
		DatumTIR datum1 = myMocksControl.createMock(DatumTIR.class);
		DatumTIR datum2 = myMocksControl.createMock(DatumTIR.class);

		myMocksControl.replay();
		myOperator.apply(datum1, datum2);
	}

}
