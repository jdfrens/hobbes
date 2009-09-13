package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.compiler.HobbesPIRComponentCompilerTest;

public class HobbesInterpreterTest {

	private String[]			myArgv;

	private HobbesInterpreter	myInterpreter;

	@Before
	public void setUp() {
		myArgv = new String[10];
		myInterpreter = new HobbesInterpreter(myArgv);
	}

	@Test
	public void shouldInterpretInteger() {
		assertEquals("5", myInterpreter
				.interpret(HobbesPIRComponentCompilerTest.createIntegerTree(5)));
		assertEquals("555", myInterpreter
				.interpret(HobbesPIRComponentCompilerTest
						.createIntegerTree(555)));
	}

	@Test
	public void shouldInterpretCommandLineArguments() {
		myArgv[8] = "7";
		assertEquals("7", myInterpreter
				.interpret(HobbesPIRComponentCompilerTest.createArgvTree(8)));
		myArgv[3] = "abc";
		assertEquals("abc", myInterpreter
				.interpret(HobbesPIRComponentCompilerTest.createArgvTree(3)));
	}

}
