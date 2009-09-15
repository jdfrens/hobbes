package org.norecess.hobbes.backend;

import java.io.IOException;


/*
 * Writes out the instructions for PIR.  Mostly this means putting a tab
 * in front of the real instructions.
 */
public class CodeWriter {

    private final Appendable myWriter;

    public CodeWriter(Appendable writer) {
        myWriter = writer;
    }

    public Appendable getWriter() {
        return myWriter;
    }

    public void writeCode(ICode code) throws IOException {
        for (String instruction : code) {
            getWriter().append(instruction).append("\n");
        }
    }
}
