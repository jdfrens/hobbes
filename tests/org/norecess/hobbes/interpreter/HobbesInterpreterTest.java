package org.norecess.hobbes.interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.norecess.hobbes.compiler.HobbesPIRComponentCompilerTest;

public class HobbesInterpreterTest {

	private HobbesInterpreter	myInterpreter;

	@Before
	public void setUp() {
		myInterpreter = new HobbesInterpreter();
	}

	@Test
	public void shouldInterpretInteger() {
		assertEquals("5", myInterpreter
				.interpret(HobbesPIRComponentCompilerTest.createIntegerTree(5)));
		assertEquals("555", myInterpreter
				.interpret(HobbesPIRComponentCompilerTest
						.createIntegerTree(555)));
	}

}
