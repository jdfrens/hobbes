package org.norecess.hobbes.frontend;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.antlr.ANTLRTester;
import org.norecess.citkit.tir.expressions.IfETIR;
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
		myTester = new ANTLRTester(new HobbesFrontEndFactory(), "program",
				"program");
	}

	@Test
	public void shouldBuildIntegers() {
		assertEquals(new IntegerETIR(23), myTester.treeParseInput("23"));
		assertEquals(new IntegerETIR(1234), myTester.treeParseInput("1234"));
	}

	@Test
	public void shouldBuildBooleans() {
		assertEquals(new IntegerETIR(1), myTester.treeParseInput("#t"));
		assertEquals(new IntegerETIR(0), myTester.treeParseInput("#f"));
	}

	@Test
	public void shouldBuildAddition() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.ADD,
				new IntegerETIR(2)), myTester.treeParseInput("1+2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.ADD,
				new IntegerETIR(222)), myTester.treeParseInput("1055+222"));
	}

	@Test
	public void shouldBuildSubtraction() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.SUBTRACT,
				new IntegerETIR(2)), myTester.treeParseInput("1-2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.SUBTRACT,
				new IntegerETIR(222)), myTester.treeParseInput("1055-222"));
	}

	@Test
	public void shouldBuildUnaryMinus() {
		assertEquals(new IntegerETIR(-5), myTester.treeParseInput("-5"));
		assertEquals(new IntegerETIR(-1223), myTester.treeParseInput("-1223"));
	}

	@Test
	public void shouldBuildMultiplication() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.MULTIPLY,
				new IntegerETIR(2)), myTester.treeParseInput("1*2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.MULTIPLY,
				new IntegerETIR(222)), myTester.treeParseInput("1055*222"));
	}

	@Test
	public void shouldBuildDivision() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.DIVIDE,
				new IntegerETIR(2)), myTester.treeParseInput("1/2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.DIVIDE,
				new IntegerETIR(222)), myTester.treeParseInput("1055/222"));
	}

	@Test
	public void shouldBuildModulus() {
		assertEquals(new OperatorETIR(new IntegerETIR(1), Operator.MODULUS,
				new IntegerETIR(2)), myTester.treeParseInput("1%2"));
		assertEquals(new OperatorETIR(new IntegerETIR(1055), Operator.MODULUS,
				new IntegerETIR(222)), myTester.treeParseInput("1055%222"));
	}

	@Test
	public void shouldBuildCommandLineArgumentRequest() {
		assertEquals(new VariableETIR(new SubscriptLValueTIR(
				new SimpleLValueTIR("ARGV"), new IntegerETIR(1))), //
				myTester.treeParseInput("ARGV[1]"));
		assertEquals(new VariableETIR(new SubscriptLValueTIR(
				new SimpleLValueTIR("ARGV"), new IntegerETIR(8))), //
				myTester.treeParseInput("ARGV[8]"));
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
				myTester.treeParseInput("ARGV[1] + ARGV[8]"));
	}

	@Test
	public void shouldBuildIfExpressions() {
		assertEquals(new IfETIR(new IntegerETIR(1), new IntegerETIR(2),
				new IntegerETIR(3)), myTester
				.treeParseInput("if #t then 2 else 3 end"));
		assertEquals(new IfETIR(new IntegerETIR(0), new OperatorETIR(
				new IntegerETIR(2), Operator.ADD, new IntegerETIR(8)),
				new OperatorETIR(new IntegerETIR(3), Operator.MULTIPLY,
						new IntegerETIR(4))), myTester
				.treeParseInput("if #f then 2+8 else 3*4 end"));
	}
}
