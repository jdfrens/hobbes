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
import org.norecess.hobbes.frontend.IHobbesFrontEnd;

public class HobbesPIRCompilerTest {

	private IMocksControl				myControl;
	private IHobbesPIRComponentCompiler	myComponentCompiler;
	private HobbesPIRCompiler			myCompiler;

	@Before
	public void setUp() {
		myControl = EasyMock.createControl();

		myComponentCompiler = myControl
				.createMock(IHobbesPIRComponentCompiler.class);

		myCompiler = new HobbesPIRCompiler(myComponentCompiler);
	}

	@Test
	public void shouldCompile() throws RecognitionException, IOException {
		IHobbesFrontEnd frontEnd = myControl.createMock(IHobbesFrontEnd.class);
		ICode code = myControl.createMock(ICode.class);
		Tree tree = myControl.createMock(Tree.class);

		expect(frontEnd.process()).andReturn(tree).atLeastOnce();
		expect(myComponentCompiler.generateProlog(code, tree)).andReturn(code);
		expect(myComponentCompiler.generateCode(code, tree)).andReturn(code);
		expect(myComponentCompiler.generateEpilog(code)).andReturn(code);

		myControl.replay();
		assertSame(code, myCompiler.compile(frontEnd, code));
		myControl.verify();
	}

}