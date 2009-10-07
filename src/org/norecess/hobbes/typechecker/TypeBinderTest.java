package org.norecess.hobbes.typechecker;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.ISymbol;
import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.declarations.VariableDTIR;
import org.norecess.citkit.types.PrimitiveType;

public class TypeBinderTest {

	private IMocksControl				myMocksControl;

	private IEnvironment<PrimitiveType>	myEnvironment;
	private ITypeChecker				myTypeChecker;

	private TypeBinder					myBinder;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myEnvironment = myMocksControl.createMock(IEnvironment.class);
		myTypeChecker = myMocksControl.createMock(ITypeChecker.class);

		myBinder = new TypeBinder(myEnvironment, myTypeChecker);
	}

	@Test
	public void shouldTypeCheckVariableDeclaration() {
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		ExpressionTIR initialization = myMocksControl
				.createMock(ExpressionTIR.class);
		PrimitiveType type = myMocksControl.createMock(PrimitiveType.class);

		EasyMock.expect(myTypeChecker.recurse(initialization)).andReturn(type);
		myEnvironment.add(symbol, type);

		myMocksControl.replay();
		assertSame(myEnvironment, myBinder.visitVariableDTIR(new VariableDTIR(
				symbol, initialization)));
		myMocksControl.verify();
	}

}
