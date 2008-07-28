package org.norecess.hobbes.frontend;

import org.antlr.runtime.ANTLRStringStream;

public class HobbesFrontEnd implements IHobbesFrontEnd {

    private final ANTLRStringStream myInputFile;

    public HobbesFrontEnd(ANTLRStringStream myInputStream) {
        myInputFile = myInputStream;
    }

    public int process() {
        return Integer.parseInt(new HobbesLexer(myInputFile).nextToken()
                .getText());
    }

}
