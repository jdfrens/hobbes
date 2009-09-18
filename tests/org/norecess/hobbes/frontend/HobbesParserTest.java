package org.norecess.hobbes.frontend;

import static org.norecess.antlr.Assert.assertTree;

import org.junit.Before;
import org.junit.Test;
import org.norecess.antlr.ANTLRTester;

public class HobbesParserTest {

	private ANTLRTester	myTester;

	@Before
	public void setUp() {
		myTester = new ANTLRTester(new HobbesFrontEndFactory(), "program");
	}

	@Test
	public void shouldParseAnInteger() {
		assertTree(HobbesParser.INTEGER, "(23)", myTester.parseInput("23"));
		assertTree(HobbesParser.INTEGER, "(1234)", myTester.parseInput("1234"));
	}

	@Test
	public void shouldParseBooleans() {
		assertTree(HobbesParser.BOOLEAN, "(#t)", myTester.parseInput("#t"));
		assertTree(HobbesParser.BOOLEAN, "(#f)", myTester.parseInput("#f"));
	}

	@Test
	public void shouldParseAddition() {
		assertTree(HobbesParser.PLUS, "(+(1)(2))", myTester.parseInput("1+2"));
		assertTree(HobbesParser.PLUS, "(+(1055)(222))", myTester
				.parseInput("1055+222"));
	}

	@Test
	public void shouldParseSubtraction() {
		assertTree(HobbesParser.MINUS, "(-(1)(2))", myTester.parseInput("1-2"));
		assertTree(HobbesParser.MINUS, "(-(1055)(222))", myTester
				.parseInput("1055-222"));
		assertTree(HobbesParser.MINUS, "(-(888)(1442))", myTester
				.parseInput("888-1442"));
	}

	@Test
	public void shouldRecognizeUnaryMinus() {
		assertTree(HobbesLexer.MINUS, "(-(5))", myTester.parseInput("-5"));
		assertTree(HobbesLexer.MINUS, "(-(123))", myTester.parseInput("-123"));
		assertTree(HobbesLexer.MINUS, "(-(665))", myTester.parseInput("-665"));
	}

	@Test
	public void shouldParseMultiplication() {
		assertTree(HobbesParser.MULTIPLY, "(*(1)(2))", myTester
				.parseInput("1*2"));
		assertTree(HobbesParser.MULTIPLY, "(*(1055)(222))", myTester
				.parseInput("1055*222"));
		assertTree(HobbesParser.MULTIPLY, "(*(888)(32))", myTester
				.parseInput("888*32"));
	}

	@Test
	public void shouldParseDivision() {
		assertTree(HobbesParser.DIVIDE, "(/(1)(2))", myTester.parseInput("1/2"));
		assertTree(HobbesParser.DIVIDE, "(/(1055)(222))", myTester
				.parseInput("1055/222"));
		assertTree(HobbesParser.DIVIDE, "(/(888)(32))", myTester
				.parseInput("888/32"));
	}

	@Test
	public void shouldParseModulus() {
		assertTree(HobbesParser.MODULUS, "(%(1)(2))", myTester
				.parseInput("1%2"));
		assertTree(HobbesParser.MODULUS, "(%(1055)(222))", myTester
				.parseInput("1055%222"));
		assertTree(HobbesParser.MODULUS, "(%(888)(32))", myTester
				.parseInput("888%32"));
	}

	@Test
	public void shouldParseLeftAssociativeExpressions() {
		assertTree(HobbesParser.PLUS, "(+(+(8)(12))(32))", myTester
				.parseInput("8+12+32"));
		assertTree(HobbesParser.PLUS, "(+(+(+(+(1)(5))(8))(12))(32))", myTester
				.parseInput("1+5+8+12+32"));
	}

	@Test
	public void shouldParseForPrecedence() {
		assertTree(HobbesParser.PLUS, "(+(8)(*(12)(32)))", myTester
				.parseInput("8+12*32"));
		assertTree(HobbesParser.PLUS, "(+(*(8)(12))(32))", myTester
				.parseInput("8*12+32"));
	}

	@Test
	public void shouldParseParentheses() {
		assertTree(HobbesParser.MULTIPLY, "(*(+(8)(12))(32))", myTester
				.parseInput("(8+12)*32"));
		assertTree(HobbesParser.MULTIPLY, "(*(8)(+(12)(32)))", myTester
				.parseInput("8*(12+32)"));
	}

	@Test
	public void shouldParseACommandLineArgumentRequest() {
		assertTree(HobbesParser.ARGV, "(ARGV(1))", myTester
				.parseInput("ARGV[1]"));
		assertTree(HobbesParser.ARGV, "(ARGV(8))", myTester
				.parseInput("ARGV[8]"));
		assertTree(HobbesParser.ARGV, "(ARGV(100))", myTester
				.parseInput("ARGV[100]"));
	}

	@Test
	public void shouldParseCommandLineArgumentInPlus() {
		assertTree(HobbesParser.PLUS, "(+(ARGV(1))(ARGV(8)))", myTester
				.parseInput("ARGV[1] + ARGV[8]"));
	}

	@Test
	public void shouldParseIfExpressions() {
		assertTree(HobbesParser.IF, "(IF(#t)(2)(3))", myTester
				.parseInput("if #t then 2 else 3 end"));
		assertTree(HobbesParser.IF, "(IF(#f)(+(1)(2))(*(3)(4)))", myTester
				.parseInput("if #f then 1 + 2 else 3 * 4 end"));
	}
}
