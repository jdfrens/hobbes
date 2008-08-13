package org.norecess.hobbes.drivers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.norecess.hobbes.compiler.Code;
import org.norecess.hobbes.compiler.HobbesPIRCompiler;
import org.norecess.hobbes.compiler.ICode;
import org.norecess.hobbes.drivers.targetcode.PIRWriter;
import org.norecess.hobbes.frontend.HobbesFrontEnd;

public class PIRCompiler {

    public static void main(String[] args) throws IOException {
        PrintWriter printWriter = new PrintWriter(args[1]);
        new PIRWriter(printWriter).writeCode(new PIRCompiler()
                .generateCode(args[0]));
        printWriter.close();
    }

    public ICode<String> generateCode(String sourceFile) throws IOException {
        return new HobbesPIRCompiler().compile(new HobbesFrontEnd(new File(
                sourceFile)), new Code<String>());
    }

}
