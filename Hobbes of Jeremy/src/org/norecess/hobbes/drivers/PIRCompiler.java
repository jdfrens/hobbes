package org.norecess.hobbes.drivers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.norecess.hobbes.compiler.HobbesCompiler;
import org.norecess.hobbes.frontend.HobbesFrontEnd;

public class PIRCompiler {

    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter(args[1]);
        HobbesCompiler r = new HobbesCompiler();
        r.compile(new HobbesFrontEnd(new ANTLRInputStream(new FileInputStream(
                args[0]))), writer);
        writer.close();
    }
}
