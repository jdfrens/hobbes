package org.norecess.hobbes.backend;

import java.io.IOException;
import java.io.PrintStream;

/*
 * Writes out the instructions for PIR.  Mostly this means putting a tab
 * in front of the real instructions.
 */
public class CodeWriter implements ICodeWriter {

	public void writeCode(PrintStream out, ICode code) throws IOException {
		for (String instruction : code) {
			out.append(instruction).append("\n");
		}
	}

}
