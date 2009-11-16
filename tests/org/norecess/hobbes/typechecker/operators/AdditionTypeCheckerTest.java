package org.norecess.hobbes.typechecker.operators;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.types.PrimitiveType;
import org.norecess.hobbes.typechecker.OperatorTypeException;

public class AdditionTypeCheckerTest {

	private IMocksControl		myMocksControl;

	private ArithmeticTypeChecker	myTypeChecker;

	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myTypeChecker = new ArithmeticTypeChecker();
	}

	@Test(expected = OperatorTypeException.class)
	public void shouldTypeCheck() {
		PrimitiveType leftType = myMocksControl.createMock(PrimitiveType.class);
		PrimitiveType rightType = myMocksControl
				.createMock(PrimitiveType.class);

		myMocksControl.replay();
		myTypeChecker.check(leftType, rightType);
	}

}
