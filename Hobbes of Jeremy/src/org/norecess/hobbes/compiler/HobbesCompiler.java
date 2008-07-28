package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.norecess.hobbes.frontend.IHobbesFrontEnd;

public class HobbesCompiler {

    public HobbesCompiler() {
    }

    public Appendable compile(IHobbesFrontEnd frontEnd, Appendable appendable)
            throws IOException {
        appendable.append(".sub main\n");
        appendable.append("\tprint " + frontEnd.process() + "\n");
        appendable.append("\tprint \"\\n\"\n");
        appendable.append(".end\n");
        return appendable;
    }

}
