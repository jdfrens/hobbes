package org.norecess.hobbes.compiler;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.frontend.IHobbesFrontEnd;

/*
 * This is the top-level compiler.  The component compiler does the hard work.
 */
public class HobbesPIRCompiler {

    private final IHobbesPIRComponentCompiler myComponentCompiler;

    public HobbesPIRCompiler() {
        this(new HobbesPIRComponentCompiler());
    }

    public HobbesPIRCompiler(IHobbesPIRComponentCompiler componentCompiler) {
        myComponentCompiler = componentCompiler;
    }

    public ICode<String> compile(IHobbesFrontEnd frontEnd, ICode<String> code)
            throws IOException, RecognitionException {
        myComponentCompiler.generateProlog(code);
        myComponentCompiler.generateCode(code, "$I0", frontEnd.process());
        myComponentCompiler.generateEpilog(code);
        return code;
    }

}
