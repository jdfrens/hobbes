package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.runtime.RecognitionException;
import org.norecess.hobbes.compiler.Code;
import org.norecess.hobbes.compiler.HobbesPIRCompiler;
import org.norecess.hobbes.drivers.targetcode.PIRWriter;
import org.norecess.hobbes.frontend.HobbesFrontEnd;

/**
 * The main driver. Usage: java org.norecess.hobbes.drivers.PIRCompiler
 * infile.hob outfile.pir
 */
public class PIRCompiler {

    private final HobbesFrontEnd myFrontEnd;
    private final PrintWriter    myWriter;

    public PIRCompiler(String sourceFile, String targetFile) throws IOException {
        myFrontEnd = new HobbesFrontEnd(new File(sourceFile));
        myWriter = new PrintWriter(targetFile);
    }

    public void init() {
    }

    public void generateCode() throws IOException, RecognitionException {
        new PIRWriter(myWriter).writeCode(new HobbesPIRCompiler().compile(
                myFrontEnd, new Code<String>()));
    }

    public void done() {
        myWriter.close();
    }

    public static void main(String[] args) throws IOException,
            RecognitionException {
        PIRCompiler compiler = new PIRCompiler(args[0], args[1]);
        compiler.init();
        compiler.generateCode();
        compiler.done();
    }

}
