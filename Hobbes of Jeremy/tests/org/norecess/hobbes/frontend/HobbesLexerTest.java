package org.norecess.hobbes.frontend;

import static org.norecess.antlr.Assert.*;

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

    // TODO: test bad integers (like "123x5")
}
