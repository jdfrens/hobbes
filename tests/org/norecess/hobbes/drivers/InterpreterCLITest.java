package org.norecess.hobbes.drivers;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;

public class InterpreterCLITest {

	@Test
	public void shouldHaveWorkingMain() throws IOException,
			RecognitionException {
		InterpreterCLI.main(new String[] { "examples/add.hob" });
	}

}
