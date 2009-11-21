package org.norecess.hobbes.backend;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class CodeWriterTest {

	private CodeWriter	myWriter;

	@Before
	public void setUp() {
		myWriter = new CodeWriter();
	}

	@Test
	public void shouldWriteNoCode() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		myWriter.writeCode(new PrintStream(out), new Code());
		assertEquals("", out.toString());
	}

	@Test
	public void shouldWriteCode() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ICode code = new Code("instruction 1", "instruction 2", "instruction 3");

		myWriter.writeCode(new PrintStream(out), code);
		assertEquals("instruction 1\n" + "instruction 2\n" + "instruction 3\n",
				out.toString());
	}

}