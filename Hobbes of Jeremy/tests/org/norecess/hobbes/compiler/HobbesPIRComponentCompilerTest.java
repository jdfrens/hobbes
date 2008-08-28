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

public class HobbesPIRComponentCompilerTest {

    private HobbesPIRComponentCompiler myCompiler;

    @Before
    public void setUp() {
        myCompiler = new HobbesPIRComponentCompiler();
    }

    @Test
    public void shouldGenerateProlog() {
        assertEquals(new Code<String>(".sub main"), myCompiler
                .generateProlog(new Code<String>()));
    }

    @Test
    public void shouldCompileInteger() throws IOException, RecognitionException {
        assertEquals(//
                new Code<String>("$I0 = 8"), //
                myCompiler.generateCode(new Code<String>(), "$I0",
                        createTree(8)));
    }

    @Test
    public void shouldCompileAnotherInteger() throws IOException,
            RecognitionException {
        assertEquals(new Code<String>("$I55 = -1024"), //
                myCompiler.generateCode(new Code<String>(), "$I55",
                        createTree(-1024)));
    }

    @Test
    public void shouldCompilePlus() throws RecognitionException, IOException {
        assertEquals(//
                new Code<String>("$I0 = 5", //
                        "$I1 = 9", //
                        "$I0 += $I1"), //
                myCompiler.generateCode(new Code<String>(), "$I0", createTree(
                        5, 9)));
    }

    private Tree createTree(int i, int j) {
        CommonTree root = new CommonTree(new CommonToken(HobbesLexer.PLUS, "+"));
        root.addChild(createTree(i));
        root.addChild(createTree(j));
        return root;
    }

    private Tree createTree(int i) {
        return new CommonTree(new CommonToken(HobbesLexer.INTEGER, String
                .valueOf(i)));
    }

}
