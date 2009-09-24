package org.norecess.hobbes.compiler;

import org.norecess.hobbes.backend.Code;
import org.norecess.hobbes.backend.ICode;

public class PIREpilogCompiler implements IPIREpilogCompiler {

	public ICode generate() {
		return new Code(".end");
	}

}
