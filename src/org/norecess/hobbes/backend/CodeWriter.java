package org.norecess.hobbes.backend;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/*
 * Writes out the instructions for PIR.  Mostly this means putting a tab
 * in front of the real instructions.
 */
public class CodeWriter implements ICodeWriter {

	private final Appendable	myAppendable;

	@Inject
	public CodeWriter(@Named("output") Appendable appendable) {
		myAppendable = appendable;
	}

	public void writeCode(ICode code) throws IOException {
		for (String instruction : code) {
			myAppendable.append(instruction).append("\n");
		}
	}

}
