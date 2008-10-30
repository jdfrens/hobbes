package org.norecess.hobbes.drivers.targetcode;

import java.io.IOException;

import org.norecess.hobbes.compiler.ICode;

/*
 * Writes out the instructions for PIR.  Mostly this means putting a tab
 * in front of the real instructions.
 */
public class PIRWriter {

    private final Appendable myWriter;

    public PIRWriter(Appendable writer) {
        myWriter = writer;
    }

    public Appendable getWriter() {
        return myWriter;
    }

    public void writeCode(ICode<String> code) throws IOException {
        for (String instruction : code) {
            if (instruction.startsWith(".sub")
                    || instruction.startsWith(".end")) {
            } else {
                getWriter().append("\t");
            }
            getWriter().append(instruction).append("\n");
        }
    }

}
