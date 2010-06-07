package org.norecess.hobbes.frontend;

import static org.norecess.antlr.Assert.assertToken;
import static org.norecess.antlr.Assert.refuteToken;

import org.junit.Before;
import org.junit.Test;
import org.norecess.antlr.ANTLRTester;

public class HobbesLexerTest {

	private ANTLRTester	myTester;

	@Before
	public void setUp() {
		myTester = new ANTLRTester(new HobbesFrontEndFactory());
	}

	@Test
	public void shouldRecognizeIntegers() {
		assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("5"));
		assertToken(HobbesLexer.INTEGER, "123", myTester.scanInput("123"));
		assertToken(HobbesLexer.INTEGER, "665", myTester.scanInput("665"));
	}

	@Test
	public void shouldNotRecognizeAsIntegers() {
		refuteToken(HobbesLexer.INTEGER, myTester.scanInput("123x5"));
		refuteToken(HobbesLexer.INTEGER, myTester.scanInput("xyz"));
		refuteToken(HobbesLexer.INTEGER, myTester.scanInput("+"));
	}

	@Test
	public void shouldRecognizeAsFloat() {
		assertToken(HobbesLexer.FLOAT, "5.0", myTester.scanInput("5.0"));
		assertToken(HobbesLexer.FLOAT, "3.14159", myTester.scanInput("3.14159"));
		assertToken(HobbesLexer.FLOAT, "0.001234",
				myTester.scanInput("0.001234"));
	}

	@Test
	public void shouldNotRecognizeAsFloat() {
		refuteToken(HobbesLexer.FLOAT, myTester.scanInput(".123"));
		refuteToken(HobbesLexer.FLOAT, myTester.scanInput("5."));
	}

	@Test
	public void shouldRecognizeBooleans() {
		assertToken(HobbesLexer.BOOLEAN, "#f", myTester.scanInput("#f"));
		assertToken(HobbesLexer.BOOLEAN, "#t", myTester.scanInput("#t"));
	}

	@Test
	public void shouldNotRecognizeAsBooleans() {
		refuteToken(HobbesLexer.BOOLEAN, myTester.scanInput("#a"));
		refuteToken(HobbesLexer.BOOLEAN, myTester.scanInput("#true"));
		refuteToken(HobbesLexer.BOOLEAN, myTester.scanInput("#false"));
	}

	@Test
	public void shouldRecognizeIdentifiers() {
		assertToken(HobbesParser.IDENTIFIER, "foo", myTester.scanInput("foo"));
		assertToken(HobbesParser.IDENTIFIER, "x", myTester.scanInput("x"));
		assertToken(HobbesParser.IDENTIFIER, "f592", myTester.scanInput("f592"));
	}

	@Test
	public void shouldNotRecognizeAsIdentifiers() {
		refuteToken(HobbesParser.IDENTIFIER, myTester.scanInput("89x"));
	}

	@Test
	public void shouldRecognizeArithmeticOperators() {
		assertToken(HobbesLexer.PLUS, "+", myTester.scanInput("+"));
		assertToken(HobbesLexer.MINUS, "-", myTester.scanInput("-"));
		assertToken(HobbesLexer.MULTIPLY, "*", myTester.scanInput("*"));
		assertToken(HobbesLexer.DIVIDE, "/", myTester.scanInput("/"));
		assertToken(HobbesLexer.MODULUS, "%", myTester.scanInput("%"));
	}

	@Test
	public void shouldRecognizeComparisonOperators() {
		assertToken(HobbesLexer.LT, "<", myTester.scanInput("<"));
		assertToken(HobbesLexer.LTE, "<=", myTester.scanInput("<="));
		assertToken(HobbesLexer.EQ, "==", myTester.scanInput("=="));
		assertToken(HobbesLexer.NEQ, "!=", myTester.scanInput("!="));
		assertToken(HobbesLexer.GTE, ">=", myTester.scanInput(">="));
		assertToken(HobbesLexer.GT, ">", myTester.scanInput(">"));
	}

	@Test
	public void shouldRecognizeLogicOperators() {
		assertToken(HobbesLexer.AND, "&", myTester.scanInput("&"));
		assertToken(HobbesLexer.OR, "|", myTester.scanInput("|"));
	}

	@Test
	public void shouldIgnoreWhitespace() {
		assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput(" 5"));
		assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("\t5"));
		assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("\n5"));
		assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("5 "));
		assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("5\t"));
		assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("5\n"));
		assertToken(HobbesLexer.INTEGER, "5",
				myTester.scanInput("  \t  5\n\n \t"));
	}
}
