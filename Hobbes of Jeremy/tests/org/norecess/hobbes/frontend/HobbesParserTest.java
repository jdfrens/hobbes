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
    public void shouldParseAnAddition() {
        assertTree(HobbesParser.PLUS, "(+(1)(2))", myTester.scanInput("1+2")
                .parseAs("program"));
        assertTree(HobbesParser.PLUS, "(+(1055)(222))", myTester.scanInput(
                "1055+222").parseAs("program"));
        assertTree(HobbesParser.PLUS, "(+(-1)(-32))", myTester.scanInput(
                "-1+-32").parseAs("program"));
    }
}
