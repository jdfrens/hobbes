package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.frontend.HobbesLexer;
import org.norecess.hobbes.frontend.HobbesParser;

public class HobbesPIRComponentCompilerTest {

	private static final CommonToken	PLUS_TOKEN		= new CommonToken(
																HobbesLexer.PLUS,
																"+");
	private static final CommonToken	MULTIPLY_TOKEN	= new CommonToken(
																HobbesLexer.MULTIPLY,
																"*");

	private IMocksControl				myMockControl;

	private IRegisterAllocator			myRegisterAllocator;
	private HobbesPIRComponentCompiler	myCompiler;

	@Before
	public void setUp() {
		myMockControl = EasyMock.createControl();

		myRegisterAllocator = myMockControl
				.createMock(IRegisterAllocator.class);
		myCompiler = new HobbesPIRComponentCompiler(myRegisterAllocator);
	}

	@Test
	public void shouldGenerateProlog() {
		myMockControl.replay();
		assertEquals(new Code(".sub main"), myCompiler.generateProlog(
				new Code(), createIntegerTree(5)));
		myMockControl.verify();
	}

	@Test
	public void shouldGeneratePrologForArgv() {
		myMockControl.replay();
		assertEquals(new Code(".sub main", ".param pmc argv"), myCompiler
				.generateProlog(new Code(), createArgvTree(5)));
		myMockControl.verify();
	}

	@Test
	public void shouldGeneratePrologForOp() {
		myMockControl.replay();
		assertEquals(new Code(".sub main", ".param pmc argv"), myCompiler
				.generateProlog(new Code(), createOpTree(PLUS_TOKEN,
						createArgvTree(5), createIntegerTree(9))));
		myMockControl.verify();
	}

	@Test
	public void shouldGeneratePrologForArgvAndOp() {
		myMockControl.replay();
		assertEquals(
				new Code(".sub main", ".param pmc argv", ".param pmc argv"),
				myCompiler.generateProlog(new Code(), createOpTree(PLUS_TOKEN,
						createArgvTree(5), createArgvTree(9))));
		myMockControl.verify();
	}

	@Test
	public void shouldCompileInteger() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I0");

		myMockControl.replay();
		assertEquals(//
				new Code("$I0 = 8"), //
				myCompiler.generateCode(new Code(), createIntegerTree(8)));
		myMockControl.verify();
	}

	@Test
	public void shouldCompileAnotherInteger() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I55");

		myMockControl.replay();
		assertEquals(new Code("$I55 = -1024"), //
				myCompiler.generateCode(new Code(), createIntegerTree(-1024)));
		myMockControl.verify();
	}

	@Test
	public void shouldCompilePlus() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I0");
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I33");

		myMockControl.replay();
		assertEquals(//
				new Code("$I0 = 5", //
						"$I33 = 9", //
						"$I0 += $I33"), //
				myCompiler.generateCode(new Code(), createOpTree(PLUS_TOKEN,
						createIntegerTree(5), createIntegerTree(9))));
		myMockControl.verify();
	}

	@Test
	public void shouldCompilePlusForSure() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I55");
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I11");

		myMockControl.replay();
		assertEquals(//
				new Code("$I55 = -5", //
						"$I11 = 99", //
						"$I55 += $I11"), //
				myCompiler.generateCode(new Code(), createOpTree(PLUS_TOKEN,
						createIntegerTree((-5)), createIntegerTree(99))));
		myMockControl.verify();
	}

	@Test
	public void shouldCompileMultiplication() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I0");
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I1");

		myMockControl.replay();
		assertEquals(//
				new Code("$I0 = 5", //
						"$I1 = 9", //
						"$I0 *= $I1"), //
				myCompiler.generateCode(new Code(), createOpTree(
						MULTIPLY_TOKEN, createIntegerTree(5),
						createIntegerTree(9))));
		myMockControl.verify();
	}

	@Test
	public void shouldCompileNestedExpression() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I0");
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I1");
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I2");

		myMockControl.replay();
		assertEquals(//
				new Code("$I0 = 1", //
						"$I1 = 2", //
						"$I0 *= $I1", //
						"$I2 = 3", //
						"$I0 += $I2"), //
				myCompiler.generateCode(new Code(), createOpTree(PLUS_TOKEN,
						createOpTree(MULTIPLY_TOKEN, createIntegerTree(1),
								createIntegerTree(2)), createIntegerTree(3))));
		myMockControl.verify();
	}

	@Test
	public void shouldCompileArgvReference() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I3");

		myMockControl.replay();
		assertEquals(new Code("$I3 = argv[88]"), myCompiler.generateCode(
				new Code(), createArgvTree(88)));
		myMockControl.verify();
	}

	//
	// Helpers
	//
	public static Tree createArgvTree(int i) {
		CommonTree root = new CommonTree(new CommonToken(HobbesParser.ARGV,
				"ARGV"));
		root.addChild(createIntegerTree(i));
		return root;
	}

	public static Tree createOpTree(CommonToken token, Tree left, Tree right) {
		CommonTree root = new CommonTree(token);
		root.addChild(left);
		root.addChild(right);
		return root;
	}

	public static Tree createIntegerTree(int i) {
		return new CommonTree(new CommonToken(HobbesLexer.INTEGER, String
				.valueOf(i)));
	}

}
