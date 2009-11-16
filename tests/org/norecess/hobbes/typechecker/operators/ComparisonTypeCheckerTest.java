package org.norecess.hobbes.typechecker.operators;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.types.BooleanType;
import org.norecess.citkit.types.IntegerType;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.OperatorTypeException;

import ovm.polyd.PolyD;

public class ComparisonTypeCheckerTest {

	private OperatorTypeChecker	myTypeChecker;

	@Before
	public void setUp() {
		myTypeChecker = PolyD.build(OperatorTypeChecker.class,
				new ComparisonTypeChecker());
	}

	@Test(expected = OperatorTypeException.class)
	public void shouldThrowExceptionForTypeError() {
		myTypeChecker.check(EasyMock.createMock(PrimitiveType.class), EasyMock
				.createMock(PrimitiveType.class));
	}

	@Test
	public void shouldTypeCheckIntegerComparedToInteger() {
		assertSame(BooleanType.BOOLEAN_TYPE, myTypeChecker.check(
				IntegerType.INTEGER_TYPE, IntegerType.INTEGER_TYPE));
	}

}
