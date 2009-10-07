package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.ISymbol;
import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.DeclarationTIR;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.data.DatumTIR;
import org.norecess.citkit.tir.declarations.VariableDTIR;

public class DeclarationBinderTest {

	private IMocksControl			myMocksControl;

	private IInterpreter			myInterpreter;
	private IEnvironment<DatumTIR>	myEnvironment;

	private DeclarationBinder		myBinder;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myInterpreter = myMocksControl.createMock(IInterpreter.class);
		myEnvironment = myMocksControl.createMock(IEnvironment.class);

		myBinder = new DeclarationBinder(myInterpreter, myEnvironment);
	}

	@Test
	public void shouldBindNothing() {
		myMocksControl.replay();
		assertSame(myEnvironment, myBinder.bind(Arrays
				.<DeclarationTIR> asList()));
		myMocksControl.verify();
	}

	@Test
	public void shouldBindOneThing() {
		DeclarationTIR declaration = myMocksControl
				.createMock(DeclarationTIR.class);

		EasyMock.expect(declaration.accept(myBinder)).andReturn(myEnvironment);

		myMocksControl.replay();
		assertSame(myEnvironment, myBinder.bind(Arrays.asList(declaration)));
		myMocksControl.verify();
	}

	@Test
	public void shouldBindManyThings() {
		DeclarationTIR[] declarations = new DeclarationTIR[] {
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class),
				myMocksControl.createMock(DeclarationTIR.class) };

		EasyMock.expect(declarations[0].accept(myBinder)).andReturn(
				myEnvironment);
		EasyMock.expect(declarations[1].accept(myBinder)).andReturn(
				myEnvironment);
		EasyMock.expect(declarations[2].accept(myBinder)).andReturn(
				myEnvironment);

		myMocksControl.replay();
		assertSame(myEnvironment, myBinder.bind(Arrays.asList(declarations)));
		myMocksControl.verify();
	}

	@Test
	public void shouldBindVariableDeclaration() {
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		ExpressionTIR initialization = myMocksControl
				.createMock(ExpressionTIR.class);
		DatumTIR evaluation = myMocksControl.createMock(DatumTIR.class);

		EasyMock.expect(initialization.accept(myInterpreter)).andReturn(
				evaluation);
		myEnvironment.add(symbol, evaluation);

		myMocksControl.replay();
		assertNull(myBinder.visitVariableDTIR(new VariableDTIR(symbol,
				initialization)));
		myMocksControl.verify();
	}
}
