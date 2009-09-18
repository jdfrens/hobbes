package org.norecess.hobbes.frontend;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.antlr.ANTLRTester;
import org.norecess.citkit.tir.expressions.IntegerETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR;
import org.norecess.citkit.tir.expressions.VariableETIR;
import org.norecess.citkit.tir.expressions.OperatorETIR.Operator;
import org.norecess.citkit.tir.lvalues.SimpleLValueTIR;
import org.norecess.citkit.tir.lvalues.SubscriptLValueTIR;

public class HobbesTIRBuilderTest {

	private ANTLRTester	myTester;

	@Before
	public void setUp() {
		myTester = new ANTLRTester(new HobbesFrontEndFactory());
	}

	@Test
	public void shouldBuildIntegers() {
		assertEquals(new IntegerETIR(23), myTester.scanInput("23").parseAs(
				"program").treeParseAs("program"));
		assertEquals(new IntegerETIR(1234), myTester.scanInput("1234").parseAs(
				"program").treeParseAs("program"));
	}

	@Test
	public void shouldBuildBooleans() {
		assertEquals(new IntegerETIR(1), myTester.scanInput("#t").parseAs(
				"program").treeParseAs("program"));
		assertEquals(new IntegerETIR(0), myTester.scanInput("#f").parseAs(
				"program").treeParseAs("program"));
	}

	@Test
	public void shouldBuildAddition() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.ADD,
				new IntegerETIR(2)), myTester.scanInput("1+2").parseAs(
				"program").treeParseAs("program"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.ADD,
				new IntegerETIR(222)), myTester.scanInput("1055+222").parseAs(
				"program").treeParseAs("program"));
	}

	@Test
	public void shouldBuildSubtraction() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.SUBTRACT,
				new IntegerETIR(2)), myTester.scanInput("1-2").parseAs(
				"program").treeParseAs("program"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.SUBTRACT,
				new IntegerETIR(222)), myTester.scanInput("1055-222").parseAs(
				"program").treeParseAs("program"));
	}

	@Test
	public void shouldBuildUnaryMinus() {
		assertEquals(new IntegerETIR(-5), myTester.scanInput("-5").parseAs(
				"program").treeParseAs("program"));
		assertEquals(new IntegerETIR(-1223), myTester.scanInput("-1223")
				.parseAs("program").treeParseAs("program"));
	}

	@Test
	public void shouldBuildMultiplication() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.MULTIPLY,
				new IntegerETIR(2)), myTester.scanInput("1*2").parseAs(
				"program").treeParseAs("program"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.MULTIPLY,
				new IntegerETIR(222)), myTester.scanInput("1055*222").parseAs(
				"program").treeParseAs("program"));
	}

	@Test
	public void shouldBuildDivision() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.DIVIDE,
				new IntegerETIR(2)), myTester.scanInput("1/2").parseAs(
				"program").treeParseAs("program"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.DIVIDE,
				new IntegerETIR(222)), myTester.scanInput("1055/222").parseAs(
				"program").treeParseAs("program"));
	}

	@Test
	public void shouldBuildModulus() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.MODULUS,
				new IntegerETIR(2)), myTester.scanInput("1%2").parseAs(
				"program").treeParseAs("program"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.MODULUS,
				new IntegerETIR(222)), myTester.scanInput("1055%222").parseAs(
				"program").treeParseAs("program"));
	}

	@Test
	public void shouldBuildCommandLineArgumentRequest() {
		assertEquals(new VariableETIR(new SubscriptLValueTIR(
				new SimpleLValueTIR("ARGV"), new IntegerETIR(1))), //
				myTester.scanInput("ARGV[1]").parseAs("program").treeParseAs(
						"program"));
		assertEquals(new VariableETIR(new SubscriptLValueTIR(
				new SimpleLValueTIR("ARGV"), new IntegerETIR(8))), //
				myTester.scanInput("ARGV[8]").parseAs("program").treeParseAs(
						"program"));
	}

	@Test
	public void shouldBuildCommandLineArgumentInPlus() {
		assertEquals(new OperatorETIR(
		//
				new VariableETIR(new SubscriptLValueTIR(new SimpleLValueTIR(
						"ARGV"), new IntegerETIR(1))), //
				Operator.ADD,//
				new VariableETIR(new SubscriptLValueTIR(new SimpleLValueTIR(
						"ARGV"), new IntegerETIR(8)))), //
				myTester.scanInput("ARGV[1] + ARGV[8]").parseAs("program")
						.treeParseAs("program"));
	}
}
