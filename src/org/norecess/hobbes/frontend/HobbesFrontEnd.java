package org.norecess.hobbes.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

/*
 * Provides an front end for the front-end testers and compilers.
 */
public class HobbesFrontEnd implements IHobbesFrontEnd {

    private final ANTLRStringStream myInputFile;

    public HobbesFrontEnd(ANTLRStringStream myInputStream) {
        myInputFile = myInputStream;
    }

    public HobbesFrontEnd(File file) throws IOException {
        myInputFile = new ANTLRInputStream(new FileInputStream(file));
    }

    public Tree process() throws RecognitionException {
        return new HobbesParser(new CommonTokenStream(new HobbesLexer(
                myInputFile))).program().tree;
    }
}
