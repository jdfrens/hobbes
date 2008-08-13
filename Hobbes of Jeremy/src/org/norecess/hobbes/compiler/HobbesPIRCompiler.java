package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.norecess.hobbes.frontend.IHobbesFrontEnd;

public class HobbesPIRCompiler {

    public HobbesPIRCompiler() {
    }

    public ICode<String> compile(IHobbesFrontEnd frontEnd, ICode<String> code)
            throws IOException {
        code.add(".sub main");
        code.add("print " + frontEnd.process());
        code.add("print \"\\n\"");
        code.add(".end");
        return code;
    }

}
