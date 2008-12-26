package org.norecess.hobbes.frontend;

import static org.norecess.antlr.Assert.assertTree;

import org.junit.Before;
import org.junit.Test;
import org.norecess.antlr.ANTLRTester;

public class HobbesParserTest {

    private ANTLRTester myTester;

    @Before
    public void setUp() {
        myTester = new ANTLRTester(new HobbesFrontEndFactory());
    }

    @Test
    public void shouldParseAnInteger() {
        assertTree(HobbesParser.INTEGER, "(23)", myTester.scanInput("23")
                .parseAs("program"));
        assertTree(HobbesParser.INTEGER, "(-23)", myTester.scanInput("-23")
                .parseAs("program"));
        assertTree(HobbesParser.INTEGER, "(1234)", myTester.scanInput("1234")
                .parseAs("program"));
    }

    @Test
    public void shouldParseAnAddition() {
        assertTree(HobbesParser.PLUS, "(+(1)(2))", myTester.scanInput("1+2")
                .parseAs("program"));
        assertTree(HobbesParser.PLUS, "(+(1055)(222))", myTester.scanInput(
                "1055+222").parseAs("program"));
        assertTree(HobbesParser.PLUS, "(+(-1)(-32))", myTester.scanInput(
                "-1+-32").parseAs("program"));
    }

    @Test
    public void shouldParseAMultiplication() {
        assertTree(HobbesParser.MULTIPLY, "(*(1)(2))", myTester
                .scanInput("1*2").parseAs("program"));
        assertTree(HobbesParser.MULTIPLY, "(*(1055)(222))", myTester.scanInput(
                "1055*222").parseAs("program"));
        assertTree(HobbesParser.MULTIPLY, "(*(-1)(-32))", myTester.scanInput(
                "-1*-32").parseAs("program"));
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
