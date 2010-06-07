package org.norecess.hobbes.typechecker.operators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.FloatingPointType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.hobbes.typechecker.OperatorTypeException;

import ovm.polyd.PolyD;

public class LogicTypeCheckerTest {

	private OperatorTypeChecker	myTypeChecker;

	@Before
	public void setUp() {
		myTypeChecker = PolyD.build(OperatorTypeChecker.class,
				new LogicTypeChecker());
	}

	@Test
	public void shouldReturnBoolType() {
		assertEquals(BooleanType.TYPE,
				myTypeChecker.check(BooleanType.TYPE, BooleanType.TYPE));
	}

	@Test(expected = OperatorTypeException.class)
	public void shouldDisallowInts() {
		myTypeChecker.check(IntegerType.TYPE, IntegerType.TYPE);
	}

	@Test(expected = OperatorTypeException.class)
	public void shouldDisallowFloats() {
		myTypeChecker.check(FloatingPointType.TYPE, FloatingPointType.TYPE);
	}

}
