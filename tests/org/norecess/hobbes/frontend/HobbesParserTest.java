package org.norecess.hobbes.frontend;

import static org.norecess.antlr.Assert.assertTree;

import org.junit.Before;
import org.junit.Test;
import org.norecess.antlr.ANTLRTester;

public class HobbesParserTest {

	private ANTLRTester	myTester;

	@Before
	public void setUp() {
		myTester = new ANTLRTester(new HobbesFrontEndFactory());
	}

	@Test
	public void shouldParseAnInteger() {
		assertTree(HobbesParser.INTEGER, "(23)", myTester.scanInput("23")
				.parseAs("program"));
		assertTree(HobbesParser.INTEGER, "(1234)", myTester.scanInput("1234")
				.parseAs("program"));
	}

	@Test
	public void shouldParseBooleans() {
		assertTree(HobbesParser.BOOLEAN, "(#t)", myTester.scanInput("#t")
				.parseAs("program"));
		assertTree(HobbesParser.BOOLEAN, "(#f)", myTester.scanInput("#f")
				.parseAs("program"));
	}

	@Test
	public void shouldParseAddition() {
		assertTree(HobbesParser.PLUS, "(+(1)(2))", myTester.scanInput("1+2")
				.parseAs("program"));
		assertTree(HobbesParser.PLUS, "(+(1055)(222))", myTester.scanInput(
				"1055+222").parseAs("program"));
	}

	@Test
	public void shouldParseSubtraction() {
		assertTree(HobbesParser.MINUS, "(-(1)(2))", myTester.scanInput("1-2")
				.parseAs("program"));
		assertTree(HobbesParser.MINUS, "(-(1055)(222))", myTester.scanInput(
				"1055-222").parseAs("program"));
		assertTree(HobbesParser.MINUS, "(-(888)(1442))", myTester.scanInput(
				"888-1442").parseAs("program"));
	}

	@Test
	public void shouldRecognizeUnaryMinus() {
		assertTree(HobbesLexer.MINUS, "(-(5))", myTester.scanInput("-5")
				.parseAs("program"));
		assertTree(HobbesLexer.MINUS, "(-(123))", myTester.scanInput("-123")
				.parseAs("program"));
		assertTree(HobbesLexer.MINUS, "(-(665))", myTester.scanInput("-665")
				.parseAs("program"));
	}

	@Test
	public void shouldParseMultiplication() {
		assertTree(HobbesParser.MULTIPLY, "(*(1)(2))", myTester
				.scanInput("1*2").parseAs("program"));
		assertTree(HobbesParser.MULTIPLY, "(*(1055)(222))", myTester.scanInput(
				"1055*222").parseAs("program"));
		assertTree(HobbesParser.MULTIPLY, "(*(888)(32))", myTester.scanInput(
				"888*32").parseAs("program"));
	}

	@Test
	public void shouldParseDivision() {
		assertTree(HobbesParser.DIVIDE, "(/(1)(2))", myTester.scanInput("1/2")
				.parseAs("program"));
		assertTree(HobbesParser.DIVIDE, "(/(1055)(222))", myTester.scanInput(
				"1055/222").parseAs("program"));
		assertTree(HobbesParser.DIVIDE, "(/(888)(32))", myTester.scanInput(
				"888/32").parseAs("program"));
	}

	@Test
	public void shouldParseModulus() {
		assertTree(HobbesParser.MODULUS, "(%(1)(2))", myTester.scanInput("1%2")
				.parseAs("program"));
		assertTree(HobbesParser.MODULUS, "(%(1055)(222))", myTester.scanInput(
				"1055%222").parseAs("program"));
		assertTree(HobbesParser.MODULUS, "(%(888)(32))", myTester.scanInput(
				"888%32").parseAs("program"));
	}

	@Test
	public void shouldParseLeftAssociativeExpressions() {
		assertTree(HobbesParser.PLUS, "(+(+(8)(12))(32))", myTester.scanInput(
				"8+12+32").parseAs("program"));
		assertTree(HobbesParser.PLUS, "(+(+(+(+(1)(5))(8))(12))(32))", myTester
				.scanInput("1+5+8+12+32").parseAs("program"));
	}

	@Test
	public void shouldParseForPrecedence() {
		assertTree(HobbesParser.PLUS, "(+(8)(*(12)(32)))", myTester.scanInput(
				"8+12*32").parseAs("program"));
		assertTree(HobbesParser.PLUS, "(+(*(8)(12))(32))", myTester.scanInput(
				"8*12+32").parseAs("program"));
	}

	@Test
	public void shouldParseParentheses() {
		assertTree(HobbesParser.MULTIPLY, "(*(+(8)(12))(32))", myTester
				.scanInput("(8+12)*32").parseAs("program"));
		assertTree(HobbesParser.MULTIPLY, "(*(8)(+(12)(32)))", myTester
				.scanInput("8*(12+32)").parseAs("program"));
	}

	@Test
	public void shouldParseACommandLineArgumentRequest() {
		assertTree(HobbesParser.ARGV, "(ARGV(1))", myTester
				.scanInput("ARGV[1]").parseAs("program"));
		assertTree(HobbesParser.ARGV, "(ARGV(8))", myTester
				.scanInput("ARGV[8]").parseAs("program"));
		assertTree(HobbesParser.ARGV, "(ARGV(100))", myTester.scanInput(
				"ARGV[100]").parseAs("program"));
	}

	@Test
	public void shouldParseCommandLineArgumentInPlus() {
		assertTree(HobbesParser.PLUS, "(+(ARGV(1))(ARGV(8)))", myTester
				.scanInput("ARGV[1] + ARGV[8]").parseAs("program"));
	}
}
