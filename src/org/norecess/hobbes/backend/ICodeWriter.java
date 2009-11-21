package org.norecess.hobbes.backend;

import java.io.IOException;
import java.io.PrintStream;

public interface ICodeWriter {

	public abstract void writeCode(PrintStream out, ICode code)
			throws IOException;

}
