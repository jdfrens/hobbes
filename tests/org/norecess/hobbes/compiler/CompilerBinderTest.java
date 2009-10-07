package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.ISymbol;
import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.declarations.VariableDTIR;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.body.IPIRBodyVisitor;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class CompilerBinderTest {

	private IMocksControl			myMocksControl;

	private IPIRBodyVisitor			myCompiler;
	private IResourceAllocator		myResourceAllocator;
	private IEnvironment<IRegister>	myEnvironment;

	private CompilerBinder			myBinder;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myCompiler = myMocksControl.createMock(IPIRBodyVisitor.class);
		myResourceAllocator = myMocksControl
				.createMock(IResourceAllocator.class);
		myEnvironment = myMocksControl.createMock(IEnvironment.class);

		myBinder = new CompilerBinder(myCompiler, myResourceAllocator,
				myEnvironment);
	}

	@Test
	public void shouldBindVariable() {
		ICode code = myMocksControl.createMock(ICode.class);
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		ExpressionTIR initialization = myMocksControl
				.createMock(ExpressionTIR.class);
		IRegister register = myMocksControl.createMock(IRegister.class);
		ICode initializationCode = myMocksControl.createMock(ICode.class);

		EasyMock.expect(myResourceAllocator.createCode()).andReturn(code);
		EasyMock.expect(myResourceAllocator.nextRegister()).andReturn(register);
		EasyMock.expect(myCompiler.recurse(initialization, register))
				.andReturn(initializationCode);
		EasyMock.expect(code.append(initializationCode)).andReturn(code);
		myEnvironment.add(symbol, register);

		myMocksControl.replay();
		assertSame(code, myBinder.visitVariableDTIR(new VariableDTIR(symbol,
				initialization)));
		myMocksControl.verify();
	}

}
