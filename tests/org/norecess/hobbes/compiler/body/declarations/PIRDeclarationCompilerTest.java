package org.norecess.hobbes.compiler.body.declarations;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.ISymbol;
import org.norecess.citkit.environment.IEnvironment;
import org.norecess.citkit.tir.ExpressionTIR;
import org.norecess.citkit.tir.declarations.VariableDTIR;
import org.norecess.citkit.types.HobbesType;
import org.norecess.hobbes.backend.ICode;
import org.norecess.hobbes.compiler.body.IPIRBodySubcompiler;
import org.norecess.hobbes.compiler.resources.IRegister;
import org.norecess.hobbes.compiler.resources.IResourceAllocator;

public class PIRDeclarationCompilerTest {

	private IMocksControl			myMocksControl;

	private IPIRBodySubcompiler		myCompiler;
	private IResourceAllocator		myResourceAllocator;
	private IEnvironment<IRegister>	myEnvironment;
	private IPIRDeclarationCompiler	myRecurser;

	private PIRDeclarationCompiler	myDeclarationCompiler;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		myMocksControl = EasyMock.createControl();

		myCompiler = myMocksControl.createMock(IPIRBodySubcompiler.class);
		myResourceAllocator = myMocksControl
				.createMock(IResourceAllocator.class);
		myEnvironment = myMocksControl.createMock(IEnvironment.class);
		myRecurser = myMocksControl.createMock(IPIRDeclarationCompiler.class);

		myDeclarationCompiler = new PIRDeclarationCompiler(myCompiler,
				myResourceAllocator, myEnvironment, myRecurser);
	}

	@Test
	public void shouldCompileVariable() {
		ICode code = myMocksControl.createMock(ICode.class);
		ISymbol symbol = myMocksControl.createMock(ISymbol.class);
		ExpressionTIR initialization = myMocksControl
				.createMock(ExpressionTIR.class);
		HobbesType type = myMocksControl.createMock(HobbesType.class);
		IRegister register = myMocksControl.createMock(IRegister.class);

		EasyMock.expect(initialization.getType()).andReturn(type);
		EasyMock.expect(myResourceAllocator.nextRegister(type)).andReturn(
				register);
		myRecurser.bind(symbol, register);
		EasyMock.expect(myRecurser.compile(register, initialization))
				.andReturn(code);

		myMocksControl.replay();
		assertSame(code,
				myDeclarationCompiler.visitVariableDTIR(new VariableDTIR(
						symbol, initialization)));
		myMocksControl.verify();
	}

	@Test
	public void shouldBindVariableToRegister() {
		ISymbol variable = myMocksControl.createMock(ISymbol.class);
		IRegister register = myMocksControl.createMock(IRegister.class);

		myEnvironment.add(variable, register);

		myMocksControl.replay();
		myDeclarationCompiler.bind(variable, register);
		myMocksControl.verify();
	}

	@Test
	public void shouldCompileDeclaration() {
		IRegister register = myMocksControl.createMock(IRegister.class);
		ExpressionTIR inititialization = myMocksControl
				.createMock(ExpressionTIR.class);
		ICode code = myMocksControl.createMock(ICode.class);

		EasyMock.expect(myCompiler.recurse(inititialization, register))
				.andReturn(code);

		myMocksControl.replay();
		assertSame(code,
				myDeclarationCompiler.compile(register, inititialization));
		myMocksControl.verify();
	}

}
