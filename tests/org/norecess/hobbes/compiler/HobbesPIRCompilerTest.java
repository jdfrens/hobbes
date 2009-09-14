package org.norecess.hobbes.compiler;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertSame;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.citkit.tir.ExpressionTIR;

public class HobbesPIRCompilerTest {

	private IMocksControl				myControl;

	private IHobbesPIRPrologCompiler	myPrologCompiler;
	private IHobbesPIRComponentCompiler	myComponentCompiler;

	private HobbesPIRCompiler			myCompiler;

	@Before
	public void setUp() {
		myControl = EasyMock.createControl();

		myPrologCompiler = myControl.createMock(IHobbesPIRPrologCompiler.class);
		myComponentCompiler = myControl
				.createMock(IHobbesPIRComponentCompiler.class);

		myCompiler = new HobbesPIRCompiler(myPrologCompiler,
				myComponentCompiler);
	}

	@Test
	public void shouldCompile() throws RecognitionException, IOException {
		ExpressionTIR tir = myControl.createMock(ExpressionTIR.class);
		Tree tree = myControl.createMock(Tree.class);
		ICode code = myControl.createMock(ICode.class);

		expect(myPrologCompiler.generateProlog(code, tir)).andReturn(code);
		expect(myComponentCompiler.generateCode(code, tree)).andReturn(code);
		expect(myComponentCompiler.generateEpilog(code)).andReturn(code);

		myControl.replay();
		assertSame(code, myCompiler.compile(tir, tree, code));
		myControl.verify();
	}

}
