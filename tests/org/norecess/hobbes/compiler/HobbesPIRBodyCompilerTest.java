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

public class HobbesPIRBodyCompilerTest {

	public static final CommonToken	PLUS_TOKEN		= new CommonToken(
															HobbesLexer.PLUS,
															"+");
	public static final CommonToken	MINUS_TOKEN		= new CommonToken(
															HobbesLexer.MINUS,
															"-");
	public static final CommonToken	MULTIPLY_TOKEN	= new CommonToken(
															HobbesLexer.MULTIPLY,
															"*");

	private IMocksControl			myMockControl;

	private IRegisterAllocator		myRegisterAllocator;
	private HobbesPIRBodyCompiler	myCompiler;

	@Before
	public void setUp() {
		myMockControl = EasyMock.createControl();

		myRegisterAllocator = myMockControl
				.createMock(IRegisterAllocator.class);
		myCompiler = new HobbesPIRBodyCompiler(myRegisterAllocator);
	}

	@Test
	public void shouldCompileInteger() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I0");

		myMockControl.replay();
		assertEquals(//
				new Code("$I0 = 8"), //
				myCompiler.generate(new Code(), createIntegerTree(8)));
		myMockControl.verify();
	}

	@Test
	public void shouldCompileAnotherInteger() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I55");

		myMockControl.replay();
		assertEquals(new Code("$I55 = 1024"), //
				myCompiler.generate(new Code(), createIntegerTree(1024)));
		myMockControl.verify();
	}

	@Test
	public void shouldCompileUnaryMinus() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I6");

		myMockControl.replay();
		assertEquals(new Code("$I6 = 1024", "$I6 *= -1"), //
				myCompiler.generate(new Code(), createOpTree(MINUS_TOKEN,
						createIntegerTree(1024))));
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
				myCompiler.generate(new Code(), createOpTree(PLUS_TOKEN,
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
				myCompiler.generate(new Code(), createOpTree(PLUS_TOKEN,
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
				myCompiler.generate(new Code(), createOpTree(MULTIPLY_TOKEN,
						createIntegerTree(5), createIntegerTree(9))));
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
				myCompiler.generate(new Code(), createOpTree(PLUS_TOKEN,
						createOpTree(MULTIPLY_TOKEN, createIntegerTree(1),
								createIntegerTree(2)), createIntegerTree(3))));
		myMockControl.verify();
	}

	@Test
	public void shouldCompileAnotherNestedExpression() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I0");
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I1");
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I2");

		myMockControl.replay();
		assertEquals(//
				new Code("$I0 = 1", //
						"$I1 = 2", //
						"$I2 = 3", //
						"$I1 *= $I2", //
						"$I0 += $I1"), //
				myCompiler.generate(new Code(), createOpTree(PLUS_TOKEN,
						createIntegerTree(1), createOpTree(MULTIPLY_TOKEN,
								createIntegerTree(2), createIntegerTree(3)))));
		myMockControl.verify();
	}

	@Test
	public void shouldCompileArgvReference() {
		EasyMock.expect(myRegisterAllocator.next()).andReturn("$I3");

		myMockControl.replay();
		assertEquals(new Code("$I3 = argv[88]"), myCompiler.generate(
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

	public static Tree createOpTree(CommonToken token, Tree left) {
		CommonTree root = new CommonTree(token);
		root.addChild(left);
		return root;
	}

	public static Tree createIntegerTree(int i) {
		return new CommonTree(new CommonToken(HobbesLexer.INTEGER, String
				.valueOf(i)));
	}

}