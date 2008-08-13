package org.norecess.hobbes.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;

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

    public int process() {
        return Integer.parseInt(new HobbesLexer(myInputFile).nextToken()
                .getText());
    }

}
