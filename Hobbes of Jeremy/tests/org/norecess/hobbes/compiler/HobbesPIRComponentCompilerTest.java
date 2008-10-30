package org.norecess.hobbes.compiler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.frontend.HobbesLexer;
import org.norecess.hobbes.frontend.HobbesParser;

public class HobbesPIRComponentCompilerTest {

    private HobbesPIRComponentCompiler myCompiler;

    @Before
    public void setUp() {
        myCompiler = new HobbesPIRComponentCompiler();
    }

    @Test
    public void shouldGenerateProlog() {
        assertEquals(new Code(".sub main"), myCompiler.generateProlog(
                new Code(), createIntegerTree(5)));
        assertEquals(new Code(".sub main", ".param pmc argv"), myCompiler
                .generateProlog(new Code(), createArgvTree(5)));
        assertEquals(new Code(".sub main", ".param pmc argv"), myCompiler
                .generateProlog(new Code(), createPlusTree(createArgvTree(5),
                        createIntegerTree(9))));
        assertEquals(new Code(".sub main", ".param pmc argv"), myCompiler
                .generateProlog(new Code(), createPlusTree(
                        createIntegerTree(5), createArgvTree(9))));
        assertEquals(
                new Code(".sub main", ".param pmc argv", ".param pmc argv"),
                myCompiler.generateProlog(new Code(), createPlusTree(
                        createArgvTree(5), createArgvTree(9))));
    }

    @Test
    public void shouldCompileInteger() throws IOException, RecognitionException {
        assertEquals(//
                new Code("$I0 = 8"), //
                myCompiler
                        .generateCode(new Code(), "$I0", createIntegerTree(8)));
    }

    @Test
    public void shouldCompileAnotherInteger() throws IOException,
            RecognitionException {
        assertEquals(new Code("$I55 = -1024"), //
                myCompiler.generateCode(new Code(), "$I55",
                        createIntegerTree(-1024)));
    }

    @Test
    public void shouldCompilePlus() throws RecognitionException, IOException {
        assertEquals(//
                new Code("$I0 = 5", //
                        "$I1 = 9", //
                        "$I0 += $I1"), //
                myCompiler.generateCode(new Code(), "$I0", createPlusTree(
                        createIntegerTree(5), createIntegerTree(9))));
        assertEquals(//
                new Code("$I0 = -5", //
                        "$I1 = 99", //
                        "$I0 += $I1"), //
                myCompiler.generateCode(new Code(), "$I0", createPlusTree(
                        createIntegerTree((-5)), createIntegerTree(99))));
    }

    @Test
    public void shouldCompileArgvReference() {
        assertEquals(new Code("$I0 = argv[1]"), myCompiler.generateCode(
                new Code(), "$I0", createArgvTree(1)));
        assertEquals(new Code("$I3 = argv[1]"), myCompiler.generateCode(
                new Code(), "$I3", createArgvTree(1)));
        assertEquals(new Code("$I3 = argv[88]"), myCompiler.generateCode(
                new Code(), "$I3", createArgvTree(88)));
    }

    private Tree createArgvTree(int i) {
        CommonTree root = new CommonTree(new CommonToken(HobbesParser.ARGV,
                "ARGV"));
        root.addChild(createIntegerTree(i));
        return root;
    }

    private Tree createPlusTree(Tree left, Tree right) {
        CommonTree root = new CommonTree(new CommonToken(HobbesLexer.PLUS, "+"));
        root.addChild(left);
        root.addChild(right);
        return root;
    }

    private Tree createIntegerTree(int i) {
        return new CommonTree(new CommonToken(HobbesLexer.INTEGER, String
                .valueOf(i)));
    }

}
