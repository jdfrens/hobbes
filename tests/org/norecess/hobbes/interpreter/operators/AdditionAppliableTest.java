package org.norecess.hobbes.interpreter.operators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.interpreter.HobbesTypeException;

public class AdditionAppliableTest {

	private AdditionAppliable	myOperator;

	@Before
	public void setUp() {
		myOperator = new AdditionAppliable();
	}

	@Test
	public void shouldInterpretAddition() {
		assertEquals(new IntegerETIR(10), myOperator.apply(new IntegerETIR(2),
				new IntegerETIR(8)));
		assertEquals(new IntegerETIR(5), myOperator.apply(new IntegerETIR(15),
				new IntegerETIR(-10)));
	}

	@Test
	public void shouldTypeCheck() {
		IMocksControl myMocksControl = EasyMock.createControl();
		DatumTIR datum1 = myMocksControl.createMock(DatumTIR.class);
		DatumTIR datum2 = myMocksControl.createMock(DatumTIR.class);
		HobbesType type1 = myMocksControl.createMock(HobbesType.class);
		HobbesType type2 = myMocksControl.createMock(HobbesType.class);

		EasyMock.expect(datum1.getType()).andReturn(type1);
		EasyMock.expect(type1.toShortString()).andReturn("TypeA");
		EasyMock.expect(datum2.getType()).andReturn(type2);
		EasyMock.expect(type2.toShortString()).andReturn("TypeB");

		myMocksControl.replay();
		try {
			myOperator.apply(datum1, datum2);
			fail("should be a type exception");
		} catch (HobbesTypeException e) {
			assertEquals("TypeA + TypeB", e.getMessage());
		}
		myMocksControl.verify();
	}

}
