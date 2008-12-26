package org.norecess.hobbes.frontend;

import static org.norecess.antlr.Assert.assertToken;
import static org.norecess.antlr.Assert.refuteToken;

import org.junit.Before;
import org.junit.Test;
import org.norecess.antlr.ANTLRTester;

public class HobbesLexerTest {

    private ANTLRTester myTester;

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
    public void shouldRecognizeNegativeIntegers() {
        assertToken(HobbesLexer.INTEGER, "-5", myTester.scanInput("-5"));
        assertToken(HobbesLexer.INTEGER, "-123", myTester.scanInput("-123"));
        assertToken(HobbesLexer.INTEGER, "-665", myTester.scanInput("-665"));
    }

    @Test
    public void shouldNotRecognizeAsIntegers() {
        refuteToken(HobbesLexer.INTEGER, myTester.scanInput("123x5"));
        refuteToken(HobbesLexer.INTEGER, myTester.scanInput("xyz"));
        refuteToken(HobbesLexer.INTEGER, myTester.scanInput("+"));
    }

    @Test
    public void shouldRecognizeOperators() {
        assertToken(HobbesLexer.PLUS, "+", myTester.scanInput("+"));
        assertToken(HobbesLexer.MULTIPLY, "*", myTester.scanInput("*"));
    }

    @Test
    public void shouldIgnoreWhitespace() {
        assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput(" 5"));
        assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("\t5"));
        assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("\n5"));
        assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("5 "));
        assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("5\t"));
        assertToken(HobbesLexer.INTEGER, "5", myTester.scanInput("5\n"));
        assertToken(HobbesLexer.INTEGER, "5", myTester
                .scanInput("  \t  5\n\n \t"));
    }
}
